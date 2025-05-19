package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Models.Manager;
import com.podzilla.warehouse.Repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
@CacheConfig(cacheNames = {"managers"})
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @CachePut(key = "'manager:id:' + #result.id")
    public Manager createManager(String name, String email, String department, String phoneNumber) {
        Manager manager = new Manager(name, email, department, phoneNumber);
        return managerRepository.save(manager);
    }

    @CachePut(key = "'manager:id:' + #result.id")
    public Manager createManager(String name, String email, String department) {
        Manager manager = new Manager(name, email, department);
        return managerRepository.save(manager);
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    @Cacheable(key = "'active'")
    public List<Manager> getActiveManagers() {
        return managerRepository.findByIsActiveTrue();
    }

    @Cacheable(key = "'inactive'")
    public List<Manager> getInactiveManagers() {
        return managerRepository.findByIsActiveFalse();
    }

    @Cacheable(key = "'manager:id:' + #id")
    public Optional<Manager> getManagerById(UUID id) {
        return managerRepository.findById(id);
    }

    @Cacheable(key = "'manager:email:' + #email")
    public Optional<Manager> getManagerByEmail(String email) {
        return managerRepository.findByEmail(email);
    }

    public List<Manager> getManagersByDepartment(String department) {
        return managerRepository.findByDepartment(department);
    }

    @CachePut(key = "'manager:id:' + #id")
    public Optional<Manager> updateManager(UUID id, Manager managerDetails) {
        return managerRepository.findById(id)
                .map(manager -> {
                    if (managerDetails.getName() != null) manager.setName(managerDetails.getName());
                    if (managerDetails.getEmail() != null) manager.setEmail(managerDetails.getEmail());
                    if (managerDetails.getDepartment() != null) manager.setDepartment(managerDetails.getDepartment());
                    if (managerDetails.getPhoneNumber() != null) manager.setPhoneNumber(managerDetails.getPhoneNumber());
                    if (managerDetails.getIsActive() != null) manager.setIsActive(managerDetails.getIsActive());
                    return managerRepository.save(manager);
                });
    }

    @CachePut(key = "'manager:id:' + #id")
    public Optional<Manager> activateManager(UUID id) {
        return managerRepository.findById(id)
                .map(manager -> {
                    manager.setIsActive(true);
                    return managerRepository.save(manager);
                });
    }

    @CachePut(key = "'manager:id:' + #id")
    public Optional<Manager> deactivateManager(UUID id) {
        return managerRepository.findById(id)
                .map(manager -> {
                    manager.setIsActive(false);
                    return managerRepository.save(manager);
                });
    }

    @CacheEvict(key = "'manager:id:' + #id")
    public boolean deleteManager(UUID id) {
        if (managerRepository.existsById(id)) {
            managerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @CacheEvict(allEntries = true)
    public void clearManagerCache() {
        // Clears all cache entries under 'managers'
    }
}
