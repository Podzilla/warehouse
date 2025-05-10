package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Events.InventorySnapshotEvent;
import com.podzilla.warehouse.Models.Stock;
import com.podzilla.warehouse.Services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @Operation(summary = "Create new stock entry")
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestParam String name,
                                             @RequestParam Integer quantity,
                                             @RequestParam Integer threshold,
                                             @RequestParam String category,
                                             @RequestParam Double cost) {
        log.info("Creating stock: name={}, quantity={}, threshold={}, category={}, cost={}",
                name, quantity, threshold, category, cost);
        Stock stock = stockService.createStock(name, quantity, threshold, category, cost);
        return ResponseEntity.status(HttpStatus.CREATED).body(stock);
    }

    @Operation(summary = "Get all stock items")
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        log.info("Fetching all stocks");
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @Operation(summary = "Get stock by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable UUID id) {
        log.info("Fetching stock by id={}", id);
        Optional<Stock> stock = stockService.getStockById(id);
        return stock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get stock by name")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Stock>> getStocksByName(@PathVariable String name) {
        log.info("Fetching stock by name={}", name);
        List<Stock> stocks = stockService.getStocksByName(name);
        return stocks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(stocks);
    }

    @Operation(summary = "Get stock items with quantity less than or equal to given value")
    @GetMapping("/below-quantity/{quantity}")
    public ResponseEntity<List<Stock>> getStocksBelowQuantity(@PathVariable Integer quantity) {
        log.info("Fetching stocks with quantity <= {}", quantity);
        List<Stock> stocks = stockService.getStocksBelowQuantity(quantity);
        return stocks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(stocks);
    }

    @Operation(summary = "Get stock items below threshold")
    @GetMapping("/below-threshold")
    public ResponseEntity<List<Stock>> getStocksBelowThreshold() {
        log.info("Fetching stocks below threshold");
        List<Stock> stocks = stockService.getStocksBelowThreshold();
        return stocks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(stocks);
    }

    @Operation(summary = "Generate inventory snapshot for Analytics Service")
    @GetMapping("/snapshot")
    public ResponseEntity<InventorySnapshotEvent> getInventorySnapshot(@RequestParam UUID warehouseId) {
        log.info("Generating inventory snapshot for warehouseId={}", warehouseId);
        InventorySnapshotEvent snapshot = stockService.generateSnapshot(warehouseId);
        return ResponseEntity.ok(snapshot);
    }

    @Operation(summary = "Update stock details")
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable UUID id,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) Integer quantity,
                                             @RequestParam(required = false) Integer threshold,
                                             @RequestParam(required = false) String category,
                                             @RequestParam(required = false) Double cost) {
        log.info("Updating stock id={} with changes name={}, quantity={}, threshold={}, category={}, cost={}",
                id, name, quantity, threshold, category, cost);
        Optional<Stock> updatedStock = stockService.updateStock(id, name, quantity, threshold, category, cost);
        return updatedStock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete stock by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable UUID id) {
        log.info("Deleting stock with id={}", id);
        boolean deleted = stockService.deleteStock(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}