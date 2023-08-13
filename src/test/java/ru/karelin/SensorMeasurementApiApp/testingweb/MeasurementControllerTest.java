package ru.karelin.SensorMeasurementApiApp.testingweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.karelin.SensorMeasurementApiApp.dto.MeasurementDTO;
import ru.karelin.SensorMeasurementApiApp.mappers.ModelDtoMapper;
import ru.karelin.SensorMeasurementApiApp.models.Measurement;
import ru.karelin.SensorMeasurementApiApp.models.Sensor;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Vlad on 13.08.2023.
 *
 * @author Vlad
 */
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Transactional
public class MeasurementControllerTest {
    private final MockMvc mockMvc;
    private final Measurement testMeasurement;
    @Qualifier("MeasurementMapper")
    private final ModelDtoMapper<MeasurementDTO, Measurement> mapper;

    @Autowired
    public MeasurementControllerTest(MockMvc mockMvc, ModelDtoMapper<MeasurementDTO, Measurement> mapper) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;

        this.testMeasurement = new Measurement();
        this.testMeasurement.setRaining(true);
        this.testMeasurement.setValue(20.0);
        this.testMeasurement.setSensor(new Sensor("test_sensor"));
    }

    @Test
    public void shouldSaveMeasurement() throws Exception {
        Measurement measurement = this.testMeasurement;

        //register new sensor
        registerSensor(measurement.getSensor());

        //add measurement
        mockMvc.perform(post("/measurements/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mapper.convertToDto(measurement))))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));
    }

    @Test
    public void shouldNotSaveMeasurementWithNotRegisteredSensor() throws Exception {
        mockMvc.perform(post("/measurements/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mapper.convertToDto(this.testMeasurement))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotSaveNotValidMeasurement() throws Exception {
        Measurement measurement = new Measurement();
        measurement.setValue(-120D);
        measurement.setSensor(new Sensor("test_sensor"));

        registerSensor(measurement.getSensor());

        //add measurement
        mockMvc.perform(post("/measurements/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mapper.convertToDto(measurement))))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void shouldReturnAllMeasurements() throws Exception {
        registerSensor(this.testMeasurement.getSensor());

        //add 5 new measurements
        for (int i = 0; i < 5; i++) {
            addMeasurement(this.testMeasurement);
        }
        String measurementsJSON = new ObjectMapper()
                .writeValueAsString(mapper.convertToDto(this.testMeasurement));

        mockMvc.perform(get("/measurements"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(measurementsJSON)));

    }

    @Test
    public void shouldReturnCountOfRainingDays() throws Exception {
        registerSensor(this.testMeasurement.getSensor());

        //add 5 new measurements
        for (int i = 0; i < 5; i++) {
            addMeasurement(this.testMeasurement);
        }

        mockMvc.perform(get("/measurements/rainy"))
                .andExpect(content().string(containsString("5")));
    }

    private void registerSensor(Sensor sensor) throws Exception {
        mockMvc.perform(post("/sensors/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(sensor)));
    }

    private void addMeasurement(Measurement measurement) throws Exception{
        mockMvc.perform(post("/measurements/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mapper.convertToDto(measurement))));
    }
}
