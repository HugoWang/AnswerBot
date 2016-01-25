package com.example.kaiwang.answerbot;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GetRecomendActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recomend);
        AsyncHttpClient client = new AsyncHttpClient();
        /*
        JSONArray rateArray = null;
        ArrayList<Rate> arrayOfRates = new ArrayList<>();
        for (int i=0;i<rateArray.length();i++){
            try{
                Rate rate = new Rate();
                JSONObject buffer = rateArray.getJSONObject(i);
                rate.setRate_body(buffer.getString("criterion_body"));
                rate.setRate_details(buffer.getString("criterion_details"));
                arrayOfRates.add(rate);
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        // Create the adapter to convert the array to views
        CustomRatesAdapter adapter = new CustomRatesAdapter(this, arrayOfRates);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvRates);
        listView.setAdapter(adapter);
        */
        Typeface tf = Typeface.createFromAsset(getAssets(),"RobotoCondensed-Regular.ttf");
        TextView tips1 = (TextView)findViewById(R.id.tips1);
        TextView tips2 = (TextView)findViewById(R.id.tips2);
        tips1.setTypeface(tf);
        tips2.setTypeface(tf);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_recomend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_criteria) {
            Intent toAddCriteria = new Intent(getApplicationContext(), AddCriteriaActivity.class);
            startActivity(toAddCriteria);
        }
        else if (id == R.id.add_answer) {
            Intent toAddAnswer = new Intent(getApplicationContext(), AddOptionsActivity.class);
            startActivity(toAddAnswer);
        }
        else if (id == R.id.rate_answer) {
            Intent toRateAnswer = new Intent(getApplicationContext(), DonateKnowledgeActivity.class);
            startActivity(toRateAnswer);
        }
        else if (id == R.id.share_question) {
            String urlToShare = "http://dss.simohosio.com/new.php?type=s&qid=1";

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

            boolean facebookAppFound = false;
            List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
            for (ResolveInfo info : matches) {
                if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                    intent.setPackage(info.activityInfo.packageName);
                    facebookAppFound = true;
                    break;
                }
            }
            if (!facebookAppFound) {
                String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            }

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }
}
