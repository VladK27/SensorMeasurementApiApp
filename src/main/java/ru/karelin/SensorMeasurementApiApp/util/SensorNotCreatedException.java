package ru.karelin.SensorMeasurementApiApp.util;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */
public class SensorNotCreatedException extends RuntimeException{
    public SensorNotCreatedException(String message){
        super(message);
    }
}
