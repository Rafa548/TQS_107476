package ua.tqs.homework.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceMockRepoTest {

    @Mock
    private RestTemplate restTemplate;

    @Test
    void testGetExchangeRates() {
        Map<String, Object> mockResponse = Map.of(
                "amount", 1.0,
                "base", "EUR",
                "date", "2024-04-08",
                "rates", Map.of(
                        "USD", 1.2,
                        "GBP", 0.9
                )
        );

        when(restTemplate.getForObject("https://api.frankfurter.app/latest", Map.class))
                .thenReturn(mockResponse);

        CurrencyExchangeService apiService = new CurrencyExchangeService(restTemplate);
        Map<String, Object> exchangeRates = apiService.getExchangeRates();
        verify(restTemplate, times(1)).getForObject("https://api.frankfurter.app/latest", Map.class);
        assertEquals(mockResponse, exchangeRates);
    }
}
