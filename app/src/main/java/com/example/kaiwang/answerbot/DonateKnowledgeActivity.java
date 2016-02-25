package com.example.kaiwang.answerbot;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class DonateKnowledgeActivity extends AppCompatActivity {

    TextView answer_content, answer_detail;
    Button donate_knowledge;
    String user_id;
    public int url_queston_id;
    public String url;
    ArrayList<String> arrayofSolutions;
    ArrayList<String> solutionId;
    private Random randomGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_knowledge);

        answer_content = (TextView)findViewById(R.id.answer_content);
        answer_detail = (TextView)findViewById(R.id.answer_details);
        donate_knowledge = (Button)findViewById(R.id.donate_btn);

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
