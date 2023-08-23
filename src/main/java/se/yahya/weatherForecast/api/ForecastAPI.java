package se.yahya.weatherForecast.api;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.dbConnection.MongoDBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ForecastAPI {


     @Autowired
     MongoDBConnection mongoDBConnection;

    private static String API_URL = "http://api.weatherapi.com/v1/forecast.json?key=f8aa838b2d5f429493c170228232208&q=59.30996552541549,18.02151508449004";
        //&days=2&aqi=no&alerts=no
    public void gettingAPI() throws IOException {
     //   List<String> todayPrognoses = new ArrayList<>();
        //List<String> next24HoursPrognoses = new ArrayList<>();
        mongoDBConnection.getDatabase();
        String apiUrl = API_URL;


        var url = new URL(apiUrl);
        var connection = (HttpURLConnection) url.openConnection();
        connection.connect();

      //  int res = connection.getResponseCode();
        var bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        var responseBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            responseBuilder.append(line);
        }

        bufferedReader.close();
      // String data = responseBuilder.toString();

        //todayPrognoses.add(line);
      //  System.out.println(data);

        var database = mongoDBConnection.getDatabase();
        MongoCollection mongoCollection = database.getCollection("forecasts");
        var doc = new Document();
        doc = Document.parse(responseBuilder.toString());
        mongoCollection.insertOne(doc);
        System.out.println("Document inserted into database");

        mongoDBConnection.close();








        /*
        JSONObject jsonObject = new JSONObject(response);
        JSONArray hourlyForecast = jsonObject.getJSONObject("forecast").getJSONArray("forecastday")
                .getJSONObject(0).getJSONArray("hour");

        // Loopa igenom varje timme i de kommande 24 timmarnas prognos




        for (int i = 0; i < hourlyForecast.length(); i++) {
            JSONObject hourData = hourlyForecast.getJSONObject(i);
            String time = hourData.getString("time");
            double temperatureC = hourData.getDouble("temp_c");

            // Skriv ut tid och temperatur för varje timme
            System.out.println("Time: " + time);
            System.out.println("Temperature: " + temperatureC + "°C");
            System.out.println("--------------------------");
        }

        //printWeatherPrognoses("Prognose", todayPrognoses);

         */
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
    }

