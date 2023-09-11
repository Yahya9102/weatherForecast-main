package se.yahya.weatherForecast.apiConnections;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.VisualCrossing.models.VisualCrossingHourlyData;
import se.yahya.weatherForecast.VisualCrossing.models.VisuallCrossingDayData;
import se.yahya.weatherForecast.VisualCrossing.models.VisualCrossingAPIProps;
import se.yahya.weatherForecast.models.DataSource;
import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.repositories.ForecastRepository;


import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VisualCrossingApiSetup {


    private float latitude = 59.334591F;
    private float longitude = 18.063240F;

    private  String API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + latitude + "%2C%20" + longitude + "/next24hours?unitGroup=metric&include=hours&key=CBMMVHXH6GZ7LNK2C8Z9343E6&contentType=json";

    @Autowired
    ForecastRepository forecastRepository;
    public void gettingAPI() throws IOException {
LocalDate createdDate = LocalDate.now();
        var objectmapper = new ObjectMapper();
        var url = new URL(API_URL);
        VisualCrossingAPIProps visualCrossingAPIProps = objectmapper.readValue(url, VisualCrossingAPIProps.class);



        List<VisuallCrossingDayData> timelist = visualCrossingAPIProps.getDays();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        LocalDate theDate = LocalDateTime.now().toLocalDate();

        for (VisuallCrossingDayData hourDate : timelist) {


            for (VisualCrossingHourlyData hourlyData : hourDate.getHours()) {
                long hourDatetimeEpoch = hourlyData.getDatetimeEpoch();
                if (hourDatetimeEpoch >= currentTimestamp && hourDatetimeEpoch <= currentTimestamp + 25 * 3600) {

                    String hourDatetime = hourlyData.getDatetime();



                    String[] timeParts = hourDatetime.split(":");

                    int predictionHour = Integer.parseInt(timeParts[0]);
                    float hourTemp = hourlyData.getTemp();
                    int precip = hourlyData.getPrecip();

                    boolean rainOrSnow = false;

                    if (precip >= 1){
                        rainOrSnow = true;
                    }

                    if (predictionHour == 0) {
                     theDate =  theDate.plusDays(1);
                    }
                    var forecastFromVisuall = new Forecast();
                    forecastFromVisuall.setRainOrSnow(rainOrSnow);
                    forecastFromVisuall.setCreated(createdDate);
                    forecastFromVisuall.setLatitude(latitude);
                    forecastFromVisuall.setLongitude(longitude);
                    forecastFromVisuall.setPredictionTemperature(hourTemp);
                    forecastFromVisuall.setPredictionHour(predictionHour);
                    forecastFromVisuall.setDataSource(DataSource.VisualCrossing);
                    forecastFromVisuall.setPredictionDate(theDate);
                    forecastRepository.save(forecastFromVisuall);



                }

                }

            }
        }
}
