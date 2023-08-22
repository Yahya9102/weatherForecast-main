package se.yahya.weatherForecast.services;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import se.yahya.weatherForecast.dbConnection.MongoDBConnection;
import se.yahya.weatherForecast.models.Forecast;

import java.util.Scanner;
import java.util.UUID;

public class ForecastDatabaseFunctions {
    @Autowired
    ForecastService forecastService;
    @Autowired
    MongoDBConnection mongoDBConnection;

    private void allPredictionsInMongoDB (){
        MongoDatabase database = mongoDBConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection("forecasts");


        FindIterable<Document> documents = collection.find();

        for (Document document: documents) {
            System.out.println("Here is everything in MongoDB " +  document.toJson());
        }
    }

    private void insertForecastToMongoDB(Forecast forecast) {
        MongoDatabase database = mongoDBConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection("forecasts");

        var newDoc = new Document();
        newDoc.append("_id", forecast.getId().toString())
                .append("date", forecast.getDate())
                .append("hour", forecast.getHour())
                .append("temperature", forecast.getTemperature());

        collection.insertOne(newDoc);
        System.out.println("Forecast inserted into MongoDB");
    }


    private void deleteForecastInMongoDB(Scanner scan) {
        MongoDatabase database = mongoDBConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection("forecasts");

        System.out.println("Type in ID to delete");
        UUID deleteID = UUID.fromString(scan.next());
        Bson filter = Filters.eq("_id", deleteID.toString());

        collection.deleteOne(filter);
        System.out.println("Deleted from mongoDB");

    }


}
