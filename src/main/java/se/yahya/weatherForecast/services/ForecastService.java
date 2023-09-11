package se.yahya.weatherForecast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.models.DataSource;
import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.repositories.ForecastRepository;

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


        List<Map<String, Object>> transformedData = new ArrayList<>();

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




  /*
    public void deleteByProviderAndId(DataSource dataSource, UUID id) {
        forecastRepository.deleteByProviderAndId(dataSource,id);
    }

   */
}


