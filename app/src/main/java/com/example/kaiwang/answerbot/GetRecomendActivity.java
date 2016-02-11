package com.example.kaiwang.answerbot;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GetRecomendActivity extends AppCompatActivity {

    Button get_recommend;
    TextView recommend_ques, recommend_detail, tips1, tips2;
    String user_id;
    public String url;
    public int url_queston_id;
    public String ques_body,rques_body;
    public String ques_details,rques_details;
    private boolean viewGroupIsVisible = false;
    private View mViewGroup;
    public ListView listView;
    ArrayList<Rate> arrayOfRates;
    Rate rate;
    public String temp = "[";

    public class Criteria {
        private String c_id;
        private String c_value;

        public Criteria(String string_rate_id, String string_seek_value) {
            this.c_id = string_rate_id;
            this.c_value=string_seek_value;
        }
    }

    public List<Criteria> allCriteria = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recomend);

        mViewGroup = findViewById(R.id.viewsContainer);
        mViewGroup.setVisibility(View.GONE);

        listView = (ListView) findViewById(R.id.lvRates);

        Typeface tf = Typeface.createFromAsset(getAssets(),"RobotoCondensed-Regular.ttf");
        recommend_ques = (TextView)findViewById(R.id.recommend_ques);
        recommend_detail = (TextView)findViewById(R.id.recommend_detail);
        tips1 = (TextView)findViewById(R.id.tips1);
        tips2 = (TextView)findViewById(R.id.tips2);
        get_recommend = (Button)findViewById(R.id.get_recommend_btn);

        recommend_ques.setTypeface(tf);
        recommend_detail.setTypeface(tf);
        tips1.setTypeface(tf);
        tips2.setTypeface(tf);
        get_recommend.setTypeface(tf);

        recommend_ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewGroupIsVisible) {
                    mViewGroup.setVisibility(View.VISIBLE);
                } else {
                    mViewGroup.setVisibility(View.GONE);
                }
                viewGroupIsVisible = !viewGroupIsVisible;
            }
        });

        AsyncHttpClient client = new AsyncHttpClient();
        Bundle bundle = getIntent().getExtras();
        if(!bundle.isEmpty()){
            url_queston_id = bundle.getInt("position");
            user_id= bundle.getString("user_id");
            ques_body = bundle.getString("question_body");
            ques_details = bundle.getString("question_details");
            rques_body="Question: "+ques_body;
            recommend_ques.setText(rques_body);
            rques_details="Description: "+ques_details;
            recommend_detail.setText(rques_details);
        }
        url = "http://dss.simohosio.com/api/getcriteria.php?question_id="+url_queston_id;
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Log.d("TEST", "1111");
                try {
                    String response = (new String(responseBody, "UTF-8"));
                    JSONArray rateArray = new JSONArray(response);
                    arrayOfRates = new ArrayList<>();
                    Log.d("TEST", "2222");
                    for (int i = 0; i < rateArray.length(); i++) {
                        try {
                            Log.d("TEST", "3333");
                            rate = new Rate();
                            JSONObject buffer = rateArray.getJSONObject(i);
                            rate.setRate_body(buffer.getString("criterion_body"));
                            rate.setRate_details(buffer.getString("criterion_details"));
                            rate.setRate_id(buffer.getInt("criterion_id"));
                            rate.setRate_Meta(buffer.getString("meta"));
                            arrayOfRates.add(rate);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    CustomRatesAdapter adapter = new CustomRatesAdapter(getApplication(), arrayOfRates);

                    // Attach the adapter to a ListView
                    listView.setAdapter(adapter);

                } catch (UnsupportedEncodingException | JSONException e1) {
                    e1.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

        get_recommend = (Button)findViewById(R.id.get_recommend_btn);
        get_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Rate r:arrayOfRates) {
                    //Log.d("TS", r.rate_id + "is" + r.seek_value);
                    String string_rate_id = String.valueOf(r.rate_id);
                    String string_seek_value = String.valueOf(r.seek_value);
                    allCriteria.add(new Criteria(string_rate_id, string_seek_value));
                    Log.d("TS", "array is " + allCriteria);
                }
                for(Criteria c:allCriteria){
                    //Log.d("TS","["+c.c_id+","+c.c_value+"]");
                    temp += "[\""+c.c_id+"\",\""+c.c_value+"\"]"+",";
                }
                //Log all criteria:
                temp = temp.substring(0,temp.length()-1)+"]";
                Log.d("TEST",temp);
                uploadCriteria();

            }
        });
    }

    private void uploadCriteria() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("criteria_importances", temp);
        params.put("question_id",url_queston_id );
        params.put("user_id",user_id);
        params.put("meta","");

        client.get("http://dss.simohosio.com/api/getrecommendations.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String response = (new String(responseBody, "UTF-8"));
                    JSONArray rateArray = new JSONArray(response);
                    arrayOfRates = new ArrayList<>();
                    for (int i = 0; i < rateArray.length(); i++) {
                        try {
                            rate = new Rate();
                            JSONObject buffer = rateArray.getJSONObject(i);
                            rate.setRate_body(buffer.getString("criterion_body"));
                            rate.setRate_details(buffer.getString("criterion_details"));
                            rate.setRate_id(buffer.getInt("criterion_id"));
                            rate.setRate_Meta(buffer.getString("meta"));
                            arrayOfRates.add(rate);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    CustomRatesAdapter adapter = new CustomRatesAdapter(getApplication(), arrayOfRates);

                    // Attach the adapter to a ListView
                    listView.setAdapter(adapter);

                } catch (UnsupportedEncodingException | JSONException e1) {
                    e1.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_recomend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        bundle.putInt("position", url_queston_id);
        bundle.putString("question_body",ques_body);
        bundle.putString("question_details",ques_details);
        bundle.putString("user_id", user_id);
        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.add_criteria:
                Intent toAddCriteria = new Intent(getApplicationContext(), AddCriteriaActivity.class);
                toAddCriteria.putExtras(bundle);
                startActivity(toAddCriteria);
                break;
            case R.id.add_answer:
                Intent toAddAnswer = new Intent(getApplicationContext(), AddOptionsActivity.class);
                toAddAnswer.putExtras(bundle);
                startActivity(toAddAnswer);
                break;
            case R.id.rate_answer:
                Intent toRateAnswer = new Intent(getApplicationContext(), DonateKnowledgeActivity.class);
                toRateAnswer.putExtras(bundle);
                startActivity(toRateAnswer);
                break;
            case R.id.share_question:
                String urlToShare = "http://dss.simohosio.com/new.php?type=s&qid="+url_queston_id;

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
                break;
        }

        return super.onOptionsItemSelected(item);

    }
}
