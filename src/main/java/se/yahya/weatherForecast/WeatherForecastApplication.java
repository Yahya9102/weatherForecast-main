package se.yahya.weatherForecast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import se.yahya.weatherForecast.apiConnections.SMHIApiSetup;
import se.yahya.weatherForecast.apiConnections.VisualCrossingApiSetup;
import se.yahya.weatherForecast.repositories.ForecastRepository;
import se.yahya.weatherForecast.services.ForecastFunctions;


@SpringBootApplication
public class WeatherForecastApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WeatherForecastApplication.class, args);
	}


	@Autowired
	ForecastFunctions forecastFunctions;

	@Override
	public void run(String... args) throws Exception {




	forecastFunctions.menu();
	}
}






