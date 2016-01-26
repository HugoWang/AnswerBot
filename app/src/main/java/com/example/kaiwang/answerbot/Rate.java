package com.example.kaiwang.answerbot;

import java.util.ArrayList;

public class Rate {
	public String rate_body;
	public String rate_details;
	public int rate_id;
	public String meta;

	public Rate() {
		rate_id=11;
		rate_body="";
		rate_details = "";
		meta="";
	}

	public void setRate_id(int id){
		rate_id = id;
	}
	public void setRate_body(String body){
		rate_body = body;
	}
	public void setRate_details(String details){
		rate_details = details;
	}
	public void setRate_Meta(String met){
		meta = met;
	}
}
