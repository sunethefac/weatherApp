package com.weatherapp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weatherapp.model.WeatherData;
import com.weatherapp.model.WeatherDataResponse;
import com.weatherapp.repo.WeatherDataRepo;

@Service
public class WeatherService {

	private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
	private static final String DARKSKY_BASE_URL="https://api.darksky.net/forecast/";
	private static final String URL_PARAMETERS="?exclude=currently,minutely,hourly,flags&units=si";
	
	@Autowired
	private WeatherDataRepo weatherDataRepo;
	
	@Value("${darksky.secret.key}")
	private String secretKey;
	
	@Value("#{'${weather.cities}'.split('#')}") 
	private List<String> cityDetails;
	
	public List<WeatherData> getWeatherData(){
		RestTemplate restTemplate = new RestTemplate();
		LocalDate today = LocalDate.now();
		ZoneId zoneId = ZoneId.systemDefault();
		long unixTime = LocalDateTime.now().atZone(zoneId).toEpochSecond();
		List<WeatherData> weatherDetails = new ArrayList<>();
		WeatherData weatherData = null;
		
		for (String city : cityDetails) { // Iterate through the list of cities to get weather data
			String[] cityData = city.split("\\|");
			logger.info("Retrieving weather data of {} for {} from DB.", cityData[0], today);
			Optional<WeatherData> weatherDataFromDB = weatherDataRepo.findByDateAndCityName(LocalDate.now(), city.split("\\|")[0]); // Retrieve weather data from the DB
			
			if(weatherDataFromDB.isPresent()) { // Check if the weather data is available in the DB
				logger.info("Weather data of {} for {} found in the DB", cityData[0], today);
				weatherData = weatherDataFromDB.get(); 
			} else {
				logger.info("Weather data of {} for {} not found in the DB", cityData[0], today);
				try {
					logger.info("Retrieving Weather data of {} for {} from API", cityData[0], today);
					String uri = DARKSKY_BASE_URL + secretKey + "/"+ cityData[1] + "," + unixTime + URL_PARAMETERS;
					WeatherDataResponse weatherDataResponse = restTemplate.getForObject(uri, WeatherDataResponse.class); // Retrieve weather data from the API
					logger.info("Retrieved Weather data of {} for {} from API", cityData[0], today);
					weatherData = weatherDataResponse.getDaily().getData().get(0); 
					weatherData.setCityName(cityData[0]); // Add city name
					weatherData.setDate(today); // Add the date of the weather result
					weatherData.setCityCode(cityData[0].replace(", ", ""));
					weatherData = weatherDataRepo.save(weatherData); // Save the weather data to the DB 
				} catch (Exception e) {
					logger.warn("Weather Data Retrieval For : '{}' Failed.", cityData[0]);
				}
			}
			
			if (weatherData != null)
				weatherDetails.add(weatherData);
			
		}
		
		return weatherDetails;
	}

}
