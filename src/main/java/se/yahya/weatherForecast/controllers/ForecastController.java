package se.yahya.weatherForecast.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yahya.weatherForecast.dto.CreateForecastDTO;
import se.yahya.weatherForecast.dto.ForecastListDTO;
import se.yahya.weatherForecast.dto.NewForecastDTO;
import se.yahya.weatherForecast.models.DataSource;
import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.services.ForecastService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import java.util.stream.Collectors;

@RestController
public class ForecastController {
    @Autowired
    ForecastService forecastService;



    @GetMapping("/api/forecasts")
    public ResponseEntity<List<ForecastListDTO>> getAll() {
        return new ResponseEntity<>(forecastService.getForecasts().stream().map(forecast-> {var forecastListDTD = new ForecastListDTO();
            forecastListDTD.id = forecast.getId();
            forecastListDTD.Datum = forecast.getPredictionDate();
            forecastListDTD.Temperatur = forecast.getPredictionTemperature();
            forecastListDTD.Hour = forecast.getPredictionHour();
            forecastListDTD.rainOrSnow = forecast.isRainOrSnow();
            forecastListDTD.dataSource = forecast.getDataSource();
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
    public ResponseEntity<Forecast> update(@PathVariable UUID id, @RequestBody NewForecastDTO newForecastDTO )  {
        var forecast = forecastService.get(id).get();
        LocalDate localDate = LocalDate.now();
        forecast.setPredictionDate(newForecastDTO.getDatum());
        forecast.setUpdated(localDate);
        forecast.setPredictionHour(newForecastDTO.getHour());
        forecast.setPredictionTemperature(newForecastDTO.getTemperatur());

        forecastService.update(forecast);

        return ResponseEntity.ok(forecast);

    }


    @PostMapping("/api/forecasts")
    public ResponseEntity<Forecast> create(@RequestBody CreateForecastDTO createForecastDTO)  {
        var forecast = new Forecast();
        LocalDate createdDate = LocalDate.now();
        forecast.setId(UUID.randomUUID());
        forecast.setCreated(createdDate);
        forecast.setDataSource(createForecastDTO.getDataSource());
        forecast.setPredictionDate(createForecastDTO.getDatum());
        forecast.setPredictionHour(createForecastDTO.getHour());
        forecast.setPredictionTemperature(createForecastDTO.getTemperatur());

        forecastService.add(forecast);
        return ResponseEntity.ok(forecast);
    }


    @DeleteMapping("/api/forecasts/{id}")
    public  ResponseEntity delete(@PathVariable UUID id) throws IOException {

        forecastService.delete(id);
        return ResponseEntity.ok("delete");
    }

    @GetMapping("/api/forecasts/averageTemp/{date}")
    public ResponseEntity<List<Map<String, Object>>> getAverageTemperaturePerHour(@PathVariable("date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date) {
        List<Map<String, Object>> averageTempData = forecastService.getAverageTemperaturePerHour(date);
        return new ResponseEntity<>(averageTempData, HttpStatus.OK);
    }


    @GetMapping("/api/forecasts/averageTempByProvider/{date}/{provider}")
    public ResponseEntity<List<Map>> getAverageTemperaturePerHourByProvider(
            @PathVariable("date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date,
            @PathVariable("provider") DataSource provider)  {
        List<Map> averageTempData = forecastService.getAverageTempPerHourByProvider(date, provider);
        return new ResponseEntity<>(averageTempData, HttpStatus.OK);
    }

}
