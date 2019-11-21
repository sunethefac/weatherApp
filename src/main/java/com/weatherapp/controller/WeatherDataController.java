package com.weatherapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.weatherapp.model.WeatherData;
import com.weatherapp.service.WeatherService;

@Controller
public class WeatherDataController {

	private static final Logger logger = LoggerFactory.getLogger(WeatherDataController.class);
	
	@Autowired
	private WeatherService weatherService;
	
	@RequestMapping(value="/weather", method=RequestMethod.GET)
	public String get(Model model) {
		logger.info("Request for weather date recived.");
		List<WeatherData> weatherDetails = weatherService.getWeatherData();
		weatherDetails.stream()
					  .map(weatherData -> weatherData.getCityName().trim().toLowerCase())
					  .collect(Collectors.toList());
		model.addAttribute("weatherDetails", weatherDetails);
		model.addAttribute("cities", weatherDetails.stream()
												   .map(weatherData -> weatherData.getCityCode())
												   .collect(Collectors.toList()));
		model.addAttribute("icons", weatherDetails.stream()
												   .map(weatherData -> weatherData.getIcon())
												   .collect(Collectors.toList()));
		model.addAttribute("weatherDetails", weatherDetails);
		return "weatherreport";
	}
	
	
}
