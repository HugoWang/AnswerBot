package com.example.kaiwang.answerbot;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    JSONArray stuff = RequestServer("getQuestions.php");
    ListView myListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ArrayList<Questions> values = new ArrayList<>();
        for (int i=0;i<stuff.length();i++){
            try{
                Questions ques = new Questions();
                JSONObject buffer = stuff.getJSONObject(i);
                ques.setQuality_score(buffer.getInt("quality_score"));
                ques.setUser_id(buffer.getString("user_id"));
                ques.setQuestion_id(buffer.getInt("question_id"));
                ques.setQuestion_body(buffer.getString("question_body"));
                ques.setQuestion_details(buffer.getString("question_details"));
                ques.setMeta(buffer.getString("meta"));

                values.add(ques);
                }catch (JSONException e){
                    e.printStackTrace();
            }
        }
        final ListAdapter myAdapter = new CustomAdapter(this, values);
        myListView = (ListView)findViewById(R.id.myListView);
        myListView.setAdapter(myAdapter);
        Button btn = (Button) findViewById(R.id.askNewQuestion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText)findViewById(R.id.input);
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
}
