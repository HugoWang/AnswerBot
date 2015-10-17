package com.example.feil.answerbot;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> placeHolders = new ArrayList<String>();
    ListView myListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final ListAdapter myAdapter = new CustomAdapter(this, placeHolders);
        myListView = (ListView)findViewById(R.id.myListView);
        myListView.setAdapter(myAdapter);
        Button btn = (Button) findViewById(R.id.askNewQuestion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText)findViewById(R.id.input);
                placeHolders.add(edit.getText().toString());
                edit.setText("");
            }
        });

    }


    public void addCriteria(View view) {
    }

    public void addOptions(View view) {
    }

    public void donateKnowledge(View view) {
    }

    public void getAnswers(View view) {
    }

    public void newQuestion(View view) {
    }
}

