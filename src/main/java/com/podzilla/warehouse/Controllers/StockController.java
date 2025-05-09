package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Models.Stock;
import com.podzilla.warehouse.Services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("warehouse/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    //@PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestParam String name,
                                            @RequestParam Integer quantity,
                                            @RequestParam Integer threshold) {
        Stock stock = stockService.createStock(name, quantity, threshold);
        return ResponseEntity.status(HttpStatus.CREATED).body(stock);
    }

   // @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    //@PreAuthorize("hasAnyRole('MANAGER', 'ASSIGNER', 'PACKAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable UUID id) {
        Optional<Stock> stock = stockService.getStockById(id);
        return stock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //@PreAuthorize("hasAnyRole('MANAGER', 'ASSIGNER', 'PACKAGER')")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Stock>> getStocksByName(@PathVariable String name) {
        List<Stock> stocks = stockService.getStocksByName(name);
        return stocks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(stocks);
    }

   // @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/below-quantity/{quantity}")
    public ResponseEntity<List<Stock>> getStocksBelowQuantity(@PathVariable Integer quantity) {
        List<Stock> stocks = stockService.getStocksBelowQuantity(quantity);
        return stocks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(stocks);
    }

    //@PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/below-threshold")
    public ResponseEntity<List<Stock>> getStocksBelowThreshold() {
        List<Stock> stocks = stockService.getStocksBelowThreshold();
        return stocks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(stocks);
    }

    //@PreAuthorize("hasAnyRole('MANAGER', 'ASSIGNER')")
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable UUID id,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) Integer quantity,
                                           @RequestParam(required = false) Integer threshold) {
        Optional<Stock> updatedStock = stockService.updateStock(id, name, quantity, threshold);
        return updatedStock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //@PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable UUID id) {
        boolean deleted = stockService.deleteStock(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}