package com.answerbot.mobile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Result {

    public String solution_body;
    public String solution_details;
    public int solution_id;

    public Result() {
        solution_id=11;
        solution_body="";
        solution_details = "";
    }

    public void setSolution_id(int id){
        this.solution_id = id;
    }
    public void setSolution_body(String body){
        this.solution_body = body;
    }
    public void setSolution_details(String details){
        this.solution_details = details;
    }

}
