//package com.podzilla.warehouse.Services;
//
//import com.podzilla.warehouse.Models.Stock;
//import com.podzilla.warehouse.Repositories.StockRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class StockServiceTests {
//
//    private StockRepository stockRepository;
//    private StockService stockService;
//
//    @BeforeEach
//    void setUp() {
//        stockRepository = mock(StockRepository.class);
//        stockService = new StockService();
//        stockService.setStockRepository(stockRepository);
//    }
//
//    @Test
//    void testCreateStock() {
//        // Arrange
//        Stock mockStock = new Stock("Item A", 10, 5);
//        when(stockRepository.save(any(Stock.class))).thenReturn(mockStock);
//
//        // Act
//        Stock result = stockService.createStock("Item A", 10, 5);
//
//        // Assert
//        assertEquals("Item A", result.getName());
//        assertEquals(10, result.getQuantity());
//        assertEquals(5, result.getThreshold());
//        verify(stockRepository, times(1)).save(any(Stock.class));
//    }
//
//    @Test
//    void testGetAllStocks() {
//        // Arrange
//        PageRequest pageable = PageRequest.of(0, 5);
//        List<Stock> stocks = List.of(new Stock("Item B", 20, 8));
//        when(stockRepository.findAll(pageable)).thenReturn(new PageImpl<>(stocks));
//
//        // Act
//        Page<Stock> result = stockService.getAllStocks(pageable);
//
//        // Assert
//        assertEquals(1, result.getTotalElements());
//        assertEquals("Item B", result.getContent().get(0).getName());
//    }
//
//    @Test
//    void testGetStockByIdFound() {
//        // Arrange
//        UUID id = UUID.randomUUID();
//        Stock stock = new Stock("Item C", 30, 10);
//        when(stockRepository.findById(id)).thenReturn(Optional.of(stock));
//
//        // Act
//        Optional<Stock> result = stockService.getStockById(id);
//
//        // Assert
//        assertTrue(result.isPresent());
//        assertEquals("Item C", result.get().getName());
//    }
//
//    @Test
//    void testGetStockByIdNotFound() {
//        // Arrange
//        UUID id = UUID.randomUUID();
//        when(stockRepository.findById(id)).thenReturn(Optional.empty());
//
//        // Act
//        Optional<Stock> result = stockService.getStockById(id);
//
//        // Assert
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void testGetStocksByName() {
//        // Arrange
//        PageRequest pageable = PageRequest.of(0, 5);
//        when(stockRepository.findByName("Item D", pageable))
//                .thenReturn(new PageImpl<>(List.of(new Stock("Item D", 40, 15))));
//
//        // Act
//        Page<Stock> result = stockService.getStocksByName("Item D", pageable);
//
//        // Assert
//        assertEquals(1, result.getContent().size());
//        assertEquals("Item D", result.getContent().get(0).getName());
//    }
//
//    @Test
//    void testGetStocksBelowQuantity() {
//        // Arrange
//        PageRequest pageable = PageRequest.of(0, 5);
//        when(stockRepository.findByQuantityLessThanEqual(20, pageable))
//                .thenReturn(new PageImpl<>(List.of(new Stock("Item E", 15, 5))));
//
//        // Act
//        Page<Stock> result = stockService.getStocksBelowQuantity(20, pageable);
//
//        // Assert
//        assertEquals(1, result.getContent().size());
//        assertTrue(result.getContent().get(0).getQuantity() <= 20);
//    }
//
//    @Test
//    void testGetStocksBelowThreshold() {
//        // Arrange
//        PageRequest pageable = PageRequest.of(0, 5);
//        when(stockRepository.findByQuantityLessThanOrEqualToThreshold(pageable))
//                .thenReturn(new PageImpl<>(List.of(new Stock("Item F", 5, 10))));
//
//        // Act
//        Page<Stock> result = stockService.getStocksBelowThreshold(pageable);
//
//        // Assert
//        assertEquals(1, result.getContent().size());
//    }
//
//    @Test
//    void testUpdateStockSuccess() {
//        // Arrange
//        UUID id = UUID.randomUUID();
//        Stock existing = new Stock("Old Name", 5, 2);
//        when(stockRepository.findById(id)).thenReturn(Optional.of(existing));
//        when(stockRepository.save(any(Stock.class))).thenReturn(existing);
//
//        // Act
//        Optional<Stock> updated = stockService.updateStock(id, "New Name", 10, 3);
//
//        // Assert
//        assertTrue(updated.isPresent());
//        assertEquals("New Name", updated.get().getName());
//        assertEquals(10, updated.get().getQuantity());
//        assertEquals(3, updated.get().getThreshold());
//    }
//
//    @Test
//    void testUpdateStockNotFound() {
//        // Arrange
//        UUID id = UUID.randomUUID();
//        when(stockRepository.findById(id)).thenReturn(Optional.empty());
//
//        // Act
//        Optional<Stock> result = stockService.updateStock(id, "X", 1, 1);
//
//        // Assert
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void testDeleteStockSuccess() {
//        // Arrange
//        UUID id = UUID.randomUUID();
//        when(stockRepository.existsById(id)).thenReturn(true);
//
//        // Act
//        boolean result = stockService.deleteStock(id);
//
//        // Assert
//        assertTrue(result);
//        verify(stockRepository).deleteById(id);
//    }
//
//    @Test
//    void testDeleteStockNotFound() {
//        // Arrange
//        UUID id = UUID.randomUUID();
//        when(stockRepository.existsById(id)).thenReturn(false);
//
//        // Act
//        boolean result = stockService.deleteStock(id);
//
//        // Assert
//        assertFalse(result);
//        verify(stockRepository, never()).deleteById(any());
//    }
//}
