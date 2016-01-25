package com.example.kaiwang.answerbot;

/**
 * Created by Juho on 12.12.2015.
 */
/**Still working with this*/

import android.app.Activity;
import android.os.Bundle;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class RequestServer extends Activity{
    private static final String BASE_URL = "http://dss.simohosio.com/api/";
    private static final String GET_ALL_QUESTIONS_URL = BASE_URL + "getquestions.php";
    private static final String GET_QUESTION_URL = BASE_URL + "getquestion.php?"; //need question_id
    private static final String GET_CRITERIAS_URL = BASE_URL + "getcriteria.php?"; //need question_id
    private static final String GET_SOLUTIONS_URL = BASE_URL + "getsolutions.php?"; //need question_id
    private static final String GET_RECOMMENDATIONS_URL = "http://dss.simohosio.com/getsupport.php?"; //need qid ?
    private AsyncHttpClient client;
    private StringEntity entity;

    public RequestServer(String s) {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    client = new AsyncHttpClient();
    RequestParams params = new RequestParams();
    client.get("http://dss.simohosio.com/api/postsolution.php", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("notes", "Test api support");
                    entity = new StringEntity(jsonParams.toString());
                    //client.post(entity);
                   /** String response = (new String(responseBody, "UTF-8"));
                    Log.d("dfgh", response);
                    JSONObject jObject = new JSONObject(response);
                    String user_id = jObject.getString("user_id");
                    String question_id = jObject.getString("question_id");

                    if (!jObject.has("flic_id")) {
                        Intent i = new Intent();
                        Log.d("gdsg", "no flic_id");
                    } else {
                        String flic_id = jObject.getString("flic_id");
                        String status = jObject.getString("status");
                        Log.d("gdsg", "has flic_id: " + flic_id + " " + status);
                        Intent info = new Intent();
                        info.putExtra("flic_id", flic_id);
                        info.putExtra("status", status);
                    }
                    **/
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
             // do something
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });


/**
 );
 public static void getQuestion(int question_id, RequestParams params, AsyncHttpResponseHandler responseHandler) {
 client.get(GET_QUESTION_URL + question_id, params, responseHandler);

 }
 public static void getQuestions(RequestParams params, AsyncHttpResponseHandler responseHandler) {
 client.get(GET_ALL_QUESTIONS_URL, params, responseHandler);
 }
 public static void getCriterias(int question_id, RequestParams params, AsyncHttpResponseHandler responseHandler) {
 client.get(GET_CRITERIAS_URL + question_id, params, responseHandler);
 }
 public static void getAnswers(int question_id, RequestParams params, AsyncHttpResponseHandler responseHandler) {
 client.get(GET_SOLUTIONS_URL + question_id, params, responseHandler);
 }
 public static void getRecommendations(RequestParams params, AsyncHttpResponseHandler responseHandler) {
 client.get(GET_RECOMMENDATIONS_URL, params, responseHandler);
 }
 }
 **/}
}