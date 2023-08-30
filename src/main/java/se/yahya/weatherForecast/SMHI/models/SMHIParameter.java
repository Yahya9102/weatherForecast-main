package se.yahya.weatherForecast.SMHI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SMHIParameter{
    public String name;

    public ArrayList<Float> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Float> getValues() {
        return values;
    }

    public void setValues(ArrayList<Float> values) {
        this.values = values;
    }
}
