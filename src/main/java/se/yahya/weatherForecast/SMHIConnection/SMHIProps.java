package se.yahya.weatherForecast.SMHIConnection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SMHIProps {
    @JsonProperty("timeSeries")
    private List<TimeSeriesData> timeSeries;

    @JsonProperty("timeSeries")
    public List<TimeSeriesData> getTimeSeries() {
        return timeSeries;
    }

    @JsonProperty("timeSeries")
    public void setTimeSeries(List<TimeSeriesData> timeSeries) {
        this.timeSeries = timeSeries;
    }
}
