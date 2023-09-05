package se.yahya.weatherForecast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.yahya.weatherForecast.apiConnections.SMHIApiSetup;
import se.yahya.weatherForecast.apiConnections.VisualCrossingApiSetup;

import se.yahya.weatherForecast.models.DataSource;
import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.repositories.ForecastRepository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ForecastFunctions {

    @Autowired
    ForecastService forecastService;

    @Autowired
    ForecastRepository forecastRepository;


    @Autowired
    VisualCrossingApiSetup visualCrossingApiSetup;
    @Autowired
    SMHIApiSetup smhiApiSetup;


    public void menu() throws IOException, ParseException {


        var scan = new Scanner(System.in);

        while (true) {
            showHeaderMenu();

            System.out.println("Pick an option");
            int choices = scan.nextInt();

            if (choices == 1) {
                System.out.println("1");

                allPredictions();
            } else if (choices == 2) {
                System.out.println("2");

                createNewPrediction(scan);
            } else if (choices == 3) {
                 deletePrediction(scan);
            } else if (choices == 4) {
                 updatePredictions(scan);


            } else if (choices == 5) {
                callingAllApi();
            }else if(choices == 6){
                forecastRepository.deleteAll();
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
        System.out.println("6. Delete all in Database");
        System.out.println("9. Exit");
    }


    private void allPredictions() {
        String hour;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        System.out.println("Here is a list of all the predictions");
        for (var prediction : forecastService.getForecasts()) {
            if (prediction.getPredictionHour() < 12) {
                hour = " AM";
            } else {
                hour = " PM";
            }

            System.out.println("***************************************");
            System.out.println("ID: "
                    + prediction.getId() +
                    "\nDate: " + prediction.getPredictionDate() +
                    "\n " + prediction.getPredictionHour() + hour +
                    "\n temp: " + prediction.getPredictionTemperature() + " C" +
                    "\nRain or snow: " + prediction.isRainOrSnow() +
                    "\n Source: " + prediction.getDataSource());
        }
    }



    private void createNewPrediction(Scanner scan) throws IOException, ParseException {

        System.out.println("Create prediction");
        System.out.println("Ange datum (i formatet YYYYMMDD):");

        String tempDay = scan.next();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate day = LocalDate.parse(tempDay,dateFormat);


        System.out.println("Hour");
        int hour = scan.nextInt();
        System.out.println("Temp");
        float temp = scan.nextFloat();

        Boolean rainOrSnow = false;
        System.out.println("Rain or snow?");
        if (scan.next().equals("rain") || scan.next().equals("snow")){
            rainOrSnow = true;
        }


        var forecast = new Forecast();
        forecast.setId(UUID.randomUUID());
        forecast.setCreated(day);
        forecast.setRainOrSnow(rainOrSnow);
        forecast.setPredictionHour(hour);
        forecast.setPredictionTemperature(temp);
        forecast.setDataSource(DataSource.Console);
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

            existingForecast.setPredictionTemperature(newTemperature);
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



