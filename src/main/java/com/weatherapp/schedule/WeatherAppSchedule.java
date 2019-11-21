package com.weatherapp.schedule;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.weatherapp.repo.WeatherDataRepo;

@Component
public class WeatherAppSchedule {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WeatherDataRepo weatherDataRepo;
	
	@Scheduled(cron="* 05 0 * * *") // Run job at 00:05 every day
	public void dbHouseKeep() {
		logger.info("Removing weather data older than 3 days.");
		weatherDataRepo.deleteByDateLessThanEqual(LocalDate.now().minusDays(3)); // deducted 3 days from the current date to remove 3 days old data
	}
}
