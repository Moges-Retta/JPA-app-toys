package be.vdab.ToysForBoys.services;

import be.vdab.ToysForBoys.domain.Product;
import be.vdab.ToysForBoys.exceptions.ProductNietGevondenException;
import be.vdab.ToysForBoys.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DefaultProductService implements ProductService{
    private ProductRepository repository;

    public DefaultProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateInstockValue(int id, int value) {
        repository.findById(id)
        .orElseThrow(ProductNietGevondenException::new)
        .updateInStock(value);
    }

    @Override
    public void updateInorderValue(int id, int value) {
        repository.findById(id)
                .orElseThrow(ProductNietGevondenException::new)
                .updateInOrder(value);
    }

    @Override
    public Optional<Product> findById(int id) {
        return Optional.of(repository.findById(id).get());
    }
}
