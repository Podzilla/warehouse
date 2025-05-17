package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Models.*;
import com.podzilla.warehouse.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/seed")
public class DatabaseSeederController {

    @Autowired
    private AssignerRepository assignerRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private PackagerRepository packagerRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private AssignedOrdersRepository assignedOrdersRepository;

    @Autowired
    private PackagedOrdersRepository packagedOrdersRepository;

    @PostMapping("/all")
    public ResponseEntity<String> seedAllData() {
        seedAssigners();
        seedManagers();
        seedPackagers();
        seedStock();
        seedSampleOrders();
        return ResponseEntity.ok("Database seeded successfully!");
    }

    @PostMapping("/assigners")
    public ResponseEntity<String> seedAssigners() {
        List<Assigner> assigners = Arrays.asList(
                new Assigner("John Smith"),
                new Assigner("Emily Johnson"),
                new Assigner("Michael Brown"),
                new Assigner("Jessica Davis", false),
                new Assigner("David Wilson")
        );

        assignerRepository.saveAll(assigners);
        return ResponseEntity.ok("Assigners seeded successfully!");
    }

    @PostMapping("/managers")
    public ResponseEntity<String> seedManagers() {
        List<Manager> managers = Arrays.asList(
                new Manager("Robert Taylor", "robert.taylor@podzilla.com", "Operations", "555-1234"),
                new Manager("Sarah Martinez", "sarah.martinez@podzilla.com", "Logistics", "555-2345"),
                new Manager("Thomas Anderson", "thomas.anderson@podzilla.com", "Warehouse"),
                new Manager("Jennifer White", "jennifer.white@podzilla.com", "Distribution", "555-3456"),
                new Manager("Kevin Lee", "kevin.lee@podzilla.com", "Inventory")
        );

        managerRepository.saveAll(managers);
        return ResponseEntity.ok("Managers seeded successfully!");
    }

    @PostMapping("/packagers")
    public ResponseEntity<String> seedPackagers() {
        List<Packager> packagers = Arrays.asList(
                new Packager("Alex Garcia"),
                new Packager("Samantha Miller"),
                new Packager("Chris Thompson"),
                new Packager("Amanda Harris", false),
                new Packager("Daniel Lewis")
        );

        packagerRepository.saveAll(packagers);
        return ResponseEntity.ok("Packagers seeded successfully!");
    }

    @PostMapping("/stock")
    public ResponseEntity<String> seedStock() {
        List<Stock> stocks = Arrays.asList(
                new Stock("Laptop", 50, 10, "Electronics", 999.99),
                new Stock("Desktop Computer", 30, 5, "Electronics", 1299.99),
                new Stock("Monitor", 100, 20, "Electronics", 249.99),
                new Stock("Keyboard", 200, 30, "Accessories", 49.99),
                new Stock("Mouse", 250, 40, "Accessories", 29.99),
                new Stock("Headphones", 150, 25, "Accessories", 79.99),
                new Stock("Printer", 40, 8, "Office Equipment", 199.99),
                new Stock("Scanner", 25, 5, "Office Equipment", 149.99),
                new Stock("USB Drive", 500, 100, "Storage", 19.99),
                new Stock("External Hard Drive", 75, 15, "Storage", 89.99)
        );

        stockRepository.saveAll(stocks);
        return ResponseEntity.ok("Stock items seeded successfully!");
    }

    @PostMapping("/sample-orders")
    public ResponseEntity<String> seedSampleOrders() {
        // Get some packagers and assigners
        List<Packager> packagers = packagerRepository.findAll();
        List<Assigner> assigners = assignerRepository.findAll();

        if (packagers.isEmpty() || assigners.isEmpty()) {
            return ResponseEntity.badRequest().body("Please seed packagers and assigners first");
        }

        // Create some sample packaged orders
        LocalDateTime now = LocalDateTime.now();
        PackagedOrders order1 = new PackagedOrders(UUID.randomUUID(), packagers.get(0).getId(), now.minusDays(1));
        PackagedOrders order2 = new PackagedOrders(UUID.randomUUID(), packagers.get(1).getId(), now.minusDays(2));
        PackagedOrders order3 = new PackagedOrders(UUID.randomUUID(), packagers.get(2).getId(), now.minusHours(12));
        PackagedOrders order4 = new PackagedOrders(packagers.get(3).getId());
        PackagedOrders order5 = new PackagedOrders(packagers.get(4).getId());

        packagedOrdersRepository.saveAll(Arrays.asList(order1, order2, order3, order4, order5));

        // Create some assigned orders
        AssignedOrders assignment1 = new AssignedOrders(
                order1.getOrderId(),
                assigners.get(0).getId(),
                UUID.randomUUID(),
                now.minusHours(2)
        );

        AssignedOrders assignment2 = new AssignedOrders(
                order2.getOrderId(),
                assigners.get(1).getId(),
                UUID.randomUUID(),
                now.minusHours(4)
        );

        assignedOrdersRepository.saveAll(Arrays.asList(assignment1, assignment2));

        return ResponseEntity.ok("Sample orders seeded successfully!");
    }
}