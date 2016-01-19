package com.example.kaiwang.answerbot;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GetRecomendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recomend);
        populateRatesList();

        Typeface tf = Typeface.createFromAsset(getAssets(),"RobotoCondensed-Regular.ttf");
        TextView tips1 = (TextView)findViewById(R.id.tips1);
        TextView tips2 = (TextView)findViewById(R.id.tips2);
        tips1.setTypeface(tf);
        tips2.setTypeface(tf);
    }

    private void populateRatesList() {
        // Construct the data source
        ArrayList<Rate> arrayOfRates = Rate.getRates();
        // Create the adapter to convert the array to views
        CustomRatesAdapter adapter = new CustomRatesAdapter(this, arrayOfRates);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvRates);
        listView.setAdapter(adapter);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
