package com.example.kaiwang.answerbot;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class EntryActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Button start;
    TextView start_text;
    CheckBox male_check, female_check;
    EditText input_age;
    String my_age;
    int my_age_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        //Hide ActionBar
        ActionBar myActionBar=getSupportActionBar();
        myActionBar.hide();

        Typeface tf = Typeface.createFromAsset(getAssets(),"RobotoCondensed-Regular.ttf");
        start_text = (TextView)findViewById(R.id.start_text);
        start_text.setTypeface(tf);

        start = (Button)findViewById(R.id.start_btn);
        start.setTypeface(tf);

        male_check = (CheckBox)findViewById(R.id.male_check);
        female_check = (CheckBox)findViewById(R.id.female_check);
        male_check.setTypeface(tf);
        female_check.setTypeface(tf);

        input_age = (EditText)findViewById(R.id.editAge);
        input_age.setTypeface(tf);


        male_check.setOnCheckedChangeListener(this);
        female_check.setOnCheckedChangeListener(this);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_age = input_age.getText().toString();
                my_age_value = Integer.parseInt(input_age.getText().toString());
                if ((male_check.isChecked() || female_check.isChecked()) && (my_age.length() != 0) && (my_age_value < 110)) {
                    Intent toEntry = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(toEntry);
                } else if ((male_check.isChecked() || female_check.isChecked()) && (my_age.length() != 0) && (my_age_value > 110)) {
                    Toast.makeText(getApplicationContext(), "Your age is unreliable!", Toast.LENGTH_SHORT).show();
                } else if ((!(male_check.isChecked() || female_check.isChecked())) && (my_age.length() != 0)) {
                    Toast.makeText(getApplicationContext(), "Your gender is missing!", Toast.LENGTH_SHORT).show();
                }
                else if ((male_check.isChecked() || female_check.isChecked()) && (my_age_value == 0)) { //does not work properly
                    Toast.makeText(getApplicationContext(), "Your age is missing!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Your age and gender are missing!", Toast.LENGTH_SHORT).show(); // does not work properly
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.male_check:
                if (male_check.isChecked() && isChecked) {
                    female_check.setChecked(false);
                    Log.d("TEST","male is checked");
                    return;
                }
                break;
            case R.id.female_check:
                if (female_check.isChecked() && isChecked) {
                    male_check.setChecked(false);
                    Log.d("TEST", "female is checked");
                    return;
                }
                break;
            default:
                break;
        }
    }
}
