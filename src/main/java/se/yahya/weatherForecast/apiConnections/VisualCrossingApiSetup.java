package se.yahya.weatherForecast.apiConnections;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.VisualCrossing.models.VisualCrossingHourlyData;
import se.yahya.weatherForecast.VisualCrossing.models.VisuallCrossingDayData;
import se.yahya.weatherForecast.VisualCrossing.models.VisualCrossingAPIProps;



import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VisualCrossingApiSetup {


    private static String API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/Stockholm/next24hours?unitGroup=metric&include=hours&key=CBMMVHXH6GZ7LNK2C8Z9343E6&contentType=json";

    //&days=2&aqi=no&alerts=no
    public void gettingAPI() throws IOException, ParseException {

        var objectmapper = new ObjectMapper();
        var url = new URL(API_URL);
        VisualCrossingAPIProps visualCrossingAPIProps = objectmapper.readValue(url, VisualCrossingAPIProps.class);


        List<VisuallCrossingDayData> timelist = visualCrossingAPIProps.getDays();
        long currentTimestamp = System.currentTimeMillis() / 1000;


        var localDateTime = LocalDateTime.now();

        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy MM dd");



        for (VisuallCrossingDayData hourDate : timelist) {
          //  Date validtime = hourDate.getDatetime();



            for (VisualCrossingHourlyData hourlyData : hourDate.getHours()) {
                long hourDatetimeEpoch = hourlyData.getDatetimeEpoch();
                if (hourDatetimeEpoch >= currentTimestamp && hourDatetimeEpoch <= currentTimestamp + 25 * 3600) {
                    String hourDatetime = hourlyData.getDatetime();
                    float hourTemp = hourlyData.getTemp();

                    String theDate = localDateTime.format(dateTimeFormatter);


                    if (hourDatetime.equals("00:00:00")) {
                        localDateTime = localDateTime.plusDays(1);
                        theDate = localDateTime.format(dateTimeFormatter);
                    }

                        System.out.println("*******************************");
                        System.out.println("The date is : " + theDate);
                        System.out.println("The hour is: " + hourDatetime);
                        System.out.println("Visual temp is: " + hourTemp);

                }
                }

            }
        }
}
