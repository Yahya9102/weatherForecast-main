package se.yahya.weatherForecast.dto;

import java.util.Date;

public class NewForecastDTO {

//DTO = DATA TRANSFER OBJECT
    private Date Datum;
    private int Hour;
    private float Temperatur;


    public Date getDatum() {
        return Datum;
    }

    public void setDatum(Date datum) {
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
