package ru.karelin.SensorMeasurementApiApp.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.karelin.SensorMeasurementApiApp.models.Measurement;
import ru.karelin.SensorMeasurementApiApp.services.SensorService;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */
@Component
public class MeasurementValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

         if(sensorService.findByName(measurement.getSensor().getName())
                 .isEmpty())
         {
             errors.rejectValue("sensor", "", "Sensor with this name is not registered");
         }
    }
}
