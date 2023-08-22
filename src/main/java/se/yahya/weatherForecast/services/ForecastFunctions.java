package se.yahya.weatherForecast.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.models.Forecast;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

@Service
public class ForecastFunctions {

    @Autowired
    ForecastService forecastService;



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
            // Inside the run method
            else if (choices == 4) {
                updatePrediction(scan);
            }
            else if (choices == 9) {
                System.out.println("4");
                // Exit
                scan.close();  // Close the scanner before exiting
                return;
            } else {
                System.out.println("Invalid choice");
            }
        }


    }







    private void showHeaderMenu() {
        System.out.println("1. List all");
        System.out.println("2. Create");
        System.out.println("3. Delete");
        System.out.println("4. Update");
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


        // Validate temperature input
        float temp = 0.0f;
        temp = scan.nextFloat();



        // Skapa en instans som man kan mata in
        var forecast = new Forecast();
        forecast.setId(UUID.randomUUID());
        forecast.setDate(dag);
        forecast.setHour(hour);
        forecast.setTemperature(temp);
        forecastService.add(forecast);



        var objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(forecast);
        var file = new File("Forecast.json");
        objectMapper.writeValue(file,json);
        System.out.println("Saved forecast as JSON" + json);


    }

    private void updatePrediction(Scanner scan) {
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

            // Update the forecast
            existingForecast.setTemperature(newTemperature);
            forecastService.update(idToUpdate, existingForecast);

            System.out.println("Prediction with ID " + idToUpdate + " has been updated.");
        } else {
            System.out.println("No prediction found with the given ID.");
        }
    }


    private void deletePrediction(Scanner scan) {
        System.out.println("Delete prediction");
        System.out.println("Enter the ID of the prediction you want to delete:");
        UUID idToDelete = UUID.fromString(scan.next());
        forecastService.delete(idToDelete);

        System.out.println("Prediction with ID " + idToDelete + " has been deleted.");
    }

}
