package se.yahya.weatherForecast.VisualCrossing.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "address",
        "days"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastProps {

    @JsonProperty("address")
    private String address;

    @JsonProperty("days")
    //@Valid
    private List<VisualCrossingAPIProps> days;

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("days")
    public List<VisualCrossingAPIProps> getDays() {
        return days;
    }

    @JsonProperty("days")
    public void setDays(List<VisualCrossingAPIProps> days) {
        this.days = days;
    }

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
