package se.yahya.weatherForecast.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.yahya.weatherForecast.dto.NewForecastDTO;
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
            "GROUP BY f.predictionDate, f.predictionHour " + //Add f.dataSource here
            "ORDER BY " +
            "CASE " +
            "  WHEN f.predictionDate = CURRENT_DATE AND f.predictionHour = HOUR(CURRENT_TIME) THEN 0 " +
            "  ELSE 1 " +
            "END, " +
            "f.predictionDate ASC, " +
            "f.predictionHour ASC")
    List<Object[]> findAverageTempPerHour(LocalDate date);




}
