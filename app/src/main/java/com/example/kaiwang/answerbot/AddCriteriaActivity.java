package com.example.kaiwang.answerbot;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class AddCriteriaActivity extends AppCompatActivity {

    String question_body = "Passed Question Body";
    String question_details;
    String question_id = "Passed Question ID";
    String user_id = "Passed User_ID";
    public Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_criteria);
        Bundle bundle = getIntent().getExtras();
        if(!bundle.isEmpty()) {
            question_id = Integer.toString(bundle.getInt("position"));
            question_body = bundle.getString("question_body");
            question_details = bundle.getString("question_details");
            user_id = bundle.getString("user_id");
        }
        TextView QuestionTextView = (TextView) findViewById(R.id.AddCriteriaQuestionTextView);
        TextView QuestionDetails = (TextView)findViewById(R.id.AddCQ_detail);
        TextView text1 = (TextView)findViewById(R.id.add_c_1);
        TextView text2 = (TextView)findViewById(R.id.add_c_2);
        Button submit = (Button)findViewById(R.id.SubmitNewCriterionBtn);
        tf = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");
        QuestionTextView.setTypeface(tf);
        QuestionDetails.setTypeface(tf);
        text1.setTypeface(tf);
        text2.setTypeface(tf);
        submit.setTypeface(tf);
        QuestionTextView.setText("Question: " + question_body);
        QuestionDetails.setText(question_details);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    //Submit button click
    public void SubmitCriterionBtnClick(View v) {
        PostNewCriterionAsync();
    }

    private void PostNewCriterionAsync() {
        AsyncHttpClient client = new AsyncHttpClient();
        tf = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");
        EditText NCB = (EditText) findViewById(R.id.NewCriterionEditText);
        NCB.setTypeface(tf);
        String NewCriterionBody = NCB.getText().toString();

        if (NewCriterionBody.length() == 0 || NewCriterionBody.equals(" ") || NewCriterionBody.equals("\n")){
            Toast.makeText(AddCriteriaActivity.this, "Please specify a criteria!", Toast.LENGTH_SHORT).show();
        }
        else {
            NewCriterionBody = Character.toUpperCase(NewCriterionBody.charAt(0)) + NewCriterionBody.substring(1);
            EditText NCD = (EditText) findViewById(R.id.NewCriterionDetailsEditText);
            NCD.setTypeface(tf);
            String NewCriterionDetails = NCD.getText().toString();
            if (NewCriterionDetails.length() != 0){
                NewCriterionDetails = Character.toUpperCase(NewCriterionDetails.charAt(0)) + NewCriterionDetails.substring(1);
            }
            com.loopj.android.http.RequestParams params = new RequestParams();
            params.add("user_id", user_id);
            params.add("question_id", question_id);
            params.add("body", NewCriterionBody);
            params.add("details", NewCriterionDetails);
            params.add("meta", " ");
            client.post("http://dss.simohosio.com/api/postcriterion.php", params, new JsonHttpResponseHandler() {
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
        Toast.makeText(AddCriteriaActivity.this, "New criterion added!", Toast.LENGTH_SHORT).show();
        finish();
    }
}}

