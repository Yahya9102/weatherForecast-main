package se.yahya.weatherForecast.VisualCrossing.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualCrossingHourlyData {

        @JsonProperty("datetime")
        private String datetime;

        @JsonProperty("temp")
        private Double temp;

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
    }

