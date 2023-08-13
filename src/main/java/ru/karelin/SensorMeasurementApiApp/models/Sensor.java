package ru.karelin.SensorMeasurementApiApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */

@Entity
@Table(name = "sensor")
@Setter
@Getter
@NoArgsConstructor
public class Sensor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    @NotEmpty(message = "Name can't be empty")
    private String name;

    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurements;

    public Sensor(String name) {
        this.name = name;
    }
}
