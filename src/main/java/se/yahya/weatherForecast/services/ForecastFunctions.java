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

import se.yahya.weatherForecast.apiConnections.GettingAverageFromAPI;
import se.yahya.weatherForecast.apiConnections.SMHIApiSetup;
import se.yahya.weatherForecast.apiConnections.VisualCrossingApiSetup;
import se.yahya.weatherForecast.dbConnection.MongoDBConnection;
import se.yahya.weatherForecast.dbConnection.dbMethods.ForecastDatabaseFunctions;
import se.yahya.weatherForecast.models.Forecast;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ForecastFunctions {

    @Autowired
    ForecastService forecastService;

    @Autowired
    ForecastDatabaseFunctions forecastDatabaseFunctions;


    @Autowired
    VisualCrossingApiSetup visualCrossingApiSetup;
    @Autowired
    SMHIApiSetup smhiApiSetup;
    @Autowired
    Forecast forecast;

    @Autowired
    GettingAverageFromAPI gettingAverageFromAPI;


    public void menu() throws IOException, ParseException {


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
            } else if (choices == 3) {
                 deletePrediction(scan);
            } else if (choices == 4) {
                updatePredictions(scan);


            } else if (choices == 5) {
                callingAllApi();
            } else if (choices == 6) {
                gettingAverageFromAPI.gettingAverage();
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
/*
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


 */

    private void showHeaderMenu() {
        System.out.println("1. List all");
        System.out.println("2. Create");
        System.out.println("3. Delete");
        System.out.println("4. Update");
        System.out.println("5. API Calls");
        System.out.println("6. Calculating Average");
        System.out.println("9. Exit");
    }


    private void allPredictions(Scanner scan) {
        String hour;
        System.out.println("Here is a list of all the predictions");
        for (var prediction : forecastService.getForecasts()
        ) {
            if (prediction.getHour() < 12) {
                hour = " AM";
            } else {
                hour = " PM";
            }
            System.out.println("ID " + prediction.getId() + "\nDate " + prediction.getDate() + "\n " + prediction.getHour() + hour + "\n temp " + prediction.getTemperature() + " C");
        }


    }

    private void createNewPrediction(Scanner scan) throws IOException, ParseException {
        System.out.println("Create prediction");
        System.out.println("Ange datum");
        String tempDay = scan.next();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date dag = dateFormat.parse(tempDay);


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

    }


    public void callingAllApi() throws IOException, ParseException {
          visualCrossingApiSetup.gettingAPI();
        smhiApiSetup.gettingSMHIData();
    }


    private void updatePredictions(Scanner scan) throws IOException {
        System.out.println("Update prediction");
        System.out.println("Enter the ID of the prediction you want to update:");
        UUID idToUpdate = UUID.fromString(scan.next());

        // Find the forecast to update
        Forecast existingForecast = forecastService.getForecasts().stream()
                .filter(forecast -> forecast.getId().equals(idToUpdate))
                .findFirst()
                .orElse(null);

        if (existingForecast != null) {
            System.out.println("Enter new temperature:");
            float newTemperature = scan.nextFloat();

            existingForecast.setTemperature(newTemperature);
            forecastService.update(existingForecast);

            System.out.println("Prediction with ID " + idToUpdate + " has been updated.");
        } else {
            System.out.println("No prediction found with the given ID.");
        }
    }

    private void deletePrediction(Scanner scan) throws IOException {
        System.out.println("Delete prediction");
        System.out.println("Enter the ID of the prediction you want to delete:");
        UUID idToDelete = UUID.fromString(scan.next());
        System.out.println(idToDelete);
        forecastService.delete(idToDelete);
        System.out.println("Prediction with ID " + idToDelete + " has been deleted.");

    }

}


