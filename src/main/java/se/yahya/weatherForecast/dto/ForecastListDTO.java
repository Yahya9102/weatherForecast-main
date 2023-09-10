package se.yahya.weatherForecast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import se.yahya.weatherForecast.models.DataSource;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ForecastListDTO {

    public UUID id;


    public LocalDate Datum;

    public int Hour;

    public float Temperatur;


    public boolean rainOrSnow;

    public DataSource dataSource;


}
