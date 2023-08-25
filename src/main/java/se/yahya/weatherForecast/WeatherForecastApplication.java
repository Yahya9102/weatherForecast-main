package se.yahya.weatherForecast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import se.yahya.weatherForecast.apiConnections.SMHIApiSetup;
import se.yahya.weatherForecast.apiConnections.VisualCrossingApiSetup;


@SpringBootApplication
public class WeatherForecastApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WeatherForecastApplication.class, args);
	}

	@Autowired
	SMHIApiSetup smhiApiSetup;

	@Autowired
	VisualCrossingApiSetup visualCroppsingApiSetup;

	@Override
	public void run(String... args) throws Exception {
		smhiApiSetup.gettingSMHIData();
		//visualCroppsingApiSetup.gettingAPI();


//forecastFunctions.menu();
	}
}






