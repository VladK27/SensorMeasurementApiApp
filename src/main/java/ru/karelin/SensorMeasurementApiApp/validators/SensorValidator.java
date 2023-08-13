package ru.karelin.SensorMeasurementApiApp.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.karelin.SensorMeasurementApiApp.models.Sensor;
import ru.karelin.SensorMeasurementApiApp.services.SensorService;

import java.util.Optional;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */
@Component
public class SensorValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        Optional<Sensor> sensorOptional = sensorService.findByName(sensor.getName());
        if(sensorOptional.isPresent()){
            errors.rejectValue("name", "", "Sensor with this name already registered");
        }
    }
}
