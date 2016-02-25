package com.example.kaiwang.answerbot;


import android.app.ActionBar;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddOptionsActivity extends AppCompatActivity {
    String question_body = "Passed Question Body";
    String question_id = "Passed Question ID";
    String user_id = "Passed User_ID";
    String question_details;
    public Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_options);
        TextView QuestionTextView = (TextView) findViewById(R.id.AddOptionsQuestionTextView);
        TextView QuestionDetails = (TextView)findViewById(R.id.AddOQ_detail);
        Button submit = (Button)findViewById(R.id.SubmitNewOptionBtn);
        TextView text1 = (TextView)findViewById(R.id.addO_1);
        TextView text2 = (TextView)findViewById(R.id.addO_2);
        tf = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");
        Bundle bundle = getIntent().getExtras();
        if(!bundle.isEmpty()) {
            question_id = Integer.toString(bundle.getInt("position"));
            question_body = bundle.getString("question_body");
            question_details = bundle.getString("question_details");
            user_id = bundle.getString("user_id");
        }
        QuestionDetails.setTypeface(tf);
        QuestionTextView.setTypeface(tf);
        text1.setTypeface(tf);
        text2.setTypeface(tf);
        submit.setTypeface(tf);
        QuestionTextView.setText("Question: " + question_body);
        QuestionDetails.setText(question_details);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    //Submit button click
    public void SubmitOptionsBtnClick(View v) {
        PostNewOptionsAsync();
    }

    private void PostNewOptionsAsync(){
        AsyncHttpClient client = new AsyncHttpClient();
        tf = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");
        EditText NOB =  (EditText) findViewById(R.id.NewOptionEditText);
        NOB.setTypeface(tf);
        String NewOptionBody = NOB.getText().toString();
        if (NewOptionBody.length() == 0 || NewOptionBody.equals(" ") || NewOptionBody.equals("\n")){
            Toast.makeText(AddOptionsActivity.this, "Please specify an answer!", Toast.LENGTH_SHORT).show();
        }
        else {
            NewOptionBody = Character.toUpperCase(NewOptionBody.charAt(0)) + NewOptionBody.substring(1);
            EditText NOD =  (EditText) findViewById(R.id.NewOptionDetailsEditText);
            NOD.setTypeface(tf);
            String NewOptionDetails = NOD.getText().toString();
            if (NewOptionDetails.length() != 0){
                NewOptionDetails = Character.toUpperCase(NewOptionDetails.charAt(0)) + NewOptionDetails.substring(1);
            }
            com.loopj.android.http.RequestParams params = new RequestParams();
            params.add("user_id", user_id);
            params.add("question_id", question_id);
            params.add("body", NewOptionBody);
            params.add("details", NewOptionDetails);
            params.add("meta", " ");
            client.post("http://dss.simohosio.com/api/postsolution.php", params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray responseBody) {
//                Button b = (Button) findViewById(R.id.SubmitNewQuestionBtn);
//                b.setText("Kysymys lisätty");
//
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//

        });
        Toast.makeText(AddOptionsActivity.this, "New option added!", Toast.LENGTH_SHORT).show();
        finish();
}}
}
