package se.yahya.weatherForecast.dto;

import se.yahya.weatherForecast.models.DataSource;

import java.time.LocalDate;

public class CreateForecastDTO {

    private LocalDate datum;

    private int hour;

    private float temperatur;

    DataSource dataSource;

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public float getTemperatur() {
        return temperatur;
    }

    public void setTemperatur(float temperatur) {
        this.temperatur = temperatur;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
