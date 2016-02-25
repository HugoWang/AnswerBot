package com.example.kaiwang.answerbot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddQuestionActivity extends AppCompatActivity {

    String user_id = "Passed user_id";
    App app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App)getApplication();
        setContentView(R.layout.activity_add_question);
        Bundle bundle = getIntent().getExtras();
        if(!bundle.isEmpty()) {
            user_id = bundle.getString("user_id");
        }
    }

    //Submit button click
    public void SubmitQuestionBtnClick(View v) {
        PostNewQuestionAsync();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_question, menu);
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

    private void PostNewQuestionAsync(){
        AsyncHttpClient client = new AsyncHttpClient();
        EditText NQB =  (EditText) findViewById(R.id.NewQuestionEditText);
        String NewQuestionBody = NQB.getText().toString();
        if (NewQuestionBody.length() == 0 || NewQuestionBody.equals(" ")|| NewQuestionBody.equals("\n")){
            Toast.makeText(AddQuestionActivity.this, "Please specify a question!", Toast.LENGTH_SHORT).show();
        }
        else {
            NewQuestionBody = Character.toUpperCase(NewQuestionBody.charAt(0)) + NewQuestionBody.substring(1);
            EditText NQD =  (EditText) findViewById(R.id.NewQuestionDetailsEditText);
            String NewQuestionDetails = NQD.getText().toString();
            if (NewQuestionDetails.length() != 0) {
                NewQuestionDetails = Character.toUpperCase(NewQuestionDetails.charAt(0)) + NewQuestionDetails.substring(1);
            }
            com.loopj.android.http.RequestParams params = new RequestParams();
            params.add("user_id", user_id);
            params.add("body", NewQuestionBody);
            params.add("details", NewQuestionDetails);
            params.add("meta", " ");
            client.post("http://dss.simohosio.com/api/postquestion.php", params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray responseBody) {
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
    }}
}
