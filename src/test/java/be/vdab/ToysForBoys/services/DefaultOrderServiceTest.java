package be.vdab.ToysForBoys.services;

import be.vdab.ToysForBoys.domain.Country;
import be.vdab.ToysForBoys.domain.Customer;
import be.vdab.ToysForBoys.domain.Order;
import be.vdab.ToysForBoys.domain.Status;
import be.vdab.ToysForBoys.exceptions.OrderNietGevondenException;
import be.vdab.ToysForBoys.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultOrderServiceTest {
    private OrderService service;
    @Mock
    private OrderRepository repository;
    private Order order;
    @BeforeEach
    void beforeEach(){
        var date = LocalDate.of(2001,1,1);
        var country = new Country("test",1);
        var customer = new Customer("test","test","test",
                "test","test",1,country);
        order = new Order(date,date,date,1,customer, Status.PROCESSING);
        service = new DefaultOrderService(repository);
    }
    @Test
    void updateStatusValue(){
        when(repository.findById(1)).thenReturn(Optional.of(order));
        service.updateStatusValue(1,Status.DISPUTED);
        assertThat(order.getStatus()).isEqualTo(Status.DISPUTED);
        verify(repository).findById(1);
    }
    @Test
    void opslagVoorOnbestaandeDocent() {
        assertThatExceptionOfType(OrderNietGevondenException.class)
                .isThrownBy(()->service.updateStatusValue(-1, Status.DISPUTED));
        verify(repository).findById(-1);
    }

}
