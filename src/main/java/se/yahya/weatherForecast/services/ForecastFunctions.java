package se.yahya.weatherForecast.services;

/*
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import se.yahya.weatherForecast.dbConnection.MongoDBConnection;

import org.bson.Document;
import org.bson.conversions.Bson;



 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.yahya.weatherForecast.apiConnections.VisualCrossingApiSetup;
import se.yahya.weatherForecast.models.Forecast;

import java.io.IOException;
import java.util.*;

@Service
public class ForecastFunctions {

    @Autowired
    ForecastService forecastService;

    /*
    @Autowired
    MongoDBConnection mongoDBConnection;
     */

@Autowired
VisualCrossingApiSetup forecastAPI;


    public void menu () throws IOException {



    var scan = new Scanner(System.in);

        while (true) {
            showHeaderMenu();

            System.out.println("Pick an option");
            int choices = scan.nextInt();

            if (choices == 1) {
                System.out.println("1");
                allPredictions(scan);
            } else if (choices == 2) {
                System.out.println("2");

                createNewPrediction(scan);
            }else if (choices == 3) {
                deletePrediction(scan);
            }
            else if (choices == 4) {
               // updatePrediction(scan);
                updatePrediction(scan);

            }
            else if (choices == 5) {
                forecastAPI.gettingAPI();
            }
            else if (choices == 9) {
                System.out.println("4");
                scan.close();
                return;
            } else {
                System.out.println("Invalid choice");
            }
        }


    }

    private void updatePrediction(Scanner scan) {
        allPredictions(scan);
        System.out.println("Ange id");
        UUID Uid = UUID.fromString(scan.next());
        int id = Integer.parseInt(Uid.toString());

        var forecast = forecastService.getByIndex(id);
        System.out.println("Date" + forecast.getDate() + "\n hour"  + forecast.getHour() + "\ntemp" + forecast.getTemperature());
        System.out.println("ange ny temp");
        float newTemp = scan.nextFloat();
        forecast.setTemperature(newTemp);
        forecastService.update(Uid, forecast);


    }


    private void showHeaderMenu() {
        System.out.println("1. List all");
        System.out.println("2. Create");
        System.out.println("3. Delete");
        System.out.println("4. Update");
        System.out.println("5. API Calls");
        System.out.println("9. Exit");
    }

    private void allPredictions(Scanner scan) {
        String hour;
        System.out.println("Here is a list of all the predictions");
        for ( var prediction: forecastService.getForecasts()
        ) {
            if (prediction.getHour() < 12 ){
                hour = " AM";
            }
            else {
                hour = " PM";
            }
            System.out.println("ID "+ prediction.getId() + "\nDate " + prediction.getDate() + "\n " +  prediction.getHour() + hour +  "\n temp " +prediction.getTemperature() + " C");
        }


    }

    private void createNewPrediction(Scanner scan) throws IOException {
        System.out.println("Create prediction");
        System.out.println("Ange datum");
        int dag = scan.nextInt();
        System.out.println("Hour");
        int hour = scan.nextInt();
        System.out.println("Temp");
        float temp = scan.nextFloat();


        // Skapa en instans som man kan mata in
        var forecast = new Forecast();
        forecast.setId(UUID.randomUUID());
        forecast.setDate(dag);
        forecast.setHour(hour);
        forecast.setTemperature(temp);
        forecastService.add(forecast);

/*

        //DETTA ÄR FÖR JSON

        var objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(forecast);
        var file = new File("Forecast.json");
        objectMapper.writeValue(file,json);
        System.out.println("Saved forecast as JSON" + json);


 */

    }





    /*
    private void updatePrediction(Scanner scan) {
        System.out.println("Update prediction");
        System.out.println("Enter the ID of the prediction you want to update:");
        UUID idToUpdate = UUID.fromString(scan.next());

        //Fråga läraren om denna
        Forecast existingForecast = forecastService.getForecasts().stream()
                .filter(forecast -> forecast.getId().equals(idToUpdate))
                .findFirst()
                .orElse(null);


        if (existingForecast != null) {
            System.out.println("Enter new temperature:");
            float newTemperature = scan.nextFloat();

            existingForecast.setTemperature(newTemperature);
            forecastService.update(idToUpdate, existingForecast);

            System.out.println("Prediction with ID " + idToUpdate + " has been updated.");
        } else {
            System.out.println("No prediction found with the given ID.");
        }
    }

   */
    private void deletePrediction(Scanner scan) {
        System.out.println("Delete prediction");
        System.out.println("Enter the ID of the prediction you want to delete:");
        UUID idToDelete = UUID.fromString(scan.next());
        forecastService.delete(idToDelete);
        System.out.println("Prediction with ID " + idToDelete + " has been deleted.");

    }

}


