package ua.tqs.homework.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.tqs.homework.Services.CurrencyExchangeService;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyController.class)
class CurrencyExchangeControllerMockServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyExchangeService apiService;

    @Test
    void testExchange() throws Exception {
        Map<String, Object> mockExchangeRates = Map.of(
                "amount", 1.0,
                "base", "EUR",
                "date", "2024-04-08",
                "rates", Map.of(
                        "USD", 1.2,
                        "GBP", 0.9
                )
        );

        when(apiService.getExchangeRates()).thenReturn(mockExchangeRates);

        mockMvc.perform(get("/exchangeRates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1.0))
                .andExpect(jsonPath("$.base").value("EUR"))
                .andExpect(jsonPath("$.date").value("2024-04-08"))
                .andExpect(jsonPath("$.rates.USD").value(1.2))
                .andExpect(jsonPath("$.rates.GBP").value(0.9));
    }
}
