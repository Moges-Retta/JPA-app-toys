package be.vdab.ToysForBoys.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderTest {
    private Order order;
    private Orderdetail orderdetail;

    @BeforeEach
    void beforeEach(){
        var date = LocalDate.of(2001,1,1);
        var country = new Country("Belgium",1);
        var customer = new Customer("test","test1","test",
                "test","3000",1,country);
        order= new Order(date, date,date,1,customer,Status.PROCESSING);
        orderdetail = new Orderdetail(1, BigDecimal.ONE);
    }
    @Test
    @DisplayName("order detail van order")
    void orderDetail(){
        assertThat(order.add(orderdetail)).isTrue();
    }
    @Test
    @DisplayName("null orderdetail toevoegen mislukt")
    void orderDetailNull(){
        assertThatNullPointerException().isThrownBy(() -> order.add(null));
    }
    @Test
    void updateShipped(){
        order.updateShipped(LocalDate.of(2002,1,1));
        assertThat(order.getShipped()).isEqualTo(LocalDate.of(2002,1,1));
    }
    @Test
    void updateStatus(){
        order.updateStatus(Status.DISPUTED);
        assertThat(order.getStatus()).isEqualByComparingTo(Status.DISPUTED);
    }
}
