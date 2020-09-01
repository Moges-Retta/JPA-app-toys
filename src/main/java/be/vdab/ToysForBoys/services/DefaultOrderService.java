package be.vdab.ToysForBoys.services;

import be.vdab.ToysForBoys.domain.Order;
import be.vdab.ToysForBoys.domain.Status;
import be.vdab.ToysForBoys.exceptions.OrderNietGevondenException;
import be.vdab.ToysForBoys.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
@Service
public class DefaultOrderService implements OrderService{
    private OrderRepository repository;

    public DefaultOrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Order> findById(int id) {
        return Optional.of(repository.findById(id).get());
    }

    @Override
    public void updateStatusValue(int id, Status status) {
         repository.findById(id)
                .orElseThrow(OrderNietGevondenException::new)
                .updateStatus(status);
    }

    @Override
    public void updateShippedValue(int id, LocalDate shipped) {
         repository.findById(id)
                .orElseThrow(OrderNietGevondenException::new)
        .updateShipped(shipped);
    }
}
