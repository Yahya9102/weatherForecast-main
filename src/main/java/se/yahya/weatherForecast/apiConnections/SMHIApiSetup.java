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
import se.yahya.weatherForecast.services.ForecastService;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class SMHIApiSetup {


    @Autowired
    ForecastRepository forecastRepository;

    @Autowired
    ForecastService forecastService;

    private String url_Link = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0215/lat/59.3099/data.json";


    public void gettingSMHIData() throws IOException {


        var url = new URL(url_Link);
        var objectMapper = new ObjectMapper();
          SMHIProps smhiProps = objectMapper.readValue(url, SMHIProps.class);
          ArrayList<SMHITimeSeriesData> timeSeriesList = smhiProps.getTimeSeries();



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




                                System.out.println("******************************");
                                System.out.println("Tid: " + hour);
                                System.out.println("Temperatur: " + paramValue);
                                System.out.println(("Datum: " +validLocalDate));

                                forecastFromSmhi.setId(UUID.randomUUID());
                                forecastFromSmhi.setRainOrSnow(rainOrSnow);
                                forecastFromSmhi.setPredictionTemperature(paramValue);
                                forecastFromSmhi.setPredictionDate(validLocalDate);
                                forecastFromSmhi.setPredictionHour(hour);
                                forecastFromSmhi.setDataSource(DataSource.Smhi);

                                forecastRepository.save(forecastFromSmhi);

/*
                            }  if ("pcat".equals(paramName)) {
                                if (paramValue == 0.0) {
                                    System.out.println("Det kommer inte regna: " + paramValue);
                                } else if (paramValue == 3.0) {
                                    rainOrSnow = true;
                                    System.out.println("Det kommer regna: " + paramValue);
                                } else if (paramValue == 1) {
                                    rainOrSnow = true;
                                    System.out.println("Det kommer snöa: " + paramValue);

                                }


 */


                            }
                           //
                            //





                        }


                    }



                }

                /*

                    for (SMHIParameter param : timeSeries.getParameters()) {
                        String paramName = param.getName();
                        List<Float>  values = param.getValues();
                        boolean rainOrSnow = false;

                        if ("t".equals(paramName) || "pcat".equals(paramName)) {
                            for (Float paramValue : values) {

                                    if ("t".equals(paramName)) {


                                    } else if ("pcat".equals(paramName)) {
                                        if (paramValue == 0.0) {
                                            rainOrSnow = true;

                                        } else if (paramValue == 3.0) {
                                            rainOrSnow = true;

                                        } else if (paramValue == 1) {
                                            rainOrSnow = true;
                                       }

                                    }
                                    if (paramValue.intValue() > 0) {

                                        /**SE TILL ATT DATUM GÅR MED IN I DATABASEN
                                         * GÖR SAMMA SAK SOM DENNA FÖR VISUAL
                                         * */

/*

                                        forecastFromSmhi.setId(UUID.randomUUID());
                                        forecastFromSmhi.setRainOrSnow(rainOrSnow);
                                        forecastFromSmhi.setPredictionHour(currentHour);
                                        forecastFromSmhi.setPredictionTemperature(paramValue);
                                        forecastFromSmhi.setDataSource(DataSource.Smhi);
                                        forecastFromSmhi.setPredictionDate(validLocalDate);
                                        forecastRepository.save(forecastFromSmhi);


                                    }
                            }

                        }
          }
                    */
            }
        }


    }
}




