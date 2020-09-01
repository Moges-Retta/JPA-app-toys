package be.vdab.ToysForBoys.services;

import be.vdab.ToysForBoys.domain.Product;

import java.util.Optional;

public class DefaultProductService implements ProductService{
    @Override
    public int updateInstock(int id, int value) {
        return 0;
    }

    @Override
    public int updateInorder(int id, int value) {
        return 0;
    }

    @Override
    public Optional<Product> findById(int id) {
        return Optional.empty();
    }
}
