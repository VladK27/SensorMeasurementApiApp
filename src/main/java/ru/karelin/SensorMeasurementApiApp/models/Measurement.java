package ru.karelin.SensorMeasurementApiApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */
@Entity
@Table(name = "measurement")
@Setter
@Getter
@NoArgsConstructor
public class Measurement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "measurement_value")
    @Min(value = -100, message = "Value can't be less then -100")
    @Max(value = 100,  message = "Value can't be more then 100")
    @NotNull(message = "Value can't be empty")
    private Double value;

    @Column(name = "raining")
    @NotNull(message = "Raining can't be empty")
    private Boolean raining;

    @Column(name = "measured_at")
    private LocalDateTime measuredAt;

    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    @ManyToOne
    private Sensor sensor;
}
