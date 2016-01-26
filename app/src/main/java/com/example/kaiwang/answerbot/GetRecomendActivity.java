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
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recomend);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://dss.simohosio.com/api/getcriteria.php?question_id=1", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Log.d("TEST","1111");
                try {
                    String response = (new String(responseBody, "UTF-8"));
                    JSONArray rateArray = new JSONArray(response);
                    ArrayList<Rate> arrayOfRates = new ArrayList<>();
                    Log.d("TEST","2222");
                    for (int i=0;i<rateArray.length();i++){
                        try{
                            Log.d("TEST","3333");
                            Rate rate = new Rate();
                            JSONObject buffer = rateArray.getJSONObject(i);
                            rate.setRate_body(buffer.getString("criterion_body"));
                            rate.setRate_details(buffer.getString("criterion_details"));
                            arrayOfRates.add(rate);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                    CustomRatesAdapter adapter = new CustomRatesAdapter(getApplication(), arrayOfRates);

                    // Attach the adapter to a ListView
                    ListView listView = (ListView) findViewById(R.id.lvRates);
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
        /*
        JSONArray rateArray = null;
        ArrayList<Rate> arrayOfRates = new ArrayList<>();
        for (int i=0;i<rateArray.length();i++){
            try{
                Rate rate = new Rate();
                JSONObject buffer = rateArray.getJSONObject(i);
                rate.setRate_body(buffer.getString("criterion_body"));
                rate.setRate_details(buffer.getString("criterion_details"));
                arrayOfRates.add(rate);
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        // Create the adapter to convert the array to views
        CustomRatesAdapter adapter = new CustomRatesAdapter(this, arrayOfRates);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvRates);
        listView.setAdapter(adapter);
        */
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
        if (id == R.id.add_criteria) {
            Intent toAddCriteria = new Intent(getApplicationContext(), AddCriteriaActivity.class);
            startActivity(toAddCriteria);
        }
        else if (id == R.id.add_answer) {
            Intent toAddAnswer = new Intent(getApplicationContext(), AddOptionsActivity.class);
            startActivity(toAddAnswer);
        }
        else if (id == R.id.rate_answer) {
            Intent toRateAnswer = new Intent(getApplicationContext(), DonateKnowledgeActivity.class);
            startActivity(toRateAnswer);
        }
        else if (id == R.id.share_question) {
            String urlToShare = "http://dss.simohosio.com/new.php?type=s&qid=1";

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

        return super.onOptionsItemSelected(item);

    }
}
