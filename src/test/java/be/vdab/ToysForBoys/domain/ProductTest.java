package be.vdab.ToysForBoys.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class ProductTest {
    private Product product;
    private Orderdetail orderdetail;
    private Customer customer;
    private Productline productline;
    private Order order;
    private Country country;
    private Product product1;
    private Order order1;
    private Orderdetail orderdetail1;
    @BeforeEach
    void beforeEach(){
        var date = LocalDate.of(2001,1,1);
        productline = new Productline("test","test",1);
        product = new Product("test","test","test",1,1,
                BigDecimal.ONE,1,productline);
        country=new Country("test",1);
        customer = new Customer("test","test","test","test","3000",1,country);
        order= new Order(date, date,date,"test",1,customer,Status.PROCESSING);
        orderdetail = new Orderdetail(1,BigDecimal.ONE);
        product1 = new Product("test","1","test",1,1,BigDecimal.ONE,1,productline);
        order1= new Order(date, date,date,"test1",1,customer,Status.PROCESSING);
        orderdetail1 = new Orderdetail(1, BigDecimal.TEN);
    }
    @Test
    @DisplayName("inStock van product is 1")
    void inStock(){
        assertThat(product.getInStock()).isEqualTo(1);
        product.updateInStock(2);
        assertThat(product.getInStock()).isEqualTo(2);
    }
    @Test
    @DisplayName("InOrder van product is 1")
    void inOrder(){
        assertThat(product.getInOrder()).isEqualTo(1);
        product.updateInOrder(product.getInOrder()+1);
        assertThat(product.getInOrder()).isEqualTo(2);
    }
    @Test
    @DisplayName("-1 value van InOrder mislukt")
    void nullInOrderMislukt(){
        assertThatIllegalArgumentException().isThrownBy(()->product.updateInOrder(-1));
    }
    @Test
    @DisplayName("-1 value van InStock mislukt")
    void nullInStockMislukt(){
        assertThatIllegalArgumentException().isThrownBy(()->product.updateInStock(-1));
    }
}
