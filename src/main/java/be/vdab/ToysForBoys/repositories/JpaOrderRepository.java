package be.vdab.ToysForBoys.repositories;


import be.vdab.ToysForBoys.domain.Order;
import be.vdab.ToysForBoys.domain.Status;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaOrderRepository implements OrderRepository {
    private EntityManager manager;

    public JpaOrderRepository(EntityManager manager) {
        this.manager = manager;
    }
    @Override
    public Optional<Order> findById(int id) {
        return Optional.ofNullable(manager.find(Order.class,id));
    }

    @Override
    public List<Order> findByStaus(List<Status> status) {
        return manager.createNamedQuery("Order.findByStatus", Order.class)
                .setParameter("statusValues", status)
                .setHint("javax.persistence.loadgraph",
                        manager.createEntityGraph("Order.MET_CUSTOMER"))
                .getResultList();
    }

    @Override
    public int updateStatus(int id,Status status) {
        return manager.createNamedQuery("Order.updateStatus")
                .setParameter("status",status)
                .setParameter("id",id)
                .executeUpdate();
    }

    @Override
    public int updateShipped(int id, LocalDate shipped) {
        return manager.createNamedQuery("Order.updateShipped")
                .setParameter("shipped",shipped)
                .setParameter("id",id)
                .executeUpdate();
    }
}
