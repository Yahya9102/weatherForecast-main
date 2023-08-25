package se.yahya.weatherForecast.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.SMHIConnection.Parameter;
import se.yahya.weatherForecast.SMHIConnection.SMHIProps;
import se.yahya.weatherForecast.SMHIConnection.TimeSeriesData;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.List;

@Service
public class SMHIApiSetup {

    private String url_Link = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0215/lat/59.3099/data.json";


    public void gettingSMHIData() throws IOException {
        var url = new URL(url_Link);
        var objectmapper = new ObjectMapper();
        SMHIProps smhiProps = objectmapper.readValue(url, SMHIProps.class);

        List<TimeSeriesData> timeSeriesList = smhiProps.getTimeSeries();
        LocalTime currentHour = LocalTime.now();


        for (TimeSeriesData timeSeries : timeSeriesList) {
            String validTime = timeSeries.getValidTime();
            List<Parameter> parameters = timeSeries.getParameters();

            for (Parameter param : parameters) {
                String temp = "t";
                if (param.getName().equals(temp))
                {
                    String paramName = param.getName();
                    List<Float> values = param.getValues();
                    String unit = param.getName();
                    System.out.println("Tid: " + validTime);
                    System.out.println("Parameter: " + paramName);
                    System.out.println("Värden: " + values);
                    System.out.println("Enhet: " + unit);
                }
            }
        }

    }


}
