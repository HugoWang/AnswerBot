package com.example.kaiwang.answerbot;

import java.util.ArrayList;

public class Rate {
	public String body;
	public String details;

	public Rate(String body, String details) {
		this.body = body;
		this.details = details;
	}

	public static ArrayList<Rate> getRates() {
		ArrayList<Rate> rates = new ArrayList<Rate>();
		rates.add(new Rate("Cost of living", "Cost of living is the cost of maintaining a certain standard of living in different geographic areas."));
		rates.add(new Rate("Crime", "How common is crime in the option? Crimes are illegal acts for which someone can be punished by the government."));
		rates.add(new Rate("Quality of life", "Quality of life refers to the general well-being of individuals and societies. It includes for example international development, health care, politics and employment."));
		return rates;
	}
}
