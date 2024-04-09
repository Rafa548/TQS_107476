package ua.tqs.homework.Services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Service
public class CurrencyExchangeService {

    private static final String EXCHANGE_RATE_API_URL = "https://api.frankfurter.app/latest";
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeService.class);

    @Autowired
    public CurrencyExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "exchangeRates", key = "#root.methodName")
    public Map<String, Object> getExchangeRates() {
        logger.info("Fetching exchange rates from API...");
        Map<String, Object> exchangeRates = restTemplate.getForObject(EXCHANGE_RATE_API_URL, Map.class);
        logger.info("Exchange Rates: {}", exchangeRates);
        return exchangeRates;
    }
}
