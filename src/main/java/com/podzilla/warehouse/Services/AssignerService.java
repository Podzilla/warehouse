package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Models.Assigner;
import com.podzilla.warehouse.Models.AssignmentStatus;
import com.podzilla.warehouse.Repositories.AssignmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignerService {
    @Autowired
    private AssignmentsRepository assignmentsRepository;

    public Optional<Assigner> findByOrderId(Long orderId) {
        return assignmentsRepository.findByOrderId(orderId);
    }

    public Assigner assignOrder(Long orderId, String courierId) {
        //TODO Add logic to check order and courier validity

        Assigner assignment = new Assigner(orderId, courierId, AssignmentStatus.PENDING);
        return assignmentsRepository.save(assignment);
    }

}
