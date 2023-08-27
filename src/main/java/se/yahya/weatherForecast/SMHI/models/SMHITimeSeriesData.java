package se.yahya.weatherForecast.SMHI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class SMHITimeSeriesData {
    @JsonProperty("timeSeries")
    private List<SMHIProps> timeSeries;

    @JsonProperty("timeSeries")
    public List<SMHIProps> getTimeSeries() {
        return timeSeries;
    }

    @JsonProperty("timeSeries")
    public void setTimeSeries(List<SMHIProps> timeSeries) {
        this.timeSeries = timeSeries;
    }
}
