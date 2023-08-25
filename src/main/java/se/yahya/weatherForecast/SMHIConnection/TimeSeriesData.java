package se.yahya.weatherForecast.SMHIConnection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSeriesData {
    @JsonProperty("validTime")
    private String validTime;

    @JsonProperty("parameters")
    private List<Parameter> parameters;

    public String getValidTime() {
        return validTime;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }
}
