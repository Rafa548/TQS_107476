package ua.tqs;

import java.util.List;

public class StocksPortfolio {
   private IStockMarketService stockMarket;
   private List<Stock> stocks;

    public IStockMarketService getStockMarket() {
        return stockMarket;
    }

    public void setStockMarket(IStockMarketService stockMarket) {
        this.stockMarket = stockMarket;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public StocksPortfolio(IStockMarketService stockMarket) {
        this.stockMarket = stockMarket;
    }

    public double getTotalValue() {
        double total = 0;
        for (Stock s : stocks) {
            total += s.getQuantity() * stockMarket.lookUpPrice(s.getLabel());
        }
        return total;
    }

}
