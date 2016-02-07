package com.example.kaiwang.answerbot;

public class Rate {
	public String rate_body;
	public String rate_details;
	public int rate_id;
	public String meta;
	public int seek_value;

	public Rate() {
		rate_id=11;
		rate_body="";
		rate_details = "";
		meta="";
		seek_value=0;
	}

	public void setRate_id(int id){
		this.rate_id = id;
	}
	public void setRate_body(String body){
		this.rate_body = body;
	}
	public void setRate_details(String details){
		this.rate_details = details;
	}
	public void setRate_Meta(String met){
		this.meta = met;
	}
	public void setSeek_value(int seek_value){
		this.seek_value = seek_value;
	}
}
