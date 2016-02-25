package com.example.kaiwang.answerbot;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomSolutionAdapter extends ArrayAdapter<Result> {


    public CustomSolutionAdapter(Context context, ArrayList<Result> placeHolders) {
        super(context, R.layout.item_result, placeHolders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_result,parent,false);
        }
        Result result = getItem(position);

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "RobotoCondensed-Regular.ttf");

        TextView show_result = (TextView) convertView.findViewById(R.id.result_item);
        String result_body = result.solution_body;
        show_result.setText(result_body);
        show_result.setTypeface(tf);

        return convertView;
    }
}
