package se.yahya.weatherForecast.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import se.yahya.weatherForecast.dto.ForecastListDTO;
import se.yahya.weatherForecast.dto.NewForecastDTO;
import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.repositories.ForecastRepository;
import se.yahya.weatherForecast.services.ForecastService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ForecastController {
    @Autowired
    ForecastService forecastService;

    @Autowired
    ForecastRepository forecastRepository;


    @GetMapping("/api/forecasts/averageTemp/{date}")
    public ResponseEntity<List<Object[]>> getAverageTemperaturePerHour(@PathVariable("date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date) {

        List<Object[]> averageTempData = forecastRepository.findAverageTempPerHour(date);
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
    public ResponseEntity<Forecast> update(@PathVariable UUID id, @RequestBody NewForecastDTO newForecastDTO) throws IOException {


        //Mappar från DTO till -> entitet

        var forecast = new Forecast();

        forecast.setId(id);
        forecast.setUpdated(newForecastDTO.getDatum());
        forecast.setPredictionDate(newForecastDTO.getDatum());
        forecast.setPredictionHour(newForecastDTO.getHour());
        forecast.setPredictionTemperature(newForecastDTO.getTemperatur());
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
