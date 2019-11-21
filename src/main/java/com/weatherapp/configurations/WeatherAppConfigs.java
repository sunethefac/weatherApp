package com.weatherapp.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name="scheduling.enabled", matchIfMissing=true)
public class WeatherAppConfigs {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public WeatherAppConfigs() {
		logger.info("Weather App Configuration.");
	}
}
