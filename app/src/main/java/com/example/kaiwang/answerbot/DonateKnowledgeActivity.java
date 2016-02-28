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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class DonateKnowledgeActivity extends AppCompatActivity {

    TextView answer_content, answer_detail;
    Button donate_knowledge;
    public String user_id;
    public int url_queston_id;
    public String url;
    public String url2;
    ArrayList<String> arrayofSolutions;
    ArrayList<String> solutionId;
    ArrayList<String> solutionDetails;
    private Random randomGenerator;
    ArrayList<Rate> arrayOfRates;
    Rate rate;
    public ListView listView;
    public String item_id;

    public class Rating {
        private int r_question_id;
        private String r_solution_id;
        private String r_criterion_id;
        private String r_rating;

        public Rating(int int_question_id, String string_solution_id, String string_criterion_id, String string_rating) {
            this.r_question_id = int_question_id;
            this.r_solution_id = string_solution_id;
            this.r_criterion_id = string_criterion_id;
            this.r_rating = string_rating;
        }
    }

    public List<Rating> allRating = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_knowledge);

        answer_content = (TextView)findViewById(R.id.answer_content);
        answer_detail = (TextView)findViewById(R.id.answer_details);
        donate_knowledge = (Button)findViewById(R.id.donate_btn);
        listView = (ListView) findViewById(R.id.lvDonate);

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
                solutionDetails = new ArrayList<>();
                for (int i = 0; i < responseBody.length(); i++) {
                    try {
                        JSONObject buffer = responseBody.getJSONObject(i);
                        arrayofSolutions.add(buffer.getString("solution_body"));
                        solutionId.add(buffer.getString("solution_id"));
                        solutionDetails.add(buffer.getString("solution_details"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("TEST","solutions are "+arrayofSolutions);
                Log.d("TEST","Id are "+solutionId);

                if(arrayofSolutions.isEmpty()){
                    Toast.makeText(getApplicationContext(),"No answers yet.",Toast.LENGTH_SHORT).show();
                    donate_knowledge.setEnabled(false);
                }
                else{
                    randomGenerator = new Random();
                    int index = randomGenerator.nextInt(arrayofSolutions.size());
                    String item = arrayofSolutions.get(index);
                    item_id = solutionId.get(index);
                    String item_details = solutionDetails.get(index);

                    answer_content.setText("Answer: "+item);
                    answer_detail.setText(item_details);

                    Log.d("TEST", "item is "+item);
                    Log.d("TEST","item id is "+item_id);
                }

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

        donate_knowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Rate r : arrayOfRates) {
                    //Log.d("TS", r.rate_id + "is" + r.seek_value);
                    int donate_q_id = url_queston_id;
                    String donate_s_id = item_id;
                    String string_rate_id = String.valueOf(r.rate_id);
                    String string_seek_value = String.valueOf(r.seek_value);
                    allRating.add(new Rating(donate_q_id, donate_s_id, string_rate_id, string_seek_value));
                    Log.d("TS", "rating is " + allRating);
                }

                String temp = "[";
                for (Rating r : allRating) {
                    temp += "[\"" + r.r_question_id + "\",\"" + r.r_solution_id + "\",\"" + r.r_criterion_id + "\",\"" + r.r_rating + "\"]" + ",";
                }
                //Log all criteria:
                temp = temp.substring(0, temp.length() - 1) + "]";
                Log.d("TEST", temp);

                donateKnowledge(temp);

                allRating.clear();
            }
        });

    }

    private void donateKnowledge(String temp) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.add("json_ratings", temp);
        params.add("user_id", user_id);
        params.add("meta", "");

        client.post("http://dss.simohosio.com/api/postrating.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray responseBody) {
                Log.d("success",responseBody.toString());
            }
        });
        Toast.makeText(getApplicationContext(),"Thank you for your contribution!",Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
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
        if (id == R.id.action_refresh) {
            finish();
            startActivity(getIntent());
        }

        return super.onOptionsItemSelected(item);
    }
}
