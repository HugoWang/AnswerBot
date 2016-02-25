package com.example.kaiwang.answerbot;

import android.graphics.Typeface;
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
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class DonateKnowledgeActivity extends AppCompatActivity {

    TextView answer_content, answer_detail;
    Button donate_knowledge;
    String user_id;
    public int url_queston_id;
    public String url;
    public String url2;
    ArrayList<String> arrayofSolutions;
    ArrayList<String> solutionId;
    private Random randomGenerator;
    ArrayList<Rate> arrayOfRates;
    Rate rate;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_knowledge);

        answer_content = (TextView)findViewById(R.id.answer_content);
        answer_detail = (TextView)findViewById(R.id.answer_details);
        donate_knowledge = (Button)findViewById(R.id.donate_btn);
        listView = (ListView) findViewById(R.id.lvDonate);

        String answer_details = answer_detail.getText().toString();
        Typeface tf = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");
        answer_content.setTypeface(tf);
        answer_detail.setTypeface(tf);
        donate_knowledge.setTypeface(tf);

        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            url_queston_id = bundle.getInt("position");
            user_id = bundle.getString("user_id");
            Log.d("Q","q_id is "+url_queston_id);
        }

        AsyncHttpClient client = new AsyncHttpClient();
        url = "http://dss.simohosio.com/api/getsolutions.php?question_id=" + url_queston_id;

        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray responseBody) {
                arrayofSolutions = new ArrayList<>();
                solutionId = new ArrayList<>();
                for (int i = 0; i < responseBody.length(); i++) {
                    try {
                        JSONObject buffer = responseBody.getJSONObject(i);
                        arrayofSolutions.add(buffer.getString("solution_body"));
                        solutionId.add(buffer.getString("solution_id"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("TEST","solutions are "+arrayofSolutions);
                Log.d("TEST","Id are "+solutionId);

                randomGenerator = new Random();
                int index = randomGenerator.nextInt(arrayofSolutions.size());
                String item = arrayofSolutions.get(index);
                String item_id = solutionId.get(index);

                answer_content.setText("Answer: "+item);

                Log.d("TEST", "item is "+item);
                Log.d("TEST","item id is "+item_id);
            }

        });

        url2 = "http://dss.simohosio.com/api/getcriteria.php?question_id=" + url_queston_id;
        client.get(url2, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("TEST", "1111");
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
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

        if(answer_details== null || answer_details.length() == 0 || answer_details.equals("")){
            answer_detail.setVisibility(View.GONE);
        } else {
            answer_detail.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donate_knowledge, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
