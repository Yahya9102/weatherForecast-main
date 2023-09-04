package se.yahya.weatherForecast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.repositories.ForecastRepository;


import java.io.IOException;
import java.time.Instant;
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




    public void update(Forecast forecastFromUser) throws IOException {
        //
        var foreCastInList = get(forecastFromUser.getId()).get();
        foreCastInList.setPredictionTemperature(forecastFromUser.getPredictionTemperature());
        foreCastInList.setCreated(forecastFromUser.getCreated());
        foreCastInList.setPredictionHour(forecastFromUser.getPredictionHour());
        forecastRepository.save(forecastFromUser);

    }

    public Optional<Forecast> get(UUID id) {
        return forecastRepository.findById(id);

    }


    public void delete(UUID id) throws IOException {
        forecastRepository.deleteById(id);

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


