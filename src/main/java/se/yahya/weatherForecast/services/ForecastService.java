package se.yahya.weatherForecast.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.models.Forecast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ForecastService {
    private static List<Forecast> forecasts = new ArrayList<>();

/*
    //Förberedelser för nästa del av kursen med JSON

    private static String json = "forecast.json";
    private ObjectMapper objectMapper;


    public ForecastService(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
      //  loadForecastsFromJson();
    }
    //----


 */

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

    public void update(UUID id, Forecast updatedForecast) {
        for (int i = 0; i < forecasts.size(); i++) {
            if (forecasts.get(i).getId().equals(id)) {
                forecasts.set(i, updatedForecast);
               // saveForecastsToJson();
                return;
            }
        }
    }


    //Föreberedelser för JSON del
    /*
    private void loadForecastsFromJson() throws IOException {
        var file = new File(json);
        forecasts = objectMapper.readValue(file, new TypeReference<>() {});

    }

    private void saveForecastsToJson() throws IOException {
        objectMapper.writeValue(new File(json), forecasts);
    }

     //----

     */
}
