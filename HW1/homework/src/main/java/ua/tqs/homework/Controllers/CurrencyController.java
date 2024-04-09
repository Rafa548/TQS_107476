package ua.tqs.homework.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tqs.homework.Services.CurrencyExchangeService;

import java.util.Map;

@RestController
@RequestMapping("/exchangeRates")
@Tag(name = "Currency Exchange", description = "Endpoints for fetching currency exchange rates")
public class CurrencyController {

    private final CurrencyExchangeService currencyExchangeService;
    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    public CurrencyController(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }

    @GetMapping
    @Operation(summary = "Get Exchange Rates", description = "Fetches the latest exchange rates")
    public Map<String, Object> getExchangeRates() {
        logger.info("Request received for fetching exchange rates...");
        Map<String, Object> exchangeRates = currencyExchangeService.getExchangeRates();
        logger.info("Exchange rates fetched successfully.");
        return exchangeRates;
    }
}

