package com.example.kaiwang.answerbot;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView myListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myListView = (ListView) findViewById(R.id.myListView);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://dss.simohosio.com/api/getquestions.php", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray responseBody) {
                ArrayList<Questions> values = new ArrayList<>();
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

                        values.add(ques);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ListAdapter myAdapter = new CustomAdapter(HomeActivity.this, values);
                    myListView.setAdapter(myAdapter);
                    myListView.setOnItemClickListener(HomeActivity.this);
                }

            }

        });


        Button btn = (Button) findViewById(R.id.askNewQuestion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.input);
                edit.setText("");
            }
        });


        //Add question button
        FloatingActionButton FAB = (FloatingActionButton) findViewById(R.id.fab);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddQuestion = new Intent(getApplicationContext(), AddQuestionActivity.class);
                startActivity(toAddQuestion);
            }
        });

    }

    public void newQuestion(View view) {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
            Intent toRecommend = new Intent(getApplicationContext(), GetRecomendActivity.class);
            startActivity(toRecommend);
        }

        return super.onOptionsItemSelected(item);
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
        toRecommend.putExtras(bundle);
        startActivity(toRecommend);

    }
}
