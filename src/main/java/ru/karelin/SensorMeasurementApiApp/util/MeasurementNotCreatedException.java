package ru.karelin.SensorMeasurementApiApp.util;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */
public class MeasurementNotCreatedException extends RuntimeException{

    public MeasurementNotCreatedException(String message) {
        super(message);
    }
}
