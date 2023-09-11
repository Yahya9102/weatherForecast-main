package se.yahya.weatherForecast.apiConnections;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.SMHI.models.SMHIParameter;
import se.yahya.weatherForecast.SMHI.models.SMHITimeSeriesData;
import se.yahya.weatherForecast.SMHI.models.SMHIProps;

import se.yahya.weatherForecast.models.DataSource;
import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.repositories.ForecastRepository;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Service
public class SMHIApiSetup {


    @Autowired
    ForecastRepository forecastRepository;

    private float longtitude = 59.3099F;
    private float latitude = 18.0215F;


    private String url_Link = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0215/lat/59.3099/data.json";


    public void gettingSMHIData() throws IOException {



        var url = new URL(url_Link);
        var objectMapper = new ObjectMapper();
          SMHIProps smhiProps = objectMapper.readValue(url, SMHIProps.class);
          ArrayList<SMHITimeSeriesData> timeSeriesList = smhiProps.getTimeSeries();


          LocalDate createdDate = LocalDate.now();
        Date currentTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.HOUR_OF_DAY, 25);
        Date tomorrow = calendar.getTime();


        for (SMHITimeSeriesData timeSeries : timeSeriesList) {
            Date validTime = timeSeries.getValidTime();
            calendar.setTime(validTime);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);




            LocalDate validLocalDate = validTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (validTime.after(currentTime) && validTime.before(tomorrow) &&
                    hour == currentHour ) {

                for (SMHIParameter param : timeSeries.getParameters()) {



                            String paramName = param.getName();
                    var forecastFromSmhi = new Forecast();
                    List<Float>  values = param.getValues();



                    Boolean rainOrSnow = false;
                    if ("t".equals(paramName) || "pcat".equals(paramName)) {
                        for (Float paramValue : values) {
                            if ("pcat".equals(paramName))
                            {
                                if (paramValue == 3.0 && paramValue == 1 ){
                                    rainOrSnow = true;
                                }
                            }

                            if ("t".equals(paramName)) {


                                /*
                                System.out.println("******************************");
                                System.out.println("Tid: " + hour);
                                System.out.println("Temperatur: " + paramValue);
                                System.out.println(("Datum: " +validLocalDate));
                                 */

                                forecastFromSmhi.setId(UUID.randomUUID());
                                forecastFromSmhi.setRainOrSnow(rainOrSnow);
                                forecastFromSmhi.setPredictionTemperature(paramValue);
                                forecastFromSmhi.setPredictionDate(validLocalDate);
                                forecastFromSmhi.setPredictionHour(hour);
                                forecastFromSmhi.setCreated(createdDate);
                                forecastFromSmhi.setLatitude(latitude);
                                forecastFromSmhi.setLongitude(longtitude);

                                forecastFromSmhi.setDataSource(DataSource.Smhi);
                                forecastRepository.save(forecastFromSmhi);

                            }
                        }
                    }
                }
            }
        }
    }
}




