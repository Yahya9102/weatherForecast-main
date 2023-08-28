package se.yahya.weatherForecast.apiConnections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
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
import java.util.ArrayList;
import java.util.List;


@Service
public class SMHIApiSetup {

    @Autowired
    Forecast forecast;

    private String url_Link = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0215/lat/59.3099/data.json";



    public void gettingSMHIData() throws IOException {
        var url = new URL(url_Link);
        var objectmapper = new ObjectMapper();
      //  var database = mongoDBConnection.getDatabase();
        //Document smhiDoc = new Document();
        SMHIProps smhiProps = objectmapper.readValue(url, SMHIProps.class);
      //  List<SMHIProps> timeSeriesList = smhiTimeSeriesData.getTimeSeries();
        ArrayList<SMHITimeSeriesData> timeSeriesList = smhiProps.getTimeSeries();
    //    MongoCollection<Document> collection = database.getCollection("smhi");

        System.out.println(timeSeriesList);
     //   List<Document> dataPoints = new ArrayList<>();


/*
        for (SMHITimeSeriesData timeSeries : timeSeriesList) {
            String validTime = String.valueOf(timeSeries.getValidTime());
            List<Double> values = new ArrayList<>();


            for (SMHIParameter param : timeSeries.getParameters()) {
                String paramName = param.getName();


                if ("t".equals(paramName)) {
                    values = param.getValues();

                    for (int i = 0; i < values.size(); i++) {
                        Double tempValues = values.get(i);
                        values.set(i,tempValues);

                        //TRIM THE DECIMALS DOWN
                    }

                    break;
                }
            }

            Document dataPoint = new Document()
                    .append("tid", validTime)
                    .append("Värden", values);
            dataPoints.add(dataPoint);
        }
        smhiDoc.append("data", dataPoints);
        collection.insertOne(smhiDoc);



 */
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





