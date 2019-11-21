package com.weatherapp.model;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class WeatherDataResponse {

	private String timezone;
	private DailyDataSummary daily;
	
}
