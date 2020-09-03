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
    private Product product;
    private Productline productline;
    private Product product1;
    private Order order1;
    private Orderdetail orderdetail1;

    @BeforeEach
    void beforeEach(){
        var date = LocalDate.of(2001,1,1);
        var country = new Country("Belgium",1);
        var customer = new Customer("test","test1","test",
                "test","3000",1,country);
        productline = new Productline("test","test",1);
        product = new Product("test","1","test",1,1,BigDecimal.ONE,1,productline);
        order= new Order(date, date,date,"test",1,customer,Status.PROCESSING);
        orderdetail = new Orderdetail(1, BigDecimal.ONE);
        product1 = new Product("test","1","test",1,1,BigDecimal.ONE,1,productline);
        order1= new Order(date, date,date,"test1",1,customer,Status.PROCESSING);
        orderdetail1 = new Orderdetail(1, BigDecimal.TEN);

    }
    @Test
    @DisplayName("meerdere order detail kunnen behoren tot een order")
    void orderDetail(){
        assertThat(order1.add(orderdetail1)).isTrue();
        assertThat(order1.add(orderdetail)).isTrue();
        assertThat(order1.getOrderdetailSet()).containsOnly(orderdetail1, orderdetail);
    }
    @Test
    @DisplayName("priceEach van  product")
    void prijsEach(){
        order.getOrderdetailSet().forEach(orderdetail2 -> assertThat(orderdetail2.getPriceEach())
                .isEqualByComparingTo(BigDecimal.ONE));
        order.add(orderdetail1);
        order.getOrderdetailSet().forEach(orderdetail2 ->
                assertThat(orderdetail2.getPriceEach().multiply(new BigDecimal(orderdetail2.getOrdered())))
                .isEqualByComparingTo(BigDecimal.TEN));
    }

    @Test
    @DisplayName("meerdere order kunnen behoren tot een product")
    void orderProduct(){
        assertThat(order1.addMany(product)).isTrue();
        assertThat(order1.addMany(product1)).isTrue();
        assertThat(order1.getProducts()).containsOnly(product, product1);
    }
    @Test
    @DisplayName("order  komt voor in product")
    void productOrder() {
        order.addMany(product);
        assertThat(order.getProducts()).contains(product);
        assertThat(product.getOrders()).contains(order);
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
