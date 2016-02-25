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

import java.util.ArrayList;

public class AddQuestionActivity extends AppCompatActivity {

    String user_id = "Passed user_id";
    App app;
    Typeface tf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        Bundle bundle = getIntent().getExtras();
        if(!bundle.isEmpty()) {
            user_id = bundle.getString("user_id");
        }
        TextView text1 = (TextView)findViewById(R.id.addQ_1);
        TextView text2 = (TextView)findViewById(R.id.addQ_2);
        Button submit = (Button)findViewById(R.id.SubmitNewQuestionBtn);
        tf = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");
        text1.setTypeface(tf);
        text2.setTypeface(tf);
        submit.setTypeface(tf);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    //Submit button click
    public void SubmitQuestionBtnClick(View v) {
        PostNewQuestionAsync();
    }


    private boolean QuestionAlreadyExists(String question){
        app = (App)getApplication();
        ArrayList<Questions> ListOfQuestions = app.allQuestions;
        for (int i = 0; i < ListOfQuestions.size(); i++) {
            if (ListOfQuestions.get(i).question_body.equals(question)){
                return true;
            }
        }
        return false;
    }

    private void PostNewQuestionAsync(){
        AsyncHttpClient client = new AsyncHttpClient();
        EditText NQB =  (EditText) findViewById(R.id.NewQuestionEditText);
        NQB.setTypeface(tf);
        String NewQuestionBody = NQB.getText().toString();
        if (NewQuestionBody.length() == 0 || NewQuestionBody.equals(" ")|| NewQuestionBody.equals("\n")){
            Toast.makeText(AddQuestionActivity.this, "Please specify a question!", Toast.LENGTH_SHORT).show();
        }
        else {
            NewQuestionBody = Character.toUpperCase(NewQuestionBody.charAt(0)) + NewQuestionBody.substring(1);
            EditText NQD =  (EditText) findViewById(R.id.NewQuestionDetailsEditText);
            NQD.setTypeface(tf);
            String NewQuestionDetails = NQD.getText().toString();

            if (NewQuestionDetails.length() != 0) {
                NewQuestionDetails = Character.toUpperCase(NewQuestionDetails.charAt(0)) + NewQuestionDetails.substring(1);
            }

            if (NewQuestionBody.charAt((NewQuestionBody.length()-1)) != '?') {
                NewQuestionBody = NewQuestionBody + "?";
            }

            if (QuestionAlreadyExists(NewQuestionBody)) {
                Toast.makeText(AddQuestionActivity.this, "This question already exists!", Toast.LENGTH_SHORT).show();
            }
            else {
                com.loopj.android.http.RequestParams params = new RequestParams();
                params.add("user_id", user_id);
                params.add("body", NewQuestionBody);
                params.add("details", NewQuestionDetails);
                params.add("meta", " ");
                client.post("http://dss.simohosio.com/api/postquestion.php", params, new JsonHttpResponseHandler() {
//              @Override
//              public void onSuccess(int statusCode, Header[] headers, JSONArray responseBody) {
//                Button b = (Button) findViewById(R.id.SubmitNewQuestionBtn);
//                b.setText("Kysymys lisätty");
//
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//            }

                });

                Toast.makeText(AddQuestionActivity.this, "Question added!", Toast.LENGTH_SHORT).show();
                finish();
            }
    }}
}
