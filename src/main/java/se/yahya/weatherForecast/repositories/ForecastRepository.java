package se.yahya.weatherForecast.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.yahya.weatherForecast.dto.ForecastAverageDTO;
import se.yahya.weatherForecast.dto.NewForecastDTO;
import se.yahya.weatherForecast.models.DataSource;
import se.yahya.weatherForecast.models.Forecast;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ForecastRepository extends CrudRepository<Forecast, UUID> {
    @Override
    List<Forecast> findAll();


    @Query("SELECT f.predictionDate, f.predictionHour,f.rainOrSnow, ROUND(AVG(f.predictionTemperature),1)" +
            "FROM Forecast f " +
            "GROUP BY f.predictionDate, f.predictionHour " +
            "ORDER BY " +
            "CASE " +
            "  WHEN f.predictionDate = CURRENT_DATE AND f.predictionHour = HOUR(CURRENT_TIME) THEN 0 " +
            "  ELSE 1 " +
            "END, " +
            "f.predictionDate ASC, " +
            "f.predictionHour ASC")
    List<Object> findAverageTempPerHour(LocalDate date);



    @Query("SELECT f.predictionDate AS Date, f.predictionHour AS Hour, f.rainOrSnow AS rainOrSnow, ROUND(AVG(f.predictionTemperature), 1) AS Temp" +
            " FROM Forecast f " +
            " WHERE f.dataSource = :provider " +
            " AND f.predictionDate = :date " +
            " GROUP BY f.predictionDate, f.predictionHour, f.rainOrSnow " +
            " ORDER BY " +
            " CASE " +
            "   WHEN f.predictionDate = CURRENT_DATE AND f.predictionHour = HOUR(CURRENT_TIME) THEN 0 " +
            "   ELSE 1 " +
            " END, " +
            " f.predictionDate ASC, " +
            " f.predictionHour ASC")
    List<Object> findAverageTempPerHourByProvider(@Param("date") LocalDate date, @Param("provider") DataSource provider);


}
