package se.yahya.weatherForecast.apiConnections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yahya.weatherForecast.models.Forecast;

import java.util.Date;

@Service
public class GettingAverageFromAPI {

    Date SMHIDate;
    float SMHItemp;
    int SMHIhour;

    Date VisualDate;
    float VisualTemp;
    int VisualHour;



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





    public float gettingAverage(){

        float averageTemp = ((getSMHItemp() + getVisualTemp())/2);

        System.out.println("The average of both is: " + averageTemp);
        return averageTemp;

    }




    }








