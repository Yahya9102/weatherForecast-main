package se.yahya.weatherForecast.VisualCrossing.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)

public class VisualCrossingHourlyData {
    public String datetime;
    public int datetimeEpoch;
    public float temp;
    public int precip;
    public int precipprob;
    public int snow;
    public Object preciptype;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getDatetimeEpoch() {
        return datetimeEpoch;
    }

    public void setDatetimeEpoch(int datetimeEpoch) {
        this.datetimeEpoch = datetimeEpoch;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getPrecip() {
        return precip;
    }

    public void setPrecip(int precip) {
        this.precip = precip;
    }

    public int getPrecipprob() {
        return precipprob;
    }

    public void setPrecipprob(int precipprob) {
        this.precipprob = precipprob;
    }

    public int getSnow() {
        return snow;
    }

    public void setSnow(int snow) {
        this.snow = snow;
    }

    public Object getPreciptype() {
        return preciptype;
    }

    public void setPreciptype(Object preciptype) {
        this.preciptype = preciptype;
    }
}