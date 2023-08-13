package ru.karelin.SensorMeasurementApiApp.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karelin.SensorMeasurementApiApp.models.Sensor;

import java.util.Optional;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(String name);
}