package se.yahya.weatherForecast.SMHI.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class SMHIProps{
    public ArrayList<SMHITimeSeriesData> timeSeries;

    public ArrayList<SMHITimeSeriesData> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(ArrayList<SMHITimeSeriesData> timeSeries) {
        this.timeSeries = timeSeries;
    }
}
