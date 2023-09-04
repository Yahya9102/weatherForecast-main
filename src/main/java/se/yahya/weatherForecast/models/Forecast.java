package se.yahya.weatherForecast.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import java.time.Instant;

import java.util.UUID;



@Entity
public class Forecast {



    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Instant created;
    private Instant updated;
    private int hour;
    private float temperature;
    private  boolean RaindOrSnow;
    private float Longtitude;
    private float latitud;
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean isRaindOrSnow() {
        return RaindOrSnow;
    }

    public void setRaindOrSnow(boolean raindOrSnow) {
        RaindOrSnow = raindOrSnow;
    }

    public float getLongtitude() {
        return Longtitude;
    }

    public void setLongtitude(float longtitude) {
        Longtitude = longtitude;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    private String lastModifiedBy;

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    private boolean rainOrSnow;

    public boolean isRainOrSnow() {
        return rainOrSnow;
    }

    public void setRainOrSnow(boolean rainOrSnow) {
        this.rainOrSnow = rainOrSnow;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
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
