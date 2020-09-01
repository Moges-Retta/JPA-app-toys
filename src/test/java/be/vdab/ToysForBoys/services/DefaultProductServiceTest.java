package be.vdab.ToysForBoys.services;

import be.vdab.ToysForBoys.domain.*;
import be.vdab.ToysForBoys.exceptions.OrderNietGevondenException;
import be.vdab.ToysForBoys.exceptions.ProductNietGevondenException;
import be.vdab.ToysForBoys.repositories.OrderRepository;
import be.vdab.ToysForBoys.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultProductServiceTest {
    private ProductService service;
    @Mock
    private ProductRepository repository;
    private Product product;
    @BeforeEach
    void beforeEach(){
        var productline = new Productline("test","test",1);
        product = new Product("test","test","test",1,
                1, BigDecimal.ONE, 1,productline);
        service = new DefaultProductService(repository);
    }
    @Test
    void updateInstockValue(){
        when(repository.findById(1)).thenReturn(Optional.of(product));
        service.updateInstockValue(1,2);
        assertThat(product.getInStock()).isEqualTo(2);
        verify(repository).findById(1);
    }
    @Test
    void updateInorderValue(){
        when(repository.findById(1)).thenReturn(Optional.of(product));
        service.updateInstockValue(1,2);
        assertThat(product.getInStock()).isEqualTo(2);
        verify(repository).findById(1);
    }

    @Test
    void updateInStockOnbestaandeOrder() {
        assertThatExceptionOfType(ProductNietGevondenException.class)
                .isThrownBy(()->service.updateInstockValue(-1,2));
        verify(repository).findById(-1);
    }
    @Test
    void updateInOrderOnbestaandeOrder() {
        assertThatExceptionOfType(ProductNietGevondenException.class)
                .isThrownBy(()->service.updateInorderValue(-1,2));
        verify(repository).findById(-1);
    }

}
