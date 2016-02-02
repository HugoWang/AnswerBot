package com.example.kaiwang.answerbot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.os.BaseBundle;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddOptionsActivity extends AppCompatActivity {
    Bundle bundle = getIntent().getExtras();
    String question_body;
    String question_id;
    String user_id;
/*
    if(!bundle.isEmpty()){
        question_id = bundle.getString("question_id");
        question_body = bundle.getString("question_body");
        user_id = bundle.getString("user_id");
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_options);
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
        EditText NOD =  (EditText) findViewById(R.id.NewOptionDetailsEditText);
        String NewOptionDetails = NOD.getText().toString();
        com.loopj.android.http.RequestParams params = new RequestParams();
        params.add("user_id", user_id);
        params.add("question_id", question_id);
        params.add("body", NewOptionBody);
        params.add("details", NewOptionBody);
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
}
}
