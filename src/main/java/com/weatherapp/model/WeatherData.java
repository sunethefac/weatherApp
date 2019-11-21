package com.weatherapp.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Document(collection = "weatherData")
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherData {

	@Id
	private String id;
	private String cityName;
	private String cityCode;
	private LocalDate date;
	private long time;
	private String summary;
	private String icon;
	private double temperatureMin;
	private double temperatureMax;
	private long sunriseTime;
	private long sunsetTime;
	private double humidity;
	
}
