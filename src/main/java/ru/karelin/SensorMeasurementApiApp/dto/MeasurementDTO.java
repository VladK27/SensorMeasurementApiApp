package ru.karelin.SensorMeasurementApiApp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.karelin.SensorMeasurementApiApp.models.Sensor;

import java.time.LocalDateTime;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */

@Setter
@Getter
@NoArgsConstructor
public class MeasurementDTO {

    @NotEmpty(message = "Value can't be empty")
    @Min(value = -100, message = "Value can't be less then -100")
    @Max(value = 100,  message = "Value can't be more then 100")
    private Double value;

    @NotEmpty(message = "Raining can't be empty")
    private Boolean raining;

    private SensorDTO sensor;
}
