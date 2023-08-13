package ru.karelin.SensorMeasurementApiApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
public class SensorDTO {

    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    @NotEmpty(message = "Name can't be empty")
    private String name;
}
