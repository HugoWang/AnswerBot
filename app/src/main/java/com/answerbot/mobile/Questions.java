package com.answerbot.mobile;

public class Questions {
    int question_id;
    String question_body;
    String user_id;
    String question_details;
    int quality_score;
    String meta;
    Boolean sight;

    public Questions(){
        question_id=99999;
        question_body="";
        user_id="";
        question_details="";
        quality_score=99999;
        meta="";
        sight=true;
    }
    public void setQuestion_id(int id){
        question_id = id;
    }
    public void setQuestion_body(String question){
        question_body = question;
    }
    public void setUser_id(String id){
        user_id = id;
    }
    public void setQuestion_details(String details){
        question_details = details;
    }
    public void setQuality_score(int score){
        quality_score = score;
    }
    public void setMeta(String met){
        meta = met;
    }
}
