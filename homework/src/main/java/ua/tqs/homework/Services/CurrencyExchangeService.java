package ua.tqs.homework.Services;


import jakarta.persistence.Access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
public class CurrencyExchangeService {

    private static final String EXCHANGE_RATE_API_URL = "https://api.frankfurter.app/latest";

    private final RestTemplate restTemplate;

    @Autowired
    public CurrencyExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "exchangeRates", key = "#root.methodName")
    public Map<String, Object> getExchangeRates() {
        Map<String, Object> exchangeRates = restTemplate.getForObject(EXCHANGE_RATE_API_URL, Map.class);
        System.out.println("Exchange Rates: " + exchangeRates);
        return exchangeRates;
    }



}
