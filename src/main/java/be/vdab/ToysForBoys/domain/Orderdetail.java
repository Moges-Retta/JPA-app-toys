package be.vdab.ToysForBoys.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class Orderdetail {
    private int ordered;
    private BigDecimal priceEach;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "productId")
    private Product product;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

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
