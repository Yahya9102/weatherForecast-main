package se.yahya.weatherForecast.SMHI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class SMHIProps {
    @JsonProperty("timeSeries")
    private List<SMHITimeSeriesData> timeSeries;

    @JsonProperty("timeSeries")
    public List<SMHITimeSeriesData> getTimeSeries() {
        return timeSeries;
    }

    @JsonProperty("timeSeries")
    public void setTimeSeries(List<SMHITimeSeriesData> timeSeries) {
        this.timeSeries = timeSeries;
    }
}
