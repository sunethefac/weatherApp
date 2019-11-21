package com.weatherapp.repo;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.weatherapp.model.WeatherData;

@Repository
public interface WeatherDataRepo extends MongoRepository<WeatherData, String>{

	Optional<WeatherData> findByDateAndCityName(LocalDate date, String cityName);
	
	void deleteByDateLessThanEqual(LocalDate date);
	
}
