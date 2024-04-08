package ua.tqs.homework.Controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Stop;
import ua.tqs.homework.Services.StopService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StopController.class)
class StopControllerMockServiceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StopService stopService;

    Stop stop1, stop2, stop3;

    @BeforeEach
    void setup() {
        stop1 = new Stop();
        stop2 = new Stop();
        stop3 = new Stop();

        stop1.setId(1L);
        stop2.setId(2L);
        stop3.setId(3L);

        stopService.saveStop(stop1);
        stopService.saveStop(stop2);
        stopService.saveStop(stop3);
    }

    @Test
    void testGetAllStops() throws Exception {
        when(stopService.getAllStops()).thenReturn(List.of(stop1, stop2, stop3));

        mockMvc.perform(get("/stops")).andExpectAll(status().isOk(),
                jsonPath("$").isArray(),
                jsonPath("$[0].id").value(stop1.getId()),
                jsonPath("$[1].id").value(stop2.getId()),
                jsonPath("$[2].id").value(stop3.getId()));
    }

    @Test
    void testGetAllStopsEmpty() throws Exception {
        when(stopService.getAllStops()).thenReturn(List.of());

        mockMvc.perform(get("/stops")).andExpectAll(status().isOk(),
                jsonPath("$").isArray());
    }
}
