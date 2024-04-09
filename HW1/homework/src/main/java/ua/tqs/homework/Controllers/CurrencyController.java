package ua.tqs.homework.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tqs.homework.Services.CurrencyExchangeService;

import java.util.Map;

@RestController
@RequestMapping("/exchangeRates")
public class CurrencyController {

    private final CurrencyExchangeService currencyExchangeService;
    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    public CurrencyController(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }

    @GetMapping
    public Map<String, Object> getExchangeRates() {
        logger.info("Request received for fetching exchange rates...");
        Map<String, Object> exchangeRates = currencyExchangeService.getExchangeRates();
        logger.info("Exchange rates fetched successfully.");
        return exchangeRates;
    }
}
