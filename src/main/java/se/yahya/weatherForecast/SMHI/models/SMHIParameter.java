package se.yahya.weatherForecast.SMHI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class SMHIParameter {
    @JsonProperty("name")
    private String name;

    @JsonProperty("values")
    private List<Float> values;

    @JsonProperty("unit")
    private String unit;

    public String getName() {
        return name;
    }

    public List<Float> getValues() {
        return values;
    }


    public String getUnit() {
        return unit;
    }
}