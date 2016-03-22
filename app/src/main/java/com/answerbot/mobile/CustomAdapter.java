package com.answerbot.mobile;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class CustomAdapter extends ArrayAdapter<Questions>{

    public CustomAdapter(Context context, ArrayList<Questions> placeHolders) {
        super(context, com.answerbot.mobile.R.layout.activity_home_question, placeHolders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(com.answerbot.mobile.R.layout.activity_home_question,parent,false);
        }
        Questions plQuestion = getItem(position);
        if (plQuestion.sight){
            convertView.setVisibility(View.VISIBLE);
        }else{
            convertView.setVisibility(View.GONE);
        }
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "RobotoCondensed-Regular.ttf");

        TextView MainQuestion = (TextView) convertView.findViewById(com.answerbot.mobile.R.id.qquestion);

        String question= plQuestion.question_body;
        MainQuestion.setText(question);
        MainQuestion.setTypeface(tf);


        return convertView;
    }
}
