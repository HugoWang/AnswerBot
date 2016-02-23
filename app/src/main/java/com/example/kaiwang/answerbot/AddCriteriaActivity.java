package com.example.kaiwang.answerbot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class AddCriteriaActivity extends AppCompatActivity {

    String question_body = "Passed Question Body";
    String question_id = "Passed Question ID";
    String user_id = "Passed User_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_criteria);
        Bundle bundle = getIntent().getExtras();
        if(!bundle.isEmpty()) {
            question_id = Integer.toString(bundle.getInt("position"));
            question_body = bundle.getString("question_body");
            user_id = bundle.getString("user_id");
        }
        TextView QuestionTextView = (TextView) findViewById(R.id.AddCriteriaQuestionTextView);
        QuestionTextView.setText(question_body);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_criteria, menu);
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

    //Submit button click
    public void SubmitCriterionBtnClick(View v) {
        PostNewCriterionAsync();
    }

    private void PostNewCriterionAsync() {
        AsyncHttpClient client = new AsyncHttpClient();
        EditText NCB = (EditText) findViewById(R.id.NewCriterionEditText);
        String NewCriterionBody = NCB.getText().toString();

        if (NewCriterionBody.length() == 0 || NewCriterionBody.equals(" ") || NewCriterionBody.equals("\n")){
            Toast.makeText(AddCriteriaActivity.this, "Please specify a criteria!", Toast.LENGTH_SHORT).show();
        }
        else {
            NewCriterionBody = Character.toUpperCase(NewCriterionBody.charAt(0)) + NewCriterionBody.substring(1);
            EditText NCD = (EditText) findViewById(R.id.NewCriterionDetailsEditText);
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

