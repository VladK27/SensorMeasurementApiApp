package ru.karelin.SensorMeasurementApiApp.mappers;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */

public interface ModelDtoMapper <D, M> {
    M convertToModel(D dto);
    D convertToDto(M model);
}