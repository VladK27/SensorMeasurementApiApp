package ru.karelin.SensorMeasurementApiApp.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.karelin.SensorMeasurementApiApp.dto.MeasurementDTO;
import ru.karelin.SensorMeasurementApiApp.models.Measurement;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */
@Component("MeasurementMapper")
public class MeasurementMapper implements ModelDtoMapper<MeasurementDTO, Measurement> {
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public Measurement convertToModel(MeasurementDTO dto) {
        return modelMapper.map(dto, Measurement.class);
    }

    @Override
    public MeasurementDTO convertToDto(Measurement model) {
        return modelMapper.map(model, MeasurementDTO.class);
    }
}
