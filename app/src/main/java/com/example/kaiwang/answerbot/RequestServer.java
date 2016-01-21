package com.example.kaiwang.answerbot;
/**
 * Created by Juho on 12.12.2015.
 */
/**Still working with this*/
/*
import org.json.*;
import com.loopj.android.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class RequestServer{
    private static final String BASE_URL = "http://dss.simohosio.com/api/";
    private static final String GET_ALL_QUESTIONS_URL = BASE_URL + "getquestions.php";
    private static final String GET_QUESTION_URL = BASE_URL + "getquestion.php?"; //need question_id
    private static final String GET_CRITERIAS_URL = BASE_URL + "getcriteria.php?"; //need question_id
    private static final String GET_SOLUTIONS_URL = BASE_URL + "getsolutions.php?"; //need question_id
    private static final String GET_RECOMMENDATIONS_URL = "http://dss.simohosio.com/getsupport.php?"; //need qid ?

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getQuestion(int question_id, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(GET_QUESTION_URL + question_id, params, responseHandler);

    }

    public static String getQuestions(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(GET_ALL_QUESTIONS_URL, params, responseHandler);
        {
            protected String doInBackground(String...params)
            {
                HttpURLConnection con = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(GET_ALL_QUESTIONS_URL);
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();
                    InputStream is = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(is));
                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);

                    }
                    return buffer.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                return null;
            }

            @Override
            protected void onPostExecute (String s){
                super.onPostExecute(s);
                txt.setText(s.toString());
        }
        }
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
  */