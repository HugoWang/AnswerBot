package com.answerbot.mobile;

import android.app.Application;

import java.util.ArrayList;

public class App extends Application {
    private ArrayList<Questions> allQuestions;

    public App() {
        allQuestions = new ArrayList<>();
    }

    public synchronized void addQuestion(Questions question){
        allQuestions.add(question);
    }
    public synchronized ArrayList<Questions> getQuestions(){
        return allQuestions;
    }
    public synchronized Questions getQuestion(int i){
        return allQuestions.get(i);
    }
    public synchronized int getSize(){
        return allQuestions.size();
    }
}
