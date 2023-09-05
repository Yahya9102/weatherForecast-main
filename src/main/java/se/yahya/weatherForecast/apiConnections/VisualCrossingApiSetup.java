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


    private static String API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/Stockholm/next24hours?unitGroup=metric&include=hours&key=CBMMVHXH6GZ7LNK2C8Z9343E6&contentType=json";

    @Autowired
    ForecastRepository forecastRepository;
    //&days=2&aqi=no&alerts=no
    public void gettingAPI() throws IOException, ParseException {

        var objectmapper = new ObjectMapper();
        var url = new URL(API_URL);
        VisualCrossingAPIProps visualCrossingAPIProps = objectmapper.readValue(url, VisualCrossingAPIProps.class);


        List<VisuallCrossingDayData> timelist = visualCrossingAPIProps.getDays();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        var localDateTime = LocalDateTime.now();
        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate theDate = LocalDateTime.now().toLocalDate();

        for (VisuallCrossingDayData hourDate : timelist) {


            for (VisualCrossingHourlyData hourlyData : hourDate.getHours()) {
                long hourDatetimeEpoch = hourlyData.getDatetimeEpoch();
                if (hourDatetimeEpoch >= currentTimestamp && hourDatetimeEpoch <= currentTimestamp + 25 * 3600) {

                    String hourDatetime = hourlyData.getDatetime();
                    String[] timeParts = hourDatetime.split(":");
                    int predictionHour = Integer.parseInt(timeParts[0]);

                    float hourTemp = hourlyData.getTemp();



                    if (predictionHour == 0) {
                     theDate =  theDate.plusDays(1);

                    }

                    var forecastFromVisuall = new Forecast();
                    forecastFromVisuall.setPredictionTemperature(hourTemp);
                    forecastFromVisuall.setPredictionHour(predictionHour);
                    forecastFromVisuall.setDataSource(DataSource.VisualCrossing);
                    forecastFromVisuall.setPredictionDate(theDate);

                        forecastRepository.save(forecastFromVisuall);


                        System.out.println("*******************************");
                        System.out.println("The date is : " + theDate);
                        System.out.println("The hour is: " + hourDatetime);
                        System.out.println("Visual temp is: " + hourTemp);

                }

                }

            }
        }
}
