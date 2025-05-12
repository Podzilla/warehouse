package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Models.Assigner;
import com.podzilla.warehouse.Repositories.AssignerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AssignerService {
    @Autowired
    private AssignerRepository assignerRepository;

    public Optional<Assigner> createAssigner(Assigner assigner) {
        return Optional.of(assignerRepository.save(assigner));
    }

    public Page<Assigner> getAllAssigners(Pageable pageable) {
        return assignerRepository.findAll(pageable);
    }

    public boolean deleteAssigner(UUID id) {
        if (assignerRepository.existsById(id)) {
            assignerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Assigner> activateAssigner(UUID id) {
        return assignerRepository.findById(id).map(assigner -> {
            assigner.setActive(true);
            return assignerRepository.save(assigner);
        });
    }

    public Optional<Assigner> deactivateAssigner(UUID id) {
        return assignerRepository.findById(id).map(assigner -> {
            assigner.setActive(false);
            return assignerRepository.save(assigner);
        });
    }
}
