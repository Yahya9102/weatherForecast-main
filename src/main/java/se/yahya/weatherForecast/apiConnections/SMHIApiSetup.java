package se.yahya.weatherForecast.apiConnections;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.SMHI.models.SMHIParameter;
import se.yahya.weatherForecast.SMHI.models.SMHITimeSeriesData;
import se.yahya.weatherForecast.SMHI.models.SMHIProps;

import se.yahya.weatherForecast.models.Forecast;
import se.yahya.weatherForecast.services.ForecastService;

import java.io.IOException;
import java.net.URL;
import java.util.*;


@Service
public class SMHIApiSetup {

   // @Autowired
    //GettingAverageFromAPI gettingAverageFromAPI;

    @Autowired
    ForecastService forecastService;

    private String url_Link = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0215/lat/59.3099/data.json";


    public void gettingSMHIData() throws IOException {
        var url = new URL(url_Link);
        var objectMapper = new ObjectMapper();
          SMHIProps smhiProps = objectMapper.readValue(url, SMHIProps.class);
          ArrayList<SMHITimeSeriesData> timeSeriesList = smhiProps.getTimeSeries();

/*
        System.out.println("Ange en tid:");
        Scanner scanner = new Scanner(System.in);
        int response = scanner.nextInt();



 */


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

                        if ("t".equals(paramName) || "pcat".equals(paramName)) {
                            for (Float paramValue : values) {



                                    if ("t".equals(paramName)) {

                                        System.out.println("\n***********************\n");
                                        System.out.println("SMHI Temperatur: " + paramValue);

                                    } else if ("pcat".equals(paramName)) {
                                        if (paramValue == 0.0) {
                                            System.out.println("Tid: " + validTime);
                                            System.out.println("Det kommer inte regna: " + paramValue);

                                        } else if (paramValue == 3.0) {

                                            System.out.println("Tid: " + validTime);
                                            System.out.println("Det kommer regna: " + paramValue);

                                        } else if (paramValue == 1) {

                                            System.out.println("Tid: " + validTime);
                                            System.out.println("Det kommer snöa: " + paramValue);

                                        }
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





