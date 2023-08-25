package se.yahya.weatherForecast.SMHIConnection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Parameter {
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