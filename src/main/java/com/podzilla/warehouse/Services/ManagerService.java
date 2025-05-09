package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Models.Manager;
import com.podzilla.warehouse.Repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;

    public Manager createManager(String name, String email, String department, String phoneNumber) {
        Manager manager = new Manager(name, email, department, phoneNumber);
        return managerRepository.save(manager);
    }

    public Manager createManager(String name, String email, String department) {
        Manager manager = new Manager(name, email, department);
        return managerRepository.save(manager);
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public List<Manager> getActiveManagers() {
        return managerRepository.findByIsActiveTrue();
    }

    public List<Manager> getInactiveManagers() {
        return managerRepository.findByIsActiveFalse();
    }

    public Optional<Manager> getManagerById(Long id) {
        return managerRepository.findById(id);
    }

    public Optional<Manager> getManagerByEmail(String email) {
        return managerRepository.findByEmail(email);
    }

    public List<Manager> getManagersByDepartment(String department) {
        return managerRepository.findByDepartment(department);
    }

    public Optional<Manager> updateManager(Long id, Manager managerDetails) {
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

    public Optional<Manager> activateManager(Long id) {
        return managerRepository.findById(id)
                .map(manager -> {
                    manager.setIsActive(true);
                    return managerRepository.save(manager);
                });
    }

    public Optional<Manager> deactivateManager(Long id) {
        return managerRepository.findById(id)
                .map(manager -> {
                    manager.setIsActive(false);
                    return managerRepository.save(manager);
                });
    }

    public boolean deleteManager(Long id) {
        if (managerRepository.existsById(id)) {
            managerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}