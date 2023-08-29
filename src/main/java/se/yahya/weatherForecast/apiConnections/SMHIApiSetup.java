package se.yahya.weatherForecast.apiConnections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.SMHI.models.SMHIParameter;
import se.yahya.weatherForecast.SMHI.models.SMHITimeSeriesData;
import se.yahya.weatherForecast.SMHI.models.SMHIProps;
import se.yahya.weatherForecast.dbConnection.MongoDBConnection;
import se.yahya.weatherForecast.models.Forecast;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class SMHIApiSetup {

    @Autowired
    Forecast forecast;
    @Autowired
    MongoDBConnection mongoDBConnection;

    private String url_Link = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0215/lat/59.3099/data.json";


    public void gettingSMHIData() throws IOException {
        var url = new URL(url_Link);
        var objectMapper = new ObjectMapper();
          SMHIProps smhiProps = objectMapper.readValue(url, SMHIProps.class);
          ArrayList<SMHITimeSeriesData> timeSeriesList = smhiProps.getTimeSeries();


        for (SMHITimeSeriesData timeSeries : timeSeriesList) {
            Date validTime = timeSeries.getValidTime();
            List<Double> values;

            Date currenTime = new Date();
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(currenTime);

            calendar.add(Calendar.HOUR_OF_DAY, 24);
            Date tomorrow = calendar.getTime();

            if (validTime.after(currenTime) && validTime.before(tomorrow)) {

                for (SMHIParameter param : timeSeries.getParameters()) {
                    String paramName = param.getName();
                    values = param.getValues();

                    if ("t".equals(paramName) || "pcat".equals(paramName)) {
                        for (Double paramValue : values) {
                            if ("t".equals(paramName)) {
                                System.out.println("**************************************");
                                System.out.println("\n");
                                System.out.println("Temperatur: " + paramValue);

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





