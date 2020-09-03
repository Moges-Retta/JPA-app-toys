package be.vdab.ToysForBoys.domain;

import org.hibernate.mapping.Collection;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@NamedEntityGraph(name = "Product.MET_PRODUCTLINE",
        attributeNodes = @NamedAttributeNode("productline"))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String scale;
    private String description;
    private int inStock;
    private int inOrder;
    private BigDecimal price;
    @Version
    private int version;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "productlineId")
    private Productline productline;

    @ElementCollection
    @CollectionTable(name = "orderdetails",
            joinColumns = @JoinColumn(name = "productId"))
    private Set<Orderdetail> orderdetailSet;
    @ManyToMany(mappedBy = "products")
    private Set<Order> orders = new LinkedHashSet<>();

    protected Product(){}
    public Product(String name,  String scale, String description,  int inStock,
                    int inOrder,  BigDecimal price,  int version,
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
        if (orderdetail == null) {
            throw new NullPointerException();
        }
        return orderdetailSet.add(orderdetail);
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
    public boolean addMany(Order order) {
        var toegevoegd = orders.add(order);
        if ( ! order.getProducts().contains(this)) {
            order.addMany(this);
        }
        return toegevoegd;
    }

    public Set<Order> getOrders() {
        return Collections.unmodifiableSet(orders);
    }
}
