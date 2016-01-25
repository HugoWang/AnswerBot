package com.example.kaiwang.answerbot;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomRatesAdapter extends ArrayAdapter<Rate> {
    public CustomRatesAdapter(Context context, ArrayList<Rate> rates) {
        super(context, 0, rates);
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
         Rate rate = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
         if (convertView == null) {
             convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_rate, parent, false);
         }
         Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"RobotoCondensed-Regular.ttf");
        // Lookup view for data population
         TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
         TextView tvDetail = (TextView) convertView.findViewById(R.id.tvDetails);
         tvBody.setTypeface(tf);
         tvDetail.setTypeface(tf);
        // Populate the data into the template view using the data object
         tvBody.setText(rate.rate_body);
         tvDetail.setText(rate.rate_details);
        // Return the completed view to render on screen
         return convertView;
    }
}
