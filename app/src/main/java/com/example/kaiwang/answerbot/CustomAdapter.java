package com.example.kaiwang.answerbot;


import android.content.Context;
import android.graphics.Typeface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

class CustomAdapter extends ArrayAdapter<Questions>{

    public CustomAdapter(Context context, ArrayList<Questions> placeHolders) {
        super(context, R.layout.activity_home_question, placeHolders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.activity_home_question,parent,false);
        }
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"RobotoCondensed-Regular.ttf");
        Questions plQuestion = getItem(position);
        TextView MainQuestion = (TextView) convertView.findViewById(R.id.qquestion);
        String question= plQuestion.question_body;
        MainQuestion.setText(question);
        MainQuestion.setTypeface(tf);
        return convertView;
    }
}
