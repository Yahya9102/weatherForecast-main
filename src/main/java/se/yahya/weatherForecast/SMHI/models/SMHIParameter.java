package se.yahya.weatherForecast.SMHI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class SMHIParameter{
    public String name;

    public ArrayList<Double> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Double> getValues() {
        return values;
    }

    public void setValues(ArrayList<Double> values) {
        this.values = values;
    }
}
