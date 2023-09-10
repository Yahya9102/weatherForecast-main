package se.yahya.weatherForecast.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import se.yahya.weatherForecast.dto.ForecastAverageDTO;
import se.yahya.weatherForecast.dto.ForecastListDTO;
import se.yahya.weatherForecast.dto.NewForecastDTO;
import se.yahya.weatherForecast.models.DataSource;
import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.repositories.ForecastRepository;
import se.yahya.weatherForecast.services.ForecastService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.stream.Collectors;

@RestController
public class ForecastController {
    @Autowired
    ForecastService forecastService;





    @GetMapping("/api/forecasts/averageTemp/{date}")
    public ResponseEntity<List<Map<String, Object>>> getAverageTemperaturePerHour(@PathVariable("date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date) {
        List<Map<String, Object>> averageTempData = forecastService.getAverageTemperaturePerHour(date);
        return new ResponseEntity<>(averageTempData, HttpStatus.OK);
    }


/*

    @GetMapping("/api/forecasts/averageTemp/{date}")
    public ResponseEntity<List<Object>> getAverageTemperaturePerHour(@PathVariable("date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date) {
        List<Object> averageTempData = forecastService.getAverageTemperaturePerHour(date);
        return new ResponseEntity<>(averageTempData, HttpStatus.OK);
    }



 */



    @GetMapping("/api/forecasts/averageTempByProvider/{date}/{provider}")
    public ResponseEntity<List<Map>> getAverageTemperaturePerHourByProvider(
            @PathVariable("date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date,
            @PathVariable("provider") DataSource provider)  {
        List<Map> averageTempData = forecastService.getAverageTempPerHourByProvider(date, provider);

        return new ResponseEntity<>(averageTempData, HttpStatus.OK);
    }


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
    public ResponseEntity<NewForecastDTO> update(@PathVariable UUID id, @RequestBody NewForecastDTO newForecastDTO )  {
       var forecast = new Forecast();
        forecast.setId(id);
        forecast.setPredictionDate(newForecastDTO.getDatum());
        forecast.setPredictionHour(newForecastDTO.getHour());
        forecast.setPredictionTemperature(newForecastDTO.getTemperatur());

        forecastService.update(forecast);
      return ResponseEntity.ok(newForecastDTO);

    }

    @PostMapping("/api/forecasts")
    public ResponseEntity<Forecast> create(@RequestBody Forecast forecast)  {
        forecastService.add(forecast);
        return ResponseEntity.ok(forecast);
    }


    @DeleteMapping("/api/forecasts/{id}")
    public  ResponseEntity delete(@PathVariable UUID id) throws IOException {

        forecastService.delete(id);
       return ResponseEntity.ok("delete");
    }




}
