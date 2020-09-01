package be.vdab.ToysForBoys.services;

import be.vdab.ToysForBoys.domain.Order;
import be.vdab.ToysForBoys.domain.Status;

import java.time.LocalDate;
import java.util.Optional;

public interface OrderService {
    Optional<Order> findById(int id);
    void updateStatusValue(int id, Status status);
    void updateShippedValue(int id, LocalDate shipped);
}
