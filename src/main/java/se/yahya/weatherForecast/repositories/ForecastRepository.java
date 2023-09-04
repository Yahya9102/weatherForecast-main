package se.yahya.weatherForecast.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.yahya.weatherForecast.models.Forecast;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ForecastRepository extends CrudRepository<Forecast, UUID> {
    @Override
    List<Forecast> findAll();


  //  List<Forecast> findTempByDay(Instant day);
}
