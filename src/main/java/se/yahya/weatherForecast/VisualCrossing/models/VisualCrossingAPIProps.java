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
public class VisualCrossingAPIProps {

    @JsonProperty("days")
    private List<VisuallCrossingDayData> days;

    @JsonProperty("days")
    public List<VisuallCrossingDayData> getDays() {
        return days;
    }
    @JsonProperty("days")
    public void setDays(List<VisuallCrossingDayData> days) {
        this.days = days;
    }

    /*
    @JsonProperty("datetime")
    public List<VisualCrossingAPIProps> getHours() {
        return hours;
    }

    @JsonProperty("datetime")
    public void setHours(List<VisualCrossingAPIProps> hours) {
        this.hours = hours;
    }


     */
    // Här kan du inkludera andra egenskaper om det behövs
}

/*


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "datetime",
        "temp"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualCrossingAPIProps {

    @JsonProperty("datetime")
    private String datetime;

    @JsonProperty("temp")
    private Integer temp;

    @JsonProperty("datetime")
    public String getDatetime() {
        return datetime;
    }

    @JsonProperty("datetime")
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @JsonProperty("temp")
    public Integer getTemp() {
        return temp;
    }

    @JsonProperty("temp")
    public void setTemp(Integer temp) {
        this.temp = temp;
    }
}

 */