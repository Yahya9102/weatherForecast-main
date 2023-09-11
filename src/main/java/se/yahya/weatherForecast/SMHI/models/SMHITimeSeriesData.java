package se.yahya.weatherForecast.SMHI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SMHITimeSeriesData{
    public Date validTime;


    public ArrayList<SMHIParameter> parameters;

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public ArrayList<SMHIParameter> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<SMHIParameter> parameters) {
        this.parameters = parameters;
    }
}

