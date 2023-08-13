package ru.karelin.SensorMeasurementApiApp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */

@Setter
@Getter
@NoArgsConstructor
public class MeasurementDTO {

    @Min(value = -100, message = "Value can't be less then -100")
    @Max(value = 100,  message = "Value can't be more then 100")
    @NotNull(message = "Value can't be empty")
    private Double value;

    @NotNull(message = "Raining can't be empty")
    private Boolean raining;

    private SensorDTO sensor;
}
