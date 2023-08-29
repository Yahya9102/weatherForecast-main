package se.yahya.weatherForecast.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.apiConnections.SMHIApiSetup;
import se.yahya.weatherForecast.models.Forecast;


import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class ForecastService {


    private static List<Forecast> forecasts = new ArrayList<>();




    public ForecastService(){
        try {
            forecasts = readFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private List<Forecast> readFromFile() throws IOException {
        if(!Files.exists(Path.of("forecastPrediction.json"))) return new ArrayList<Forecast>();
        ObjectMapper objectMapper = getObjectMapper();
        var jsonStr = Files.readString(Path.of("forecastPrediction.json"));
        return  new ArrayList(Arrays.asList(objectMapper.readValue(jsonStr, Forecast[].class ) ));
    }


    private void writeAllToFile(List<Forecast> weatherPredictions) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, weatherPredictions);
        Files.writeString(Path.of("forecastPrediction.json"), stringWriter.toString());

    }


    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.registerModule(new JavaTimeModule());
        return mapper;
    }



    public List<Forecast> getForecasts(){
        return forecasts;
    }
    public void add(Forecast forecast) throws IOException {
        forecasts.add(forecast);
        writeAllToFile(forecasts);
    }


    public Forecast getByIndex(int i) {
        return forecasts.get(i);
    }

    public void update(Forecast forecastFromUser) throws IOException {
        //
        var foreCastInList = get(forecastFromUser.getId()).get();
        foreCastInList.setTemperature(forecastFromUser.getTemperature());
        foreCastInList.setDate(forecastFromUser.getDate());
        foreCastInList.setHour(forecastFromUser.getHour());

        writeAllToFile(forecasts);
    }

    public Optional<Forecast> get(UUID id) {
        return getForecasts().stream().filter(forecast -> forecast.getId().equals(id))
                .findFirst();
    }


    public void delete(UUID id) throws IOException {
        forecasts.removeIf(forecast -> forecast.getId().equals(id));
        writeAllToFile(forecasts);
        // saveForecastsToJson();
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

