package be.vdab.ToysForBoys.services;

import be.vdab.ToysForBoys.domain.Product;

import java.util.Optional;

public interface ProductService {
    int updateInstock(int id,int value);
    int updateInorder(int id,int value);
    Optional<Product> findById(int id);
}
