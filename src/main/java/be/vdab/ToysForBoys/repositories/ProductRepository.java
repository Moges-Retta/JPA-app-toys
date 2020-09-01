package be.vdab.ToysForBoys.repositories;

import be.vdab.ToysForBoys.domain.Order;
import be.vdab.ToysForBoys.domain.Product;

import java.util.Optional;

public interface ProductRepository {
    int updateInstock(int id,int value);
    int updateInorder(int id,int value);
    Optional<Product> findById(int id);
}
