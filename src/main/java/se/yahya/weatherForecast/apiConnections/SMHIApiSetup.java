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
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;


@Service
public class SMHIApiSetup {


    @Autowired
    ForecastRepository forecastRepository;

    @Autowired
    ForecastService forecastService;

    private String url_Link = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0215/lat/59.3099/data.json";


    public void gettingSMHIData() throws IOException {
        var forecastFromSmhi = new Forecast();

        var url = new URL(url_Link);
        var objectMapper = new ObjectMapper();
          SMHIProps smhiProps = objectMapper.readValue(url, SMHIProps.class);
          ArrayList<SMHITimeSeriesData> timeSeriesList = smhiProps.getTimeSeries();



        Date currenTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currenTime);
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        Date tomorrow = calendar.getTime();


        for (SMHITimeSeriesData timeSeries : timeSeriesList) {
            Date validTime = timeSeries.getValidTime();

            calendar.setTime(validTime);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

            if (validTime.after(currenTime) && validTime.before(tomorrow) &&
                    hour == currentHour ) {

                    for (SMHIParameter param : timeSeries.getParameters()) {
                        String paramName = param.getName();
                        List<Float>  values = param.getValues();
                        boolean rainOrSnow = false;

                        if ("t".equals(paramName) || "pcat".equals(paramName)) {
                            for (Float paramValue : values) {

                                    if ("t".equals(paramName)) {
                                        //System.out.println("\n***********************\n");
                                        //System.out.println("SMHI Temperatur: " + paramValue);

                                    } else if ("pcat".equals(paramName)) {
                                        if (paramValue == 0.0) {
                                            rainOrSnow = true;
                                           // System.out.println("Tid: " + validTime);
                                            //System.out.println("Det kommer inte regna: " + paramValue);

                                        } else if (paramValue == 3.0) {
                                            rainOrSnow = true;
                                           // System.out.println("Tid: " + validTime);
                                            //System.out.println("Det kommer regna: " + paramValue);

                                        } else if (paramValue == 1) {
                                            rainOrSnow = true;
                                           // System.out.println("Tid: " + validTime);
                                           // System.out.println("Det kommer snöa: " + paramValue);


                                       }


                                    }
                                    if (paramValue.intValue() > 0) {
                                        forecastFromSmhi.setId(UUID.randomUUID());
                                        forecastFromSmhi.setRainOrSnow(rainOrSnow);
                                        forecastFromSmhi.setPredictionHour(paramValue.intValue());
                                        forecastFromSmhi.setPredictionTemperature(paramValue);
                                        forecastFromSmhi.setDataSource(DataSource.Smhi);
                                       // forecastFromSmhi.setPredictionDatum(DATUM FIXA);
                                        forecastRepository.save(forecastFromSmhi);
                                    }
                            }

                        }

          }
            }
        }






        //
/*
        SMHIParameter smhiParameter = objectmapper.readValue(url,SMHIParameter.class);


        Document smhiDoc = new Document();

       smhiDoc.append("testing", smhiParameter.getName().toString());
       collection.insertOne(smhiDoc);
        System.out.println("Done");

    }



 */

            //Mata in informationen i mongoDB



    }
}





