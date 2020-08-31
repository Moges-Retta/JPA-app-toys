package be.vdab.ToysForBoys.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class Orderdetail {
    @NotEmpty
    private int ordered;
    @NotEmpty
    private BigDecimal priceEach;

    public Orderdetail(@NotEmpty int ordered, @NotEmpty BigDecimal priceEach) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Orderdetail)) return false;
        Orderdetail that = (Orderdetail) o;
        return ordered == that.ordered &&
                priceEach.equals(that.priceEach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ordered, priceEach);
    }
}
