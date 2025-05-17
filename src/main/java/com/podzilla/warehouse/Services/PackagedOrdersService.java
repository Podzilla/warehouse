package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Events.EventFactory;
import com.podzilla.warehouse.Models.PackagedOrders;
import com.podzilla.warehouse.Repositories.PackagedOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PackagedOrdersService {

    @Autowired
    private PackagedOrdersRepository packagedOrdersRepository;

    public Page<PackagedOrders> findByOrderId(UUID orderId, Pageable pageable) {
        return packagedOrdersRepository.findByOrderId(orderId, pageable);
    }

    public Page<PackagedOrders> findByPackagerId(UUID packagerId, Pageable pageable) {
        return packagedOrdersRepository.findByPackagerId(packagerId, pageable);
    }

    public Page<PackagedOrders> findByPackagerIdIsNull(Pageable pageable) {
        return packagedOrdersRepository.findByPackagerIdIsNull(pageable);
    }

    public Optional<PackagedOrders> packageOrder(UUID packagerId) {
        PackagedOrders packagedOrder = new PackagedOrders(packagerId);
        return Optional.of(packagedOrdersRepository.save(packagedOrder));
    }
}
