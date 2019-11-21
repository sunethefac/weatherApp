package com.weatherapp.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class DailyDataSummary {

	private List<WeatherData> data;
		
}
