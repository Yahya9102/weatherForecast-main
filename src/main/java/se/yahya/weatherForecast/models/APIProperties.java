package se.yahya.weatherForecast.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;

@Service

public class APIProperties {
    public static class Region {
        @JsonProperty("region")
        private String region;
        private String localtime;

        public String getRegion() {
            return region;
        }

        public String getLocaltime() {
            return localtime;
        }
    }

    public static class Current {
        @JsonProperty("temp_c")
        private double temperatureCelsius;

        public double getTemperatureCelsius() {
            return temperatureCelsius;
        }
    }

    public static class ForecastDay {
        private String date;

        @JsonProperty("day")
        private DayData dayData;

        public String getDate() {
            return date;
        }

        public DayData getDayData() {
            return dayData;
        }
    }

    public static class DayData {
        @JsonProperty("avgtemp_c")
        private double averageTemperatureCelsius;

        public double getAverageTemperatureCelsius() {
            return averageTemperatureCelsius;
        }
    }

    public static class Forecast {
        private ForecastDay[] forecastday;

        public ForecastDay[] getForecastday() {
            return forecastday;
        }
    }

    private Region region; // Här är ändringen
    private Current current;
    private Forecast forecast;

    public Region getRegion() { // Här är ändringen
        return region;
    }

    public Current getCurrent() {
        return current;
    }

    public Forecast getForecast() {
        return forecast;
    }
}

   /* public static class Location {
        @JsonProperty("name")
        private String name;
        private String localtime;

        public String getName() {
            return name;
        }

        public String getLocaltime() {
            return localtime;
        }
    }

    public static class Current {
        @JsonProperty("temp_c")
        private double temperatureCelsius;

        public double getTemperatureCelsius() {
            return temperatureCelsius;
        }
    }

    public static class ForecastDay {
        private String date;

        @JsonProperty("day")
        private DayData dayData;

        public String getDate() {
            return date;
        }

        public DayData getDayData() {
            return dayData;
        }
    }

    public static class DayData {
        @JsonProperty("avgtemp_c")
        private double averageTemperatureCelsius;

        public double getAverageTemperatureCelsius() {
            return averageTemperatureCelsius;
        }
    }

    public static class Forecast {
        private ForecastDay[] forecastday;

        public ForecastDay[] getForecastday() {
            return forecastday;
        }
    }

    private Location location;
    private Current current;
    private Forecast forecast;

    public Location getLocation() {
        return location;
    }

    public Current getCurrent() {
        return current;
    }

    public Forecast getForecast() {
        return forecast;
    }
}


    */