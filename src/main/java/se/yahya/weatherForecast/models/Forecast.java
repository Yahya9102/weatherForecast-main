package se.yahya.weatherForecast.models;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class Forecast {
    private UUID id;
    private Date date;
    private int hour;
    private float temperature;



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
