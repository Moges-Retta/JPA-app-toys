package be.vdab.ToysForBoys.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class Orderdetail {
    private int ordered;
    private BigDecimal priceEach;

    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderId")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "productId")
    private Product product;*/

    public Orderdetail(int ordered,  BigDecimal priceEach) {
        this.ordered = ordered;
        this.priceEach = priceEach;
    }
    protected Orderdetail(){}

    public int getOrdered() {
        return ordered;
    }

    public BigDecimal getPriceEach() {
        return priceEach;
    }

   /* public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        if (!order.getOrderdetailSet().contains(this)) {
            order.add(this);
        }
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if (!product.getOrderdetailSet().contains(this)) {
            product.add(this);
        }
        this.product = product;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Orderdetail)) return false;
        Orderdetail that = (Orderdetail) o;
        return ordered==(that.ordered) &&
                priceEach.equals(that.priceEach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ordered, priceEach);
    }
}
