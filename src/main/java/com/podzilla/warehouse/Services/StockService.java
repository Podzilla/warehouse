package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Models.Stock;
import com.podzilla.warehouse.Repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public Stock createStock(String name, Integer quantity, Integer threshold) {
        Stock stock = new Stock(name, quantity, threshold);
        return stockRepository.save(stock);
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(UUID id) {
        return stockRepository.findById(id);
    }

    public List<Stock> getStocksByName(String name) {
        return stockRepository.findByName(name);
    }

    public List<Stock> getStocksBelowQuantity(Integer quantity) {
        return stockRepository.findByQuantityLessThanEqual(quantity);
    }

    public List<Stock> getStocksBelowThreshold() {
        return stockRepository.findByQuantityLessThanOrEqualToThreshold();
    }

    public Optional<Stock> updateStock(UUID id, String name, Integer quantity, Integer threshold) {
        return stockRepository.findById(id)
                .map(stock -> {
                    if (name != null) stock.setName(name);
                    if (quantity != null) stock.setQuantity(quantity);
                    if (threshold != null) stock.setThreshold(threshold);
                    return stockRepository.save(stock);
                });
    }

    public boolean deleteStock(UUID id) {
        if (stockRepository.existsById(id)) {
            stockRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
