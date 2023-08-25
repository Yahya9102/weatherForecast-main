package se.yahya.weatherForecast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import se.yahya.weatherForecast.api.SMHIApiSetup;


@SpringBootApplication
public class WeatherForecastApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WeatherForecastApplication.class, args);
	}

	@Autowired
	SMHIApiSetup smhiApiSetup;
/*
	@Autowired
	ForecastFunctions forecastFunctions;
 */
	@Override
	public void run(String... args) throws Exception {
		smhiApiSetup.gettingSMHIData();
/*
		String url = "https://jsonplaceholder.typicode.com/posts/1";
		var objectmapper = new ObjectMapper();

		//GÖR OM DIN KOD TILL DENNA
		APIProperties apiProps = objectmapper.readValue(new URL(url),APIProperties.class);

		var forecast = new Forecast();
		forecast.setId(UUID.randomUUID());
		forecast.setDate(2020202);
		forecast.setHour(12);
		forecast.setTemperature(12);


		String json = objectmapper.writeValueAsString(forecast);
		System.out.println(json);

		var forecast2 = objectmapper.readValue(json,Forecast.class);
		System.out.println(forecast2);

 */


// Do something with the parsed object



//forecastFunctions.menu();
	}
}






