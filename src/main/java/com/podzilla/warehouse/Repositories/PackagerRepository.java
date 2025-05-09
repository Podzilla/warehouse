package com.podzilla.warehouse.Repositories;

import com.podzilla.warehouse.Models.Packager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackagerRepository extends JpaRepository<Packager, Long> {
}
