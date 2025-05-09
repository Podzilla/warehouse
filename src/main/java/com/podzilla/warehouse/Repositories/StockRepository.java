package com.podzilla.warehouse.Repositories;

import com.podzilla.warehouse.Models.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {
    Page<Stock> findByName(String name, Pageable pageable);
    Page<Stock> findByQuantityLessThanEqual(Integer quantity, Pageable pageable);
    Page<Stock> findByQuantityLessThanEqualOrderByQuantityAsc(Integer quantity,Pageable pageable);
    @Query("SELECT s FROM Stock s WHERE s.quantity <= s.threshold")
    Page<Stock> findByQuantityLessThanOrEqualToThreshold(Pageable pageable);
}
