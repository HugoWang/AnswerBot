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

public class AddOptionsActivity extends AppCompatActivity {
    String question_body = "Passed Question Body";
    String question_id = "Passed Question ID";
    String user_id = "Passed User_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_options);
        TextView QuestionTextView = (TextView) findViewById(R.id.AddOptionsQuestionTextView);
        Bundle bundle = getIntent().getExtras();
        if(!bundle.isEmpty()) {
            question_id = Integer.toString(bundle.getInt("position"));
            question_body = bundle.getString("question_body");
            user_id = bundle.getString("user_id");
        }
        QuestionTextView.setText(question_body);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_options, menu);
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
    public void SubmitOptionsBtnClick(View v) {
        PostNewOptionsAsync();
    }

    private void PostNewOptionsAsync(){
        AsyncHttpClient client = new AsyncHttpClient();
        EditText NOB =  (EditText) findViewById(R.id.NewOptionEditText);
        String NewOptionBody = NOB.getText().toString();
        if (NewOptionBody.length() == 0 || NewOptionBody.equals(" ") || NewOptionBody.equals("\n")){
            Toast.makeText(AddOptionsActivity.this, "Please specify an answer!", Toast.LENGTH_SHORT).show();
        }
        else {
        EditText NOD =  (EditText) findViewById(R.id.NewOptionDetailsEditText);
        String NewOptionDetails = NOD.getText().toString();
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
