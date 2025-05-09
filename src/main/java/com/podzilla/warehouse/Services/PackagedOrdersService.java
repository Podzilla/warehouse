package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Models.PackagedOrders;
import com.podzilla.warehouse.Repositories.PackagedOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PackagedOrdersService {
    @Autowired
    private PackagedOrdersRepository packagedOrdersRepository;

    public Page<PackagedOrders> findByOrderId(Long orderId, Pageable pageable) {
        return packagedOrdersRepository.findByOrderId(orderId, pageable);
    }

    public Page<PackagedOrders> findByPackagerId(Long packagerId, Pageable pageable) {
        return packagedOrdersRepository.findByPackagerId(packagerId, pageable);
    }

    public Page<PackagedOrders> findByPackagerIdIsNull(Pageable pageable) {
        return packagedOrdersRepository.findByPackagerIdIsNull(pageable);
    }

    public PackagedOrders packageOrder(Long orderId, Long packagerId) {
        PackagedOrders packagedOrder = new PackagedOrders(orderId, packagerId);
        return packagedOrdersRepository.save(packagedOrder);
    }
}
