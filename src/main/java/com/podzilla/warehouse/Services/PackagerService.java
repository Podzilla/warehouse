package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Models.Packager;
import com.podzilla.warehouse.Repositories.PackagerRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@CacheConfig(cacheNames = {"packagers"})
public class PackagerService {

    private final PackagerRepository packagerRepository;

    public PackagerService(PackagerRepository packagerRepository) {
        this.packagerRepository = packagerRepository;
    }

    @Cacheable(value = "packagerById", key = "#id")
    public Optional<Packager> getPackagerById(UUID id) {
        return packagerRepository.findById(id);
    }

    public Page<Packager> getAllPackagers(Pageable pageable) {
        return packagerRepository.findAll(pageable);
    }

    @CachePut(value = "packagerById", key = "#result.id")
    public Packager createPackager(Packager packager) {
        return packagerRepository.save(packager);
    }

    @CachePut(value = "packagerById", key = "#id")
    public Packager updatePackager(UUID id, Packager updated) {
        return packagerRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setActive(updated.isActive());
            return packagerRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Packager not found"));
    }

    @CacheEvict(value = {"packagerById", "allPackagers"}, key = "#id", allEntries = true)
    public void deletePackager(UUID id) {
        packagerRepository.deleteById(id);
    }
}
