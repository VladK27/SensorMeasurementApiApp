package ru.karelin.SensorMeasurementApiApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karelin.SensorMeasurementApiApp.models.Measurement;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */
@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    Long countAllByRaining(Boolean raining);
}
