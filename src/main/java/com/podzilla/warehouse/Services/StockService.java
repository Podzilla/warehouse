package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Events.EventFactory;
import com.podzilla.warehouse.Events.InventorySnapshotEvent;
import com.podzilla.warehouse.Events.ProductEvent;
import com.podzilla.warehouse.Models.Stock;
import com.podzilla.warehouse.Repositories.StockRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@CacheConfig(cacheNames = {"stocks"})
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @CachePut(value = "stocks", key = "#result.id")
    public Stock createStock(String name, Integer quantity, Integer threshold, String category, double cost) {
        Stock stock = new Stock(name, quantity, threshold, category, cost);
        Stock saved = stockRepository.save(stock);

        ProductEvent event = EventFactory.createProductEvent(
                LocalDateTime.now(),
                saved.getId(),
                saved.getName(),
                saved.getCategory(),
                saved.getPrice(),
                saved.getThreshold()
        );

        rabbitTemplate.convertAndSend("analytics.exchange", "analytics.product.changed", event);

        return saved;
    }

    @Cacheable(value = "stocksAll")
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Cacheable(value = "stockById", key = "#id")
    public Optional<Stock> getStockById(UUID id) {
        return stockRepository.findById(id);
    }

    @Cacheable(value = "stocksByName", key = "#name")

    public List<Stock> getStocksByName(String name) {
        return stockRepository.findByName(name);
    }

    @Cacheable(value = "stocksBelowQuantity", key = "#quantity")

    public List<Stock> getStocksBelowQuantity(Integer quantity) {
        return stockRepository.findByQuantityLessThanEqual(quantity);
    }

    @Cacheable("stocksBelowThreshold")

    public List<Stock> getStocksBelowThreshold() {
        return stockRepository.findByQuantityLessThanOrEqualToThreshold();
    }

    @CachePut(value = "stockById", key = "#id")

    public Optional<Stock> updateStock(UUID id, String name, Integer quantity, Integer threshold, String category, Double cost) {
        return stockRepository.findById(id)
                .map(stock -> {
                    if (name != null) stock.setName(name);
                    if (quantity != null) stock.setQuantity(quantity);
                    if (threshold != null) stock.setThreshold(threshold);
                    if (category != null) stock.setCategory(category);
                    if (cost != null) stock.setPrice(cost);

                    Stock updated = stockRepository.save(stock);

                    ProductEvent event = EventFactory.createProductEvent(
                            LocalDateTime.now(),
                            updated.getId(),
                            updated.getName(),
                            updated.getCategory(),
                            updated.getPrice(),
                            updated.getThreshold()
                    );
                    rabbitTemplate.convertAndSend("analytics.exchange", "analytics.product.changed", event);

                    return updated;
                });
    }

    public InventorySnapshotEvent generateSnapshot(UUID warehouseId) {
        List<InventorySnapshotEvent.ProductSnapshot> productSnapshots = stockRepository.findAll().stream()
                .map(stock -> EventFactory.createProductSnapshot(stock.getId(), stock.getQuantity()))
                .toList();

        return EventFactory.createInventorySnapshotEvent(LocalDateTime.now(), warehouseId, productSnapshots);
    }

    @CacheEvict(value = {"stockById", "stocksAll", "stocksByName", "stocksBelowQuantity", "stocksBelowThreshold"}, key = "#id", allEntries = true)
    public boolean deleteStock(UUID id) {
        if (stockRepository.existsById(id)) {
            stockRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
