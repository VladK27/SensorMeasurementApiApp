package ru.karelin.SensorMeasurementApiApp.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */
@Setter
@Getter
@AllArgsConstructor
public class MeasurementErrorResponse {
    private String message;
    private LocalDateTime time;
}
