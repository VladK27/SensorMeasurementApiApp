package ru.karelin.SensorMeasurementApiApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.karelin.SensorMeasurementApiApp.dto.SensorDTO;
import ru.karelin.SensorMeasurementApiApp.models.Sensor;
import ru.karelin.SensorMeasurementApiApp.services.SensorService;
import ru.karelin.SensorMeasurementApiApp.util.SensorErrorResponse;
import ru.karelin.SensorMeasurementApiApp.util.SensorNotCreatedException;
import ru.karelin.SensorMeasurementApiApp.util.SensorValidator;

import java.time.LocalDateTime;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final ModelMapper modelMapper;
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(ModelMapper modelMapper, SensorService sensorService, SensorValidator sensorValidator) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> performRegistration(@RequestBody @Valid SensorDTO sensorDTO,
                                                          BindingResult bindingResult)
    {
        sensorValidator.validate(convertToSensor(sensorDTO), bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            for(FieldError error : bindingResult.getFieldErrors()){
                errorMsg.append(error.getField())
                        .append(':')
                        .append(error.getDefaultMessage())
                        .append(';');
            }

            throw new SensorNotCreatedException(errorMsg.toString());
        }

        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e){
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
