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
    @BeforeEach
    void beforeEach(){
        var productline = new Productline("test","test",1);
        product = new Product("test","test","test",1,1,
                BigDecimal.ONE,1,productline);
        orderdetail = new Orderdetail(1,BigDecimal.ONE);
    }
    @Test
    @DisplayName("order detail van product")
    void orderDetail(){
        assertThat(product.add(orderdetail)).isTrue();
    }
    @Test
    @DisplayName("null orderdetail toevoegen mislukt")
    void orderDetailNull(){
        assertThatNullPointerException().isThrownBy(() -> product.add(null));
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
