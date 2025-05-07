package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Models.PackagedOrders;
import com.podzilla.warehouse.Repositories.PackagedOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackagedOrdersService {
    @Autowired
    private PackagedOrdersRepository packagedOrdersRepository;

    public Optional<List<PackagedOrders>> findByOrderId(Long orderId) {
        return Optional.ofNullable(packagedOrdersRepository.findByOrderId(orderId));
    }
    
    public Optional<List<PackagedOrders>> findByPackagerId(Long packagerId) {
        return Optional.ofNullable(packagedOrdersRepository.findByPackagerId(packagerId));
    }
    
    public Optional<List<PackagedOrders>> findByPackagerIdIsNull() {
        return Optional.ofNullable(packagedOrdersRepository.findByPackagerIdIsNull());
    }

    public PackagedOrders packageOrder(Long orderId, Long packagerId) {
        PackagedOrders packagedOrder = new PackagedOrders(orderId, packagerId);
        return packagedOrdersRepository.save(packagedOrder);
    }
}