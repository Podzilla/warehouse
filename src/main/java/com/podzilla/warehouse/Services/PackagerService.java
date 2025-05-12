package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Models.Packager;
import com.podzilla.warehouse.Repositories.PackagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

@Service
public class PackagerService {

    private final PackagerRepository packagerRepository;

    public PackagerService(PackagerRepository packagerRepository) {
        this.packagerRepository = packagerRepository;
    }

    public Page<Packager> getAllPackagers(Pageable pageable) {
        return packagerRepository.findAll(pageable);
    }

    public Optional<Packager> getPackagerById(UUID id) {
        return packagerRepository.findById(id);
    }

    public Packager createPackager(Packager packager) {
        return packagerRepository.save(packager);
    }

    public Packager updatePackager(UUID id, Packager updated) {
        return packagerRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setActive(updated.isActive());
            return packagerRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Packager not found"));
    }

    public void deletePackager(UUID id) {
        packagerRepository.deleteById(id);
    }
}
