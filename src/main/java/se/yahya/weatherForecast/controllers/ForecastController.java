package se.yahya.weatherForecast.controllers;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yahya.weatherForecast.dbConnection.MongoDBConnection;
import se.yahya.weatherForecast.dbConnection.dbMethods.ForecastDatabaseFunctions;
import se.yahya.weatherForecast.dto.ForecastListDTO;
import se.yahya.weatherForecast.dto.NewForecastDTO;
import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.services.ForecastService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ForecastController {
    @Autowired
    ForecastService forecastService;

     @Autowired
     ForecastDatabaseFunctions forecastDatabaseFunctions;



    @GetMapping("/api/averageTemp")
    public ResponseEntity<List<Document>> getAllPredictions() {
        List<Document> predictions = forecastDatabaseFunctions.allPredictionsInMongoDB();
        return new ResponseEntity<>(predictions, HttpStatus.OK);
    }


    @GetMapping("/api/forecasts")
    public ResponseEntity<List<ForecastListDTO>> getAll() {
        return new ResponseEntity<>(forecastService.getForecasts().stream().map(forecast-> {var forecastListDTD = new ForecastListDTO();
            forecastListDTD.id = forecast.getId();
            forecastListDTD.Datum = forecast.getDate();
            forecastListDTD.Temperatur = forecast.getTemperature();
            forecastListDTD.Hour = forecast.getHour();
            return forecastListDTD;}).collect(Collectors.toList()),
            HttpStatus.OK);
    }


    @GetMapping("/api/forecasts/{id}")
        public ResponseEntity<Forecast> getOne(@PathVariable UUID id){
        Optional<Forecast> forecasts = forecastService.get(id);
        if (forecasts.isPresent()) return ResponseEntity.ok(forecasts.get());
        return ResponseEntity.notFound().build();
    }



    @PutMapping("/api/forecasts/{id}")
    public ResponseEntity<Forecast> update(@PathVariable UUID id, @RequestBody NewForecastDTO newForecastDTO) throws IOException {


        //Mappar från DTO till -> entitet

        var forecast = new Forecast();

        forecast.setId(id);
        forecast.setDate(newForecastDTO.getDatum());
        forecast.setHour(newForecastDTO.getHour());
        forecast.setTemperature(newForecastDTO.getTemperatur());
        //forecast.setLastModifiedBy("Yahya Hussein");


        forecastService.update(forecast);

      return ResponseEntity.ok(forecast);

    }

    @PostMapping("/api/forecasts")
    public ResponseEntity<Forecast> create(@RequestBody Forecast forecast) throws IOException {
        forecastService.add(forecast);
        return ResponseEntity.ok(forecast);
    }



    @DeleteMapping("/api/forecasts/{id}")
    public  ResponseEntity delete(@PathVariable UUID id) throws IOException {
        forecastService.delete(id);
       return ResponseEntity.ok("delete");
    }




}
