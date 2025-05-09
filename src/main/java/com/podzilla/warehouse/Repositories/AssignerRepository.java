package com.podzilla.warehouse.Repositories;

import com.podzilla.warehouse.Models.Assigner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignerRepository extends JpaRepository<Assigner, Long> {
}
