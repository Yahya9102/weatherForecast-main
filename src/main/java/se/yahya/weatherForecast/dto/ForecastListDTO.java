package se.yahya.weatherForecast.dto;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class ForecastListDTO {
    public UUID id;
    public Instant Datum;
    public int Hour;
    public float Temperatur;

}
