package be.vdab.ToysForBoys.repositories;

import be.vdab.ToysForBoys.domain.Order;
import be.vdab.ToysForBoys.domain.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(int id);
    List<Order> findByStaus(List<Status> status);
    int updateStatus(int id,Status status);
    int updateShipped(int id, LocalDate shipped);

}
