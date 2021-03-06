package com.answerbot.mobile;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListAdapter myAdapter;
    ListView myListView;
    ArrayList<Questions> newValues;
    String UserID;
    SearchView searchView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    final Context context = this;
    AsyncHttpClient client;
    //final private int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;

    App app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //app= (App)getApplication();
        super.onCreate(savedInstanceState);
        //if (Integer.valueOf(Build.VERSION.SDK_INT)==23) getDevieID();

        Bundle bun = getIntent().getExtras();
        if (bun != null) {
            UserID = bun.getString("user_id");
        }
        setContentView(com.answerbot.mobile.R.layout.activity_home);
        myListView = (ListView) findViewById(com.answerbot.mobile.R.id.myListView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(com.answerbot.mobile.R.id.activity_main_swipe_refresh_layout);

        mSwipeRefreshLayout.setColorSchemeResources(com.answerbot.mobile.R.color.ripple, com.answerbot.mobile.R.color.green, com.answerbot.mobile.R.color.blue);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });

        getQuestions();

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Questions question = (Questions) parent.getItemAtPosition(position);
                final int q_id = question.question_id;
                String q_body = question.question_body;
                String q_details = question.question_details;
                final Animation shake = AnimationUtils.loadAnimation(HomeActivity.this, com.answerbot.mobile.R.anim.shake);

                final Bundle bundle = new Bundle();
                bundle.putInt("position", q_id);
                bundle.putString("question_body", q_body);
                bundle.putString("question_details", q_details);
                bundle.putString("user_id", UserID);
                //bundle.putString("upload_user_id", upload_user_id);

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(com.answerbot.mobile.R.layout.dialog);

                final ImageButton addC = (ImageButton) dialog.findViewById(com.answerbot.mobile.R.id.addC);
                final ImageButton addO = (ImageButton) dialog.findViewById(com.answerbot.mobile.R.id.addO);
                final ImageButton rateO = (ImageButton) dialog.findViewById(com.answerbot.mobile.R.id.rateO);
                final ImageButton share = (ImageButton) dialog.findViewById(com.answerbot.mobile.R.id.share);
                //ImageButton cancel = (ImageButton)dialog.findViewById(R.id.cancel);
                dialog.show();

                addC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addC.startAnimation(shake);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent toaddC = new Intent(getApplicationContext(), AddCriteriaActivity.class);
                                toaddC.putExtras(bundle);
                                startActivity(toaddC);
                            }
                        }, 1000);
                    }
                });

                addO.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addO.startAnimation(shake);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent toaddO = new Intent(getApplicationContext(), AddOptionsActivity.class);
                                toaddO.putExtras(bundle);
                                startActivity(toaddO);
                            }
                        }, 1000);
                    }
                });

                rateO.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rateO.startAnimation(shake);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent torateO = new Intent(getApplicationContext(), DonateKnowledgeActivity.class);
                                torateO.putExtras(bundle);
                                startActivity(torateO);
                            }
                        }, 1000);
                    }
                });

                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        share.startAnimation(shake);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                String urlToShare = "http://dss.simohosio.com/new.php?type=s&qid=" + q_id;

                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

                                boolean facebookAppFound = false;
                                List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
                                for (ResolveInfo info : matches) {
                                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                                        intent.setPackage(info.activityInfo.packageName);
                                        facebookAppFound = true;
                                        break;
                                    }
                                }

                                if (!facebookAppFound) {
                                    String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
                                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                                }
                                startActivity(intent);
                            }
                        }, 500);
                    }
                });

            /*
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            */

                return true;
            }
        });
/*
        SearchView search = (SearchView)findViewById(R.id.input);
        search.setQueryHint("Search");
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("Debugging:",newText);
                doSearch(newText);
                updateView();
                return false;
            }
        });
*/

            //Add question button
        FloatingActionButton FAB = (FloatingActionButton) findViewById(com.answerbot.mobile.R.id.fab);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddQuestion = new Intent(getApplicationContext(), AddQuestionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user_id", UserID);
                toAddQuestion.putExtras(bundle);
                startActivity(toAddQuestion);
            }
        });
    }
    /*
    private void getDevieID() {
        Log.d("tt", "here");
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            return;
        }
    }
    */
    @Override
    public void onRestart(){
        super.onRestart();
        getQuestions();
    }
    public void getQuestions(){
        client = new AsyncHttpClient();
        client.get("http://dss.simohosio.com/api/getquestions.php", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray responseBody) {
                app = new App();
                for (int i = 0; i < responseBody.length(); i++) {
                    try {
                        Questions ques = new Questions();
                        JSONObject buffer = responseBody.getJSONObject(i);
                        ques.setQuality_score(buffer.getInt("quality_score"));
                        ques.setUser_id(buffer.getString("user_id"));
                        ques.setQuestion_id(buffer.getInt("question_id"));
                        ques.setQuestion_body(buffer.getString("question_body"));
                        ques.setQuestion_details(buffer.getString("question_details"));
                        ques.setMeta(buffer.getString("meta"));

                        app.addQuestion(ques);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    myAdapter = new CustomAdapter(HomeActivity.this, app.getQuestions());
                    myListView.setAdapter(myAdapter);
                    myListView.setOnItemClickListener(HomeActivity.this);
                }
            }

        });
    }
    public void doSearch(String s) {
        newValues = new ArrayList<>();
        if (s.equals("")){
            newValues=app.getQuestions();
        } else {
            for (int i=0;i<app.getSize();i++){
                if (app.getQuestion(i).question_body.toLowerCase().contains(s.toLowerCase())){
                    newValues.add(app.getQuestion(i));
                }
            }
        }
    }

    public void updateView(){
        myListView.setAdapter(null);
        myAdapter=new CustomAdapter(HomeActivity.this,newValues);
        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(HomeActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.answerbot.mobile.R.menu.menu_home, menu);
        MenuItem searchItem = menu.findItem(com.answerbot.mobile.R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("Debugging:",newText);
                doSearch(newText);
                updateView();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.answerbot.mobile.R.id.action_search) {

        }

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Questions question = (Questions)parent.getItemAtPosition(position);
        int q_id = question.question_id;
        String q_body = question.question_body;
        String q_details = question.question_details;
        Intent toRecommend = new Intent(getApplicationContext(),GetRecomendActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position", q_id);
        bundle.putString("question_body",q_body);
        bundle.putString("question_details",q_details);
        bundle.putString("user_id", UserID);
        //bundle.putString("upload_user_id", upload_user_id);
        toRecommend.putExtras(bundle);
        startActivity(toRecommend);
    }
}
