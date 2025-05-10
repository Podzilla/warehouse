package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Models.Packager;
import com.podzilla.warehouse.Repositories.PackagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class PackagerService {

    private final PackagerRepository packagerRepository;

    public PackagerService(PackagerRepository packagerRepository) {
        this.packagerRepository = packagerRepository;
    }

    public Page<Packager> getAllPackagers(Pageable pageable) {
        return packagerRepository.findAll(pageable);
    }

    public Optional<Packager> getPackagerById(Long id) {
        return packagerRepository.findById(id);
    }

    public Optional<Packager> createPackager(Packager packager) {
        return Optional.of(packagerRepository.save(packager));
    }

    public Optional<Packager> updatePackager(Long id, Packager updated) {
        return Optional.ofNullable(packagerRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setActive(updated.isActive());
            return packagerRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Packager not found")));
    }

    public void deletePackager(Long id) {
        packagerRepository.deleteById(id);
    }
}
