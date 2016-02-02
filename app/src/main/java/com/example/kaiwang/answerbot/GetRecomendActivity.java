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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GetRecomendActivity extends AppCompatActivity {

    SeekBar seekBar;
    Button get_recommend;
    TextView recommend_ques, recommend_detail, tips1, tips2;
    public String url;
    public int url_queston_id;
    public String ques_body;
    public String ques_details;
    private boolean viewGroupIsVisible = false;
    private View mViewGroup;
    public ListView listView;
    ArrayList<Rate> arrayOfRates;
    Rate rate;

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
            ques_body = bundle.getString("question_body");
            ques_details = bundle.getString("question_details");
            recommend_ques.setText("Question: "+ques_body);
            recommend_detail.setText("Description: "+ques_details);
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

                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();

                } catch (JSONException e) {
                    e.printStackTrace();
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
                    Toast.makeText(getApplicationContext(),"It is"+r.seek_value,Toast.LENGTH_SHORT).show();
                }
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

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.add_criteria:
                Intent toAddCriteria = new Intent(getApplicationContext(), AddCriteriaActivity.class);
                startActivity(toAddCriteria);
                break;
            case R.id.add_answer:
                Intent toAddAnswer = new Intent(getApplicationContext(), AddOptionsActivity.class);
                startActivity(toAddAnswer);
                break;
            case R.id.rate_answer:
                Intent toRateAnswer = new Intent(getApplicationContext(), DonateKnowledgeActivity.class);
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

    /*

    public void changeSeekBar(SeekBar seekbar){
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                int progress = seekBar.getProgress();
                int pos = listView.getPositionForView(seekBar);
                if(pos!=ListView.INVALID_POSITION){
                    Rate r = arrayOfRates.get(pos);
                    r.setSeek_value(progress);
                    Toast.makeText(getApplicationContext(),progress, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    */

}
