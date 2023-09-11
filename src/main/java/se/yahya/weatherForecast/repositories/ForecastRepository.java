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
import java.util.*;

@Repository
public interface ForecastRepository extends CrudRepository<Forecast, UUID> {
    @Override
    List<Forecast> findAll();

    @Query("SELECT f.predictionDate, f.predictionHour,f.rainOrSnow, ROUND(AVG(f.predictionTemperature),1)" +
            "FROM Forecast f " +
            "GROUP BY f.predictionDate, f.predictionHour " +
            "HAVING f.predictionDate = ?1 " +
            "ORDER BY " +
            "f.predictionDate ASC, " +
            "f.predictionHour ASC")
    List<Object> findAverageTempPerHour(LocalDate date);


    @Query("SELECT\n" +
            "    f.predictionDate AS Date, f.predictionHour AS Hour, f.rainOrSnow AS rainOrSnow,\n" +
            "    ROUND(AVG(IFNULL(f.predictionTemperature, 0.0)), 1) AS Temp\n" +
            "FROM Forecast f\n" +
            "WHERE\n" +
            "    f.dataSource = :provider\n" +
            "    AND f.predictionDate = :date\n" +
            "GROUP BY\n" +
            "    f.predictionDate, f.predictionHour, f.rainOrSnow\n" +
            "HAVING f.predictionDate = :date AND f.dataSource = :provider\n" +
            "ORDER BY\n" +
            "    f.predictionDate ASC,\n" +
            "    f.predictionHour ASC")

    List<Map> findAverageTempPerHourByProvider(@Param("date") LocalDate date, @Param("provider") DataSource provider);


}
