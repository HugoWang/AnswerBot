package com.example.kaiwang.answerbot;

import java.util.ArrayList;
import java.util.Dictionary;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class CustomRatesAdapter extends ArrayAdapter<Rate> {
    private Context context;
    private SeekBar seekBar;
    TextView tvBody, tvDetail;
    Rate rate;

    public CustomRatesAdapter(Context context, ArrayList<Rate> rates) {
        super(context, R.layout.item_rate, rates);
        this.context = context;
     }

     @Override

     public View getView(int position, View convertView, ViewGroup parent) {
         View v = convertView;
        // Get the data item for this position
         //Rate rate = getItem(position);

         //RateHolder holder = new RateHolder();
         Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"RobotoCondensed-Regular.ttf");

        // Check if an existing view is being reused, otherwise inflate the view
         if (convertView == null) {

             Log.d("TEST1", "inifcheck convertView");

             LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             v = inflater.inflate(R.layout.item_rate, null);


         }
         else {
             Log.d("TEST2", "inelsecheck convertView");

         }

        // Lookup view for data population
         rate = getItem(position);
         seekBar = (SeekBar) v.findViewById(R.id.seekBar);
         tvBody = (TextView) v.findViewById(R.id.tvBody);
         tvDetail = (TextView) v.findViewById(R.id.tvDetails);

         seekBar.setProgress(rate.seek_value);
         seekBar.setMax(100);

         seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 rate.setSeek_value(progress);
             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {

             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {

             }
         });

         tvBody.setTypeface(tf);
         tvDetail.setTypeface(tf);
         tvBody.setText(rate.rate_body);
         tvDetail.setText(rate.rate_details);

         return v;
    }
}
