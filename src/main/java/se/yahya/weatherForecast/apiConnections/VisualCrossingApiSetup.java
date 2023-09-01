package se.yahya.weatherForecast.apiConnections;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.VisualCrossing.models.VisualCrossingHourlyData;
import se.yahya.weatherForecast.VisualCrossing.models.VisuallCrossingDayData;
import se.yahya.weatherForecast.dbConnection.MongoDBConnection;
import se.yahya.weatherForecast.VisualCrossing.models.VisualCrossingAPIProps;
//import se.yahya.weatherForecast.VisualCrossing.models.ForecastProps;


import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VisualCrossingApiSetup {


    //@Autowired
    //GettingAverageFromAPI gettingAverageFromAPI;

    @Autowired
    MongoDBConnection mongoDBConnection;
    private static String API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/Stockholm/next24hours?unitGroup=metric&include=hours&key=CBMMVHXH6GZ7LNK2C8Z9343E6&contentType=json";

    //&days=2&aqi=no&alerts=no
    public void gettingAPI() throws IOException, ParseException {

        var objectmapper = new ObjectMapper();
        URL url = new URL(API_URL);
        VisualCrossingAPIProps visualCrossingAPIProps = objectmapper.readValue(url, VisualCrossingAPIProps.class);


        List<VisuallCrossingDayData> timelist = visualCrossingAPIProps.getDays();
        long currentTimestamp = System.currentTimeMillis() / 1000;



        LocalDateTime localDateTime = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E MMM dd", Locale.ENGLISH);



        for (VisuallCrossingDayData hourDate : timelist) {
            Date validtime = hourDate.getDatetime();



            for (VisualCrossingHourlyData hourlyData : hourDate.getHours()) {
                long hourDatetimeEpoch = hourlyData.getDatetimeEpoch();
                if (hourDatetimeEpoch >= currentTimestamp && hourDatetimeEpoch <= currentTimestamp + 25 * 3600) {
                    String hourDatetime = hourlyData.getDatetime();
                    float hourTemp = hourlyData.getTemp();

                    String theDate = localDateTime.format(dateTimeFormatter);

                        System.out.println("*******************************");
                        System.out.println("The date is : " + theDate);
                        System.out.println("The hour is: " +  hourDatetime);
                        System.out.println("Visual temp is: " + hourTemp);


                }


                /*





                 */
            }
        }
    }
}




    /*
    private void printWeatherPrognoses(String prognose, List<String> prognoses) {
        var localtime = LocalTime.now();


        //Loopa genom varje JSON svar i min prognoses List
        for (String prognosis : prognoses) {
            var jsonObject = new JSONObject(prognosis);

            //Extrahera hour från datan i forecast
            JSONArray hourlyForecast = jsonObject.getJSONObject("forecast")
                    .getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour");




            //Loopa genom antal timmar som finns och skriv ut samtliga temperaturer
            for (int i = 0; i < hourlyForecast.length(); i++) {
                var hourData = hourlyForecast.getJSONObject(i);
                String time = hourData.getString("time");
                double temperatureC = hourData.getDouble("temp_c");


                var forecastTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                if (forecastTime.isAfter(localtime) && forecastTime.isBefore(localtime.plusHours(24))) {
                    System.out.println(" - Time: " + time);
                    System.out.println(" - Temperature: " + temperatureC + "°C");
                    System.out.println("--------------------------");
                }
            }
        }
    }


     */


