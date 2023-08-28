package se.yahya.weatherForecast.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yahya.weatherForecast.dbConnection.dbMethods.ForecastDatabaseFunctions;
import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.services.ForecastService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ForecastController {
    @Autowired
    ForecastService forecastService;


    @GetMapping("/api/forecasts")
    public ResponseEntity<List<Forecast>> getAll() {
        return new ResponseEntity<>(forecastService.getForecasts(), HttpStatus.OK);
    }


    @GetMapping("/api/forecasts/{id}")
        public ResponseEntity<Forecast> Get(@PathVariable UUID id){
        Optional<Forecast> forecasts = forecastService.get(id);
        if (forecasts.isPresent()) return ResponseEntity.ok(forecasts.get());
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/api/forecasts/{id}")
    public ResponseEntity<Forecast> update(@PathVariable UUID id, @RequestBody Forecast forecast) throws IOException {
      forecastService.update(forecast);
      return ResponseEntity.ok(forecast);

    }

    @PostMapping("/api/forecasts")
    public ResponseEntity<Forecast> create(@RequestBody Forecast forecast) throws IOException {
        forecast.setId(UUID.randomUUID());
        forecastService.add(forecast);
        return ResponseEntity.ok(forecast);
    }

    @DeleteMapping("/api/forecasts/{id}")
    public  ResponseEntity<String> delete(@PathVariable UUID id) throws IOException {
        forecastService.delete(id);
        return ResponseEntity.ok("Deleted");
    }


}
