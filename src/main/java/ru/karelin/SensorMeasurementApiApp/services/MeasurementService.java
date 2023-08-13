package ru.karelin.SensorMeasurementApiApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karelin.SensorMeasurementApiApp.models.Measurement;
import ru.karelin.SensorMeasurementApiApp.repositories.MeasurementRepository;
import ru.karelin.SensorMeasurementApiApp.repositories.SensorRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */
@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Measurement> findAll(){
        return measurementRepository.findAll();
    }

    @Transactional
    public Measurement save(Measurement measurement){
        measurement.setMeasuredAt(LocalDateTime.now());
        measurement.setSensor(sensorRepository.findByName(measurement.getSensor().getName()).get());

        return measurementRepository.save(measurement);
    }

    public Long getCountOfRainy(Boolean isRainy){
        return measurementRepository.countAllByRaining(isRainy);
    }
}
