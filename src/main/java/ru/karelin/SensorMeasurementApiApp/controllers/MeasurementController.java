package ru.karelin.SensorMeasurementApiApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.karelin.SensorMeasurementApiApp.dto.MeasurementDTO;
import ru.karelin.SensorMeasurementApiApp.mappers.ModelDtoMapper;
import ru.karelin.SensorMeasurementApiApp.models.Measurement;
import ru.karelin.SensorMeasurementApiApp.services.MeasurementService;
import ru.karelin.SensorMeasurementApiApp.util.MeasurementErrorResponse;
import ru.karelin.SensorMeasurementApiApp.util.MeasurementNotCreatedException;
import ru.karelin.SensorMeasurementApiApp.validators.MeasurementValidator;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */
@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;

    @Qualifier("MeasurementMapper")
    private final ModelDtoMapper<MeasurementDTO, Measurement> mapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeasurementValidator measurementValidator, ModelDtoMapper<MeasurementDTO, Measurement> mapper) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> saveMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                      BindingResult bindingResult)
    {
        Measurement measurement = mapper.convertToModel(measurementDTO);
        measurementValidator.validate(measurement, bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            for(FieldError error : bindingResult.getFieldErrors()){
                errorMsg.append(error.getField())
                        .append(':')
                        .append(error.getDefaultMessage())
                        .append(';');
            }

            throw new MeasurementNotCreatedException(errorMsg.toString());
        }

        measurementService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("")
    public List<MeasurementDTO> returnAllMeasurements(){
        return measurementService.findAll()
                .stream().map(this.mapper::convertToDto)
                .toList();
    }

    @GetMapping("/rainy")
    public Long getCountOfRainy(){
        return measurementService.getCountOfRainy(true);
    }

    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e){
        var response = new MeasurementErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
