package se.yahya.weatherForecast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.services.ForecastFunctions;
import se.yahya.weatherForecast.services.ForecastService;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;


@SpringBootApplication
public class WeatherForecastApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WeatherForecastApplication.class, args);
	}


	@Autowired
	ForecastFunctions forecastFunctions;


	@Override
	public void run(String... args) throws Exception {
		//Kommenterar ut denna
	forecastFunctions.menu();
	}
	}






