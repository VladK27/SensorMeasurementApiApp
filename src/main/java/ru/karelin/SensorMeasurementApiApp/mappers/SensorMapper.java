package ru.karelin.SensorMeasurementApiApp.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.karelin.SensorMeasurementApiApp.dto.SensorDTO;
import ru.karelin.SensorMeasurementApiApp.models.Sensor;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */
@Component("SensorMapper")
public class SensorMapper implements ModelDtoMapper<SensorDTO, Sensor> {

    private final ModelMapper modelMapper;

    @Autowired
    public SensorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Sensor convertToModel(SensorDTO dto) {
        return modelMapper.map(dto, Sensor.class);
    }

    @Override
    public SensorDTO convertToDto(Sensor model) {
        return modelMapper.map(model, SensorDTO.class);
    }
}
