package ru.karelin.SensorMeasurementApiApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karelin.SensorMeasurementApiApp.models.Sensor;
import ru.karelin.SensorMeasurementApiApp.repositories.SensorRepository;

import java.util.Optional;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */
@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public Sensor save(Sensor sensor){
       return sensorRepository.save(sensor);
    }

    public Optional<Sensor> findByName(String name){
        return sensorRepository.findByName(name);
    }
}
