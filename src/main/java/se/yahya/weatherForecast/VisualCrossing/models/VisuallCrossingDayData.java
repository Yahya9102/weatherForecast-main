package se.yahya.weatherForecast.VisualCrossing.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "days"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisuallCrossingDayData {
    @JsonProperty("datetime")
    private String datetime;

    @JsonProperty("temp")
    private Double temp;

    @JsonProperty("hours")
    private List<VisualCrossingHourlyData> hours;

    @JsonProperty("datetime")
    public String getDatetime() {
        return datetime;
    }

    @JsonProperty("datetime")
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @JsonProperty("temp")
    public Double getTemp() {
        return temp;
    }

    @JsonProperty("temp")
    public void setTemp(Double temp) {
        this.temp = temp;
    }

    @JsonProperty("hours")
    public List<VisualCrossingHourlyData> getHours() {
        return hours;
    }

    @JsonProperty("hours")
    public void setHours(List<VisualCrossingHourlyData> hours) {
        this.hours = hours;
    }
}
