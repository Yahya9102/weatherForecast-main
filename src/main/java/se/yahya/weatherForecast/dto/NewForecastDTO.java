package se.yahya.weatherForecast.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class NewForecastDTO {

//DTO = DATA TRANSFER OBJECT
    private LocalDate Datum;
    private int Hour;
    private float Temperatur;



    public LocalDate getDatum() {
        return Datum;
    }

    public void setDatum(LocalDate datum) {
        Datum = datum;
    }

    public int getHour() {
        return Hour;
    }

    public void setHour(int hour) {
        Hour = hour;
    }

    public float getTemperatur() {
        return Temperatur;
    }

    public void setTemperatur(float temperatur) {
        Temperatur = temperatur;
    }
}
