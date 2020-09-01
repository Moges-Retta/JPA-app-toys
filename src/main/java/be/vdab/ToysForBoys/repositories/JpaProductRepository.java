package be.vdab.ToysForBoys.repositories;

import be.vdab.ToysForBoys.domain.Order;
import be.vdab.ToysForBoys.domain.Product;
import be.vdab.ToysForBoys.domain.Status;

import javax.persistence.EntityManager;
import java.util.Optional;

public class JpaProductRepository implements ProductRepository{
    private EntityManager manager;

    public JpaProductRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Product> findById(int id) {
        return Optional.ofNullable(manager.find(Product.class,id));
    }

    @Override
    public int updateInstock(int id,int value) {
        if(value<0){
            throw new IllegalArgumentException();
        }
        return manager.createNamedQuery("Product.updateInStock")
                .setParameter("stockValue",value)
                .setParameter("id",id)
                .executeUpdate();
    }

    @Override
    public int updateInorder(int id,int value) {
        if(value<0){
            throw new IllegalArgumentException();
        }
        return manager.createNamedQuery("Product.updateInOrder")
                .setParameter("orderValue",value)
                .setParameter("id",id)
                .executeUpdate();
    }
}
