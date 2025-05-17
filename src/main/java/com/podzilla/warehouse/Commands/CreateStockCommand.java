package com.podzilla.warehouse.Commands;

import com.podzilla.warehouse.Models.Stock;
import com.podzilla.warehouse.Services.StockService;

public class CreateStockCommand implements Command {
    private StockService stockService;
    private String name;
    private Integer quantity;
    private Integer threshold;
    private String category;
    private double cost;

    private Stock createdStock;

    public CreateStockCommand(StockService stockService, String name, Integer quantity, Integer threshold, String category, double cost) {
        this.stockService = stockService;
        this.name = name;
        this.quantity = quantity;
        this.threshold = threshold;
        this.category = category;
        this.cost = cost;
    }

    @Override
    public void execute() {
        this.createdStock = stockService.createStock(name, quantity, threshold, category, cost);
    }

    public Stock getCreatedStock() {
        return createdStock;
    }
}
