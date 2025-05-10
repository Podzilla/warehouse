package com.podzilla.warehouse.Repositories;

import com.podzilla.warehouse.Models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {
    List<Stock> findByName(String name);
    List<Stock> findByQuantityLessThanEqual(Integer quantity);
    List<Stock> findByQuantityLessThanEqualOrderByQuantityAsc(Integer quantity);
    @Query("SELECT s FROM Stock s WHERE s.quantity <= s.threshold")
    List<Stock> findByQuantityLessThanOrEqualToThreshold();
}
