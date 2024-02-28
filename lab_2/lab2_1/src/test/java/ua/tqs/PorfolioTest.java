package ua.tqs;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PortfolioTest {

    @Mock
    IStockMarketService stockMarket;

    @InjectMocks
    StocksPortfolio portfolio;

    @Test
    void testTotalValue() {
        Stock googleStock = new Stock("1", 50);
        Stock appleStock = new Stock("2", 100);

        portfolio.setStocks(List.of(googleStock, appleStock));

        //System.out.println(portfolio.getStocks());
        //System.out.println(stockMarket.lookUpPrice("1")); //-> 0 default para o mock

        when(stockMarket.lookUpPrice("1")).thenReturn(50); //retorna algum valor
        when(stockMarket.lookUpPrice("2")).thenReturn(100);
        //when(stockMarket.lookUpPrice(anyString())).thenReturn(0); //retorna 0 para qualquer string
        //when(stockMarket.lookUpPrice("3")).thenReturn(0); // desnecessario logo deu erro/aviso

        double result = portfolio.getTotalValue();

        //System.out.println(result);

        assertThat(result, equalTo(12500.0));
        //ou
        assertEquals(12500, result);

        verify(stockMarket, atLeast(1)).lookUpPrice(anyString()); //desnecessario não é o objetivo do teste
    }
}

