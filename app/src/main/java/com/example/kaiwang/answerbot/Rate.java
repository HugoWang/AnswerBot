package com.example.kaiwang.answerbot;

import java.util.ArrayList;

public class Rate {
	public String rate_body;
	public String rate_details;

	public Rate() {
		rate_body="";
		rate_details = "";
	}

	public void setRate_body(String body){
		rate_body = body;
	}
	public void setRate_details(String details){
		rate_details = details;
	}
}
