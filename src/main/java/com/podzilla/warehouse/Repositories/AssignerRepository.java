package com.podzilla.warehouse.Repositories;

import com.podzilla.warehouse.Models.Assigner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface AssignerRepository extends JpaRepository<Assigner, Long> {

    Optional<Assigner> createAssigner(Assigner assigner);
    Page<Assigner> getAllAssigners(Pageable pageable);
    boolean deleteAssigner(Long id);
    Optional<Assigner> activateAssigner(Long id);
    Optional<Assigner> deactivateAssigner(Long id);

}
