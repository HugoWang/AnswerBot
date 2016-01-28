package com.example.kaiwang.answerbot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddCriteriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_criteria);
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
        EditText NCD = (EditText) findViewById(R.id.NewCriterionDetailsEditText);
        String NewCriterionDetails = NCD.getText().toString();
        com.loopj.android.http.RequestParams params = new RequestParams();
        params.add("user_id", "12345");
        params.add("question_id", "11");
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
    }
}
