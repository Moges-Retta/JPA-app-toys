package be.vdab.ToysForBoys.services;

import be.vdab.ToysForBoys.domain.Product;

import java.util.Optional;

public interface ProductService {
    void updateInstockValue(int id,int value);
    void updateInorderValue(int id,int value);
    Optional<Product> findById(int id);
}
