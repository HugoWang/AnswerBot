package com.answerbot.mobile;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

import cz.msebera.android.httpclient.Header;

public class AddOptionsActivity extends AppCompatActivity {
    String question_body = "Passed Question Body";
    String question_id = "Passed Question ID";
    String user_id = "Passed User_ID";
    String question_details;
    public Typeface tf;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.answerbot.mobile.R.layout.activity_add_options);
        TextView QuestionTextView = (TextView) findViewById(com.answerbot.mobile.R.id.AddOptionsQuestionTextView);
        TextView QuestionDetails = (TextView)findViewById(com.answerbot.mobile.R.id.AddOQ_detail);
        Button submit = (Button)findViewById(com.answerbot.mobile.R.id.SubmitNewOptionBtn);
        TextView text1 = (TextView)findViewById(com.answerbot.mobile.R.id.addO_1);
        TextView text2 = (TextView)findViewById(com.answerbot.mobile.R.id.addO_2);
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


        final ArrayList<String> AnswerList = new ArrayList<String>();
        AsyncHttpClient client = new AsyncHttpClient();
        url = "http://dss.simohosio.com/api/getsolutions.php?question_id=" + question_id;
        client.get(url, new AsyncHttpResponseHandler() {

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
                    Log.d("TEST", "2222");
                    for (int i = 0; i < rateArray.length(); i++) {
                        try {
                            Log.d("TEST", "3333");
                            JSONObject buffer = rateArray.getJSONObject(i);
                            AnswerList.add(buffer.getString("solution_body"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, AnswerList);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(com.answerbot.mobile.R.id.NewOptionEditText);
        textView.setAdapter(adapter);
        textView.setCompletionHint("These answers have been given");


    }

    //Submit button click
    public void SubmitOptionsBtnClick(View v) {
        PostNewOptionsAsync();
    }

    private void PostNewOptionsAsync(){
        AsyncHttpClient client = new AsyncHttpClient();
        tf = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");
        EditText NOB =  (EditText) findViewById(com.answerbot.mobile.R.id.NewOptionEditText);
        NOB.setTypeface(tf);
        String NewOptionBody = NOB.getText().toString();
        if (NewOptionBody.length() == 0 || NewOptionBody.equals(" ") || NewOptionBody.equals("\n")){
            Toast.makeText(AddOptionsActivity.this, "Please specify an answer!", Toast.LENGTH_SHORT).show();
        }
        else {
            NewOptionBody = Character.toUpperCase(NewOptionBody.charAt(0)) + NewOptionBody.substring(1);
            EditText NOD =  (EditText) findViewById(com.answerbot.mobile.R.id.NewOptionDetailsEditText);
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
