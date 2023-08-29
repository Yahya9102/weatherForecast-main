package se.yahya.weatherForecast.VisualCrossing.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@JsonIgnoreProperties(ignoreUnknown = true)

public class VisualCrossingAPIProps {
    public int queryCost;
    public double latitude;
    public double longitude;
    public String resolvedAddress;
    public String address;
    public String timezone;
    public int tzoffset;
    public ArrayList<VisuallCrossingDayData> days;
    public ArrayList<Object> alerts;
    public VisualCrossingApiCurrentCondition currentConditions;

    public int getQueryCost() {
        return queryCost;
    }

    public void setQueryCost(int queryCost) {
        this.queryCost = queryCost;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getResolvedAddress() {
        return resolvedAddress;
    }

    public void setResolvedAddress(String resolvedAddress) {
        this.resolvedAddress = resolvedAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getTzoffset() {
        return tzoffset;
    }

    public void setTzoffset(int tzoffset) {
        this.tzoffset = tzoffset;
    }

    public ArrayList<VisuallCrossingDayData> getDays() {
        return days;
    }

    public void setDays(ArrayList<VisuallCrossingDayData> days) {
        this.days = days;
    }

    public ArrayList<Object> getAlerts() {
        return alerts;
    }

    public void setAlerts(ArrayList<Object> alerts) {
        this.alerts = alerts;
    }

    public VisualCrossingApiCurrentCondition getCurrentConditions() {
        return currentConditions;
    }

    public void setCurrentConditions(VisualCrossingApiCurrentCondition currentConditions) {
        this.currentConditions = currentConditions;
    }
}