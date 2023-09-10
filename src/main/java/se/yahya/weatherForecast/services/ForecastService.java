package se.yahya.weatherForecast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.dto.ForecastAverageDTO;
import se.yahya.weatherForecast.models.DataSource;
import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.repositories.ForecastRepository;


import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Service
public class ForecastService {

    @Autowired
    private ForecastRepository forecastRepository;


    public List<Forecast> getForecasts(){
    return forecastRepository.findAll();
    }
    public Forecast add(Forecast forecast)  {
      forecastRepository.save(forecast);
        return forecast;
        }




    public List<Map<String, Object>> getAverageTemperaturePerHour(LocalDate date) {
        List<Object> averageTempData = forecastRepository.findAverageTempPerHour(date);

        // Create a new list to store the transformed data
        List<Map<String, Object>> transformedData = new ArrayList<>();

        // Iterate through the list of objects and build the map structure
        for (Object obj : averageTempData) {
            Object[] objArray = (Object[]) obj;

            Map<String, Object> dataMap = new LinkedHashMap<>();
            dataMap.put("Date", objArray[0]);
            dataMap.put("Hour", objArray[1]);
            dataMap.put("rainOrSnow", objArray[2]);
            dataMap.put("Temp", objArray[3]);

            transformedData.add(dataMap);
        }

        return transformedData;
    }



/*
    public List<Object> getAverageTemperaturePerHour(LocalDate date) {
        return forecastRepository.findAverageTempPerHour(date);
    }



 */
    public void update(Forecast forecastFromUser)  {
        forecastRepository.save(forecastFromUser);
    }

    public Optional<Forecast> get(UUID id) {
        return forecastRepository.findById(id);

    }


    public void delete(UUID id)  {
        forecastRepository.deleteById(id);

    }


    public List<Map> getAverageTempPerHourByProvider(LocalDate date, DataSource provider) {
        return forecastRepository.findAverageTempPerHourByProvider(date, provider);
    }

    }



/*
    public void delete(UUID id) {
        forecasts.removeIf(forecast -> forecast.getId().equals(id));
        forecasts.remove(forecasts);
        // saveForecastsToJson();
    }

*/



/*
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.models.Forecast;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForecastService {
    private static List<Forecast> forecasts = new ArrayList<>();

    public List<Forecast> getForecasts(){
        return forecasts;
    }
    public void add(Forecast weatherForecast){
        forecasts.add(weatherForecast);
       // saveForecastsToJson();
    }
    public void delete(UUID id) {
        forecasts.removeIf(forecast -> forecast.getId().equals(id));
       // saveForecastsToJson();
    }



/*
    public void update(UUID id, Forecast updatedForecast) {
        for (int i = 0; i < forecasts.size(); i++) {
            if (forecasts.get(i).getId().equals(id)) {
                forecasts.set(i, updatedForecast);
               // saveForecastsToJson();
                return;
            }
        }
    }


 */


