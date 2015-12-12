package com.example.kaiwang.answerbot;


import android.content.Context;
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

class CustomAdapter extends ArrayAdapter<String>{

    public CustomAdapter(Context context, ArrayList<String> placeHolders) {
        super(context, R.layout.activity_home_question, placeHolders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.activity_home_question, parent, false);

        String plQuestion = getItem(position);
        TextView MainQuestion = (TextView) customView.findViewById(R.id.qquestion);

        final LinearLayout buttonPanel = (LinearLayout) customView.findViewById(R.id.buttonPanel);
        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonPanel.getVisibility()==View.GONE) buttonPanel.setVisibility(View.VISIBLE);
                else buttonPanel.setVisibility(View.GONE);
            }
        });
        buttonPanel.setVisibility(View.GONE);
        MainQuestion.setText(plQuestion);
        return customView;
    }
}
