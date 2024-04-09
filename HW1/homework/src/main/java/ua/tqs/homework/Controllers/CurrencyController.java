package ua.tqs.homework.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tqs.homework.Services.CurrencyExchangeService;

import java.util.Map;

@RestController
@RequestMapping("/exchangeRates")
public class CurrencyController {

    private final CurrencyExchangeService currencyExchangeService;

    public CurrencyController(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }


    @GetMapping
    public Map<String, Object> getExchangeRates() {
        return currencyExchangeService.getExchangeRates();
    }

}
