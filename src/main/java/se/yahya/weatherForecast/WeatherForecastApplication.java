package se.yahya.weatherForecast;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import se.yahya.weatherForecast.models.APIProperties;
import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.services.ForecastFunctions;

import java.net.HttpURLConnection;
import java.net.URL;
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

		String url = "https://jsonplaceholder.typicode.com/posts/1";
		var objectmapper = new ObjectMapper();

		APIProperties[] apiProps = objectmapper.readValue(new URL(url),APIProperties[].class);




		var forecast = new Forecast();
		forecast.setId(UUID.randomUUID());
		forecast.setDate(2020202);
		forecast.setHour(12);
		forecast.setTemperature(12);


		String json = objectmapper.writeValueAsString(forecast);
		System.out.println(json);

		var forecast2 = objectmapper.readValue(json,Forecast.class);
		System.out.println(forecast2);











//forecastFunctions.menu();
	}
}






