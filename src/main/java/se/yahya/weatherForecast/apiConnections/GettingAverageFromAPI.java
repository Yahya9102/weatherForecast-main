package se.yahya.weatherForecast.apiConnections;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.dbConnection.MongoDBConnection;
import se.yahya.weatherForecast.dbConnection.dbMethods.ForecastDatabaseFunctions;
import se.yahya.weatherForecast.models.Forecast;

import java.util.Date;

@Service
public class GettingAverageFromAPI {

    @Autowired
    Forecast forecast;

    @Autowired
    MongoDBConnection mongoDBConnection;

    @Autowired
    ForecastDatabaseFunctions forecastDatabaseFunctions;

    Date SMHIDate;
    float SMHItemp;
    int SMHIhour;

    boolean SMHIRainOrSnow;

    Date VisualDate;
    float VisualTemp;
    int VisualHour;

    boolean VisualRainOrSnow;

    public boolean isSMHIRainOrSnow() {
        return SMHIRainOrSnow;
    }

    public void setSMHIRainOrSnow(boolean SMHIRainOrSnow) {
        this.SMHIRainOrSnow = SMHIRainOrSnow;
    }

    public boolean isVisualRainOrSnow() {
        return VisualRainOrSnow;
    }

    public void setVisualRainOrSnow(boolean visualRainOrSnow) {
        VisualRainOrSnow = visualRainOrSnow;
    }

    public Date getSMHIDate() {
        return SMHIDate;
    }

    public void setSMHIDate(Date SMHIDate) {
        this.SMHIDate = SMHIDate;
    }

    public float getSMHItemp() {
        return SMHItemp;
    }

    public void setSMHItemp(float SMHItemp) {
        this.SMHItemp = SMHItemp;
    }

    public int getSMHIhour() {
        return SMHIhour;
    }

    public void setSMHIhour(int SMHIhour) {
        this.SMHIhour = SMHIhour;
    }

    public Date getVisualDate() {
        return VisualDate;
    }

    public void setVisualDate(Date visualDate) {
        VisualDate = visualDate;
    }

    public float getVisualTemp() {
        return VisualTemp;
    }

    public void setVisualTemp(float visualTemp) {
        VisualTemp = visualTemp;
    }

    public int getVisualHour() {
        return VisualHour;
    }

    public void setVisualHour(int visualHour) {
        VisualHour = visualHour;
    }


    public void gettingAverage() {

        float averageTemp = ((getSMHItemp() + getVisualTemp()) / 2);


        if (isSMHIRainOrSnow() == true) {
            System.out.println("Det kommer regna också");
        }
        System.out.println("The average of both is: " + averageTemp);

        forecast.setTemperature(averageTemp);
        forecast.setDate(getSMHIDate());
        forecast.setHour(getSMHIhour());
        forecast.setRainOrSnow(isSMHIRainOrSnow());

        mongoDBConnection.getDatabase();

        Document smhiDoc = new Document();

        smhiDoc.append("Temp", forecast.getTemperature()).append("Tid", forecast.getHour()).append("Datun", forecast.getDate()).append("RainOrSnow",forecast.isRainOrSnow());

        MongoCollection<Document> collection = mongoDBConnection.getDatabase().getCollection("averageTemp");

       collection.insertOne(smhiDoc);


    }
}








