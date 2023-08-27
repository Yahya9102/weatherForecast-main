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
import java.util.ArrayList;
import java.util.List;

@Service
public class VisualCrossingApiSetup {


     @Autowired
     MongoDBConnection mongoDBConnection;
    private static String API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/Stockholm/today/tomorrow?unitGroup=metric&include=days%2Ccurrent%2Chours&key=CBMMVHXH6GZ7LNK2C8Z9343E6&contentType=json";
    //&days=2&aqi=no&alerts=no
    public void gettingAPI() throws IOException {

        var objectmapper = new ObjectMapper();
        var database = mongoDBConnection.getDatabase();
        URL url = new URL(API_URL);
        ObjectMapper objectMapper = new ObjectMapper();
        VisualCrossingAPIProps visualCrossingAPIProps = objectmapper.readValue(url, VisualCrossingAPIProps.class);
        MongoCollection<Document> collection = database.getCollection("visualCrossing");
        List<Document> dataPoints = new ArrayList<>();

        for (VisuallCrossingDayData day : visualCrossingAPIProps.getDays()) {
            String validTime = day.getDatetime();

            for (VisualCrossingHourlyData hour : day.getHours()) {
                String hourDatetime = hour.getDatetime();
                Double hourTemp = hour.getTemp();

                Document dataPoint = new Document()
                        .append("tid", validTime)
                        .append("hourDatetime", hourDatetime)
                        .append("hourTemp", hourTemp);
                dataPoints.add(dataPoint);
            }
        }

            Document forecastDoc = new Document()
                    .append("data", dataPoints);
            collection.insertOne(forecastDoc);



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

