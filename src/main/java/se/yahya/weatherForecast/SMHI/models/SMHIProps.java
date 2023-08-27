package se.yahya.weatherForecast.SMHI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class SMHIProps {
    @JsonProperty("validTime")
    private String validTime;

    @JsonProperty("parameters")
    private List<SMHIParameter> parameters;

    public String getValidTime() {
        return validTime;
    }

    public List<SMHIParameter> getParameters() {
        return parameters;
    }
}
