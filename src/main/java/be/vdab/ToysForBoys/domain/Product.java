package be.vdab.ToysForBoys.domain;

import org.hibernate.mapping.Collection;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String scale;
    private String description;
    @NotEmpty
    private int inStock;
    @NotEmpty
    private int inOrder;
    @NotEmpty
    private BigDecimal price;
    @NotEmpty
    private int version;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "productlineId")
    private Productline productline;

    @ElementCollection
    @CollectionTable(name = "orderdetails",
            joinColumns = @JoinColumn(name = "productId"))
    private Set<Orderdetail> orderdetailSet;


    protected Product(){}

    public Product(@NotEmpty String name, @NotEmpty String scale, String description, @NotEmpty int inStock,
                   @NotEmpty int inOrder, @NotEmpty BigDecimal price, @NotEmpty int version,
                   Productline productline) {
        this.name = name;
        this.scale = scale;
        this.description = description;
        this.inStock = inStock;
        this.inOrder = inOrder;
        this.price = price;
        this.version = version;
        this.productline=productline;
        this.orderdetailSet=new LinkedHashSet<>();
    }
    public boolean add(Orderdetail orderdetail) {
        var toegevoegd = false;
        if (orderdetail != null) {
            toegevoegd = orderdetailSet.add(orderdetail);
        }
        return toegevoegd;
    }
    public String getName() {
        return name;
    }

    public String getScale() {
        return scale;
    }

    public String getDescription() {
        return description;
    }

    public int getInStock() {
        return inStock;
    }

    public int getInOrder() {
        return inOrder;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getVersion() {
        return version;
    }

    public Productline getProductline() {
        return productline;
    }

    public int getId() {
        return id;
    }

    public Set<Orderdetail> getOrderdetailSet() {
        return Collections.unmodifiableSet(orderdetailSet);
    }
    public void updateInOrder(int value){
        if (value < 0) {
            throw new IllegalArgumentException();
        }
        inOrder = value;
    }
    public void updateInStock(int value){
        if (value < 0) {
            throw new IllegalArgumentException();
        }
        inStock = value;
    }
}
