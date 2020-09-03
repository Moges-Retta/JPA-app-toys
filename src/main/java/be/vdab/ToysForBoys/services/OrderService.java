package be.vdab.ToysForBoys.services;

import be.vdab.ToysForBoys.domain.Order;
import be.vdab.ToysForBoys.domain.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> findById(int id);
    List<Order> findByStatus(List<Status> status);
    void updateStatusValue(int id, Status status);
    void updateShippedValue(int id, LocalDate shipped);
}
