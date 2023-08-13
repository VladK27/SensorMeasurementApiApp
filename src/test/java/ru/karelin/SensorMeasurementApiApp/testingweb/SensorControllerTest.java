package ru.karelin.SensorMeasurementApiApp.testingweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.karelin.SensorMeasurementApiApp.models.Sensor;

import static org.hamcrest.Matchers.containsString;
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
public class SensorControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    public SensorControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void shouldSaveSensor() throws Exception {
        Sensor sensor = new Sensor("test_sensor");

        mockMvc.perform(post("/sensors/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sensor)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));
    }

    @Test
    public void shouldNotSaveDuplicateSensor() throws Exception{
        Sensor sensor = new Sensor("test_sensor");

        mockMvc.perform(post("/sensors/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(sensor)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));

        mockMvc.perform(post("/sensors/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(sensor)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotSaveNotValidSensor() throws Exception{
        Sensor notValidSensor = new Sensor(""); //sensor with empty name is not valid

        mockMvc.perform(post("/sensors/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(notValidSensor)))
                .andExpect(status().isBadRequest());
    }
}
