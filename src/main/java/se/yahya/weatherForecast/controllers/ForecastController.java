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


    @GetMapping("/api/forecasts/averageTemp/{date}")
    public ResponseEntity<?> getAverageTemperaturePerHour(@PathVariable("date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date) {
        try {
            List<Map<String, Object>> averageTempData = forecastService.getAverageTemperaturePerHour(date);
            if (averageTempData.isEmpty()) {
                return new ResponseEntity<>("No data found for the specified date.", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(averageTempData, HttpStatus.OK);
        } catch (Exception e) {

            String errorMessage = "Error occurred while fetching average temperature data.";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/api/forecasts/averageTempByProvider/{date}/{provider}")
    public ResponseEntity<?> getAverageTemperaturePerHourByProvider(
            @PathVariable("date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date,
            @PathVariable("provider") DataSource provider) {
        try {

            List<Map> averageTempData = forecastService.getAverageTempPerHourByProvider(date, provider);

            if (averageTempData.isEmpty()) {
                return new ResponseEntity<>("No data found for the specified date and provider.", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(averageTempData, HttpStatus.OK);
        } catch (Exception e) {

            String errorMessage = "Error occurred while processing your request.";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/api/forecasts")
    public ResponseEntity<?> getAll() {
        try {
            List<ForecastListDTO> forecastListDTOs = forecastService.getForecasts().stream()
                    .map(forecast -> {
                        var forecastListDTO = new ForecastListDTO();
                        forecastListDTO.id = forecast.getId();
                        forecastListDTO.Datum = forecast.getPredictionDate();
                        forecastListDTO.Temperatur = forecast.getPredictionTemperature();
                        forecastListDTO.Hour = forecast.getPredictionHour();
                        forecastListDTO.rainOrSnow = forecast.isRainOrSnow();
                        forecastListDTO.dataSource = forecast.getDataSource();
                        return forecastListDTO;
                    })
                    .collect(Collectors.toList());

            return new ResponseEntity<>(forecastListDTOs, HttpStatus.OK);
        } catch (Exception e) {

            String errorMessage = "An error occurred while fetching forecasts.";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/api/forecasts/{id}")
    public ResponseEntity<?> getOne(@PathVariable UUID id) {
        try {
            Optional<Forecast> forecast = forecastService.get(id);

            if (forecast.isPresent()) {
                return ResponseEntity.ok(forecast.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {

            String errorMessage = "Error occurred while fetching the forecast.";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/api/forecasts/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody NewForecastDTO newForecastDTO) {
        try {
            Optional<Forecast> optionalForecast = forecastService.get(id);

            if (!optionalForecast.isPresent()) {

                return ResponseEntity.notFound().build();
            }

            Forecast forecast = optionalForecast.get();
            LocalDate localDate = LocalDate.now();


            forecast.setPredictionDate(newForecastDTO.getDatum());
            forecast.setUpdated(localDate);
            forecast.setPredictionHour(newForecastDTO.getHour());
            forecast.setPredictionTemperature(newForecastDTO.getTemperatur());


            forecastService.update(forecast);

            return ResponseEntity.ok(forecast);
        } catch (Exception e) {

            String errorMessage = "Error occurred while updating the forecast.";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @PostMapping("/api/forecasts")
    public ResponseEntity<?> create(@RequestBody CreateForecastDTO createForecastDTO) {
        try {
            var forecast = new Forecast();
            LocalDate createdDate = LocalDate.now();
            forecast.setId(UUID.randomUUID());
            forecast.setCreated(createdDate);
            forecast.setDataSource(createForecastDTO.getDataSource());
            forecast.setPredictionDate(createForecastDTO.getDatum());
            forecast.setPredictionHour(createForecastDTO.getHour());
            forecast.setPredictionTemperature(createForecastDTO.getTemperatur());


            forecastService.add(forecast);


            return new ResponseEntity<>(forecast, HttpStatus.CREATED);
        } catch (Exception e) {

            String errorMessage = "Error while creating forecast";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @DeleteMapping("/api/forecasts/{id}")
    public  ResponseEntity delete(@PathVariable UUID id) throws IOException {

        forecastService.delete(id);
       return ResponseEntity.ok("Deleted");
    }




}
