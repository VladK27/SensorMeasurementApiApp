package ru.karelin.SensorMeasurementApiApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.karelin.SensorMeasurementApiApp.dto.SensorDTO;
import ru.karelin.SensorMeasurementApiApp.mappers.ModelDtoMapper;
import ru.karelin.SensorMeasurementApiApp.models.Sensor;
import ru.karelin.SensorMeasurementApiApp.services.SensorService;
import ru.karelin.SensorMeasurementApiApp.util.SensorErrorResponse;
import ru.karelin.SensorMeasurementApiApp.util.SensorNotCreatedException;
import ru.karelin.SensorMeasurementApiApp.validators.SensorValidator;

import java.time.LocalDateTime;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Qualifier("SensorMapper")
    private final ModelDtoMapper<SensorDTO, Sensor> mapper;

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator, ModelDtoMapper<SensorDTO, Sensor> mapper) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
        this.mapper = mapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> performRegistration(@RequestBody @Valid SensorDTO sensorDTO,
                                                          BindingResult bindingResult)
    {
        sensorValidator.validate(mapper.convertToModel(sensorDTO), bindingResult);
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

        sensorService.save(mapper.convertToModel(sensorDTO));
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
}
