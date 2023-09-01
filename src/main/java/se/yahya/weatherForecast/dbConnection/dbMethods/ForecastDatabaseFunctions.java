package se.yahya.weatherForecast.dbConnection.dbMethods;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.dbConnection.MongoDBConnection;
import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.services.ForecastService;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ForecastDatabaseFunctions {
    /*
    @Autowired
    ForecastService forecastService;
     */
    @Autowired
    MongoDBConnection mongoDBConnection;





    public List<Document> allPredictionsInMongoDB (){


        MongoDatabase database = mongoDBConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection("averageTemp");


        List<Bson> projections = new ArrayList<>();
        projections.add(Projections.include("Temp", "Tid", "Datun", "RainOrSnow"));
        projections.add(Projections.exclude("_id"));

        FindIterable<Document> documents = collection.find().projection(Projections.fields(projections));

        List<Document> predictions = new ArrayList<>();

        for (Document document : documents) {

            predictions.add(document);
        }

        return predictions;


    }











      /*  MongoDatabase database = mongoDBConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection("averageTemp");



        FindIterable<Document> documents = collection.find();

        for (var document : documents) {
            List<Document> dataArray = (List<Document>) document.get("averageTemp");
           // String id = document.get("_id").toString();

            for (int i = 0; i < dataArray.size(); i++) {
                Document data = dataArray.get(i);
                String tid = data.getString("tid");
                var dateTime = LocalDateTime.parse(tid.substring(0,tid.length()), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                var currentHour = LocalDateTime.now();
                if (dateTime.getHour() == currentHour.getHour() && dateTime.toLocalDate().isEqual(currentHour.toLocalDate())) {

                   // System.out.println("here is the id " + id);
                    System.out.println("here is the time " + data.get("tid") + "\n here is the temp " + data.get("Värden").toString().trim() );

                }
            }



        }

        return allPredictionsInMongoDB();


       */


    private  void insertForecastToMongoDB(Forecast forecast) {
      // MongoCollection<Document> collection = database.getCollection("smhi");

        var newDoc = new Document();
        newDoc.append("_id", forecast.getId().toString())
                .append("date", forecast.getDate())
                .append("hour", forecast.getHour())
                .append("temperature", forecast.getTemperature());

     //   collection.insertOne(newDoc);
        System.out.println("Forecast inserted into MongoDB");
    }


    private void deleteForecastInMongoDB(Scanner scan) {
       // MongoCollection<Document> collection = database.getCollection("forecasts");

        System.out.println("Type in ID to delete");
        UUID deleteID = UUID.fromString(scan.next());
        Bson filter = Filters.eq("_id", deleteID.toString());

       // collection.deleteOne(filter);
        System.out.println("Deleted from mongoDB");

    }


}

