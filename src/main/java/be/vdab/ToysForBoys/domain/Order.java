package be.vdab.ToysForBoys.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@NamedEntityGraph(name = "Order.MET_CUSTOMER",
        attributeNodes = @NamedAttributeNode("customer"))
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate ordered;
    private LocalDate required;
    private LocalDate shipped;
    private String comments;
    @Version
    private int version;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection
    @CollectionTable(name = "orderdetails",
            joinColumns = @JoinColumn(name = "orderId"))
    private Set<Orderdetail> orderdetailSet;
    @ManyToMany
    @JoinTable(name = "orderdetails",
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "productId"))
    private Set<Product> products = new LinkedHashSet<>();
    protected Order(){}

    public Order(LocalDate ordered,  LocalDate required,
                  LocalDate shipped,  String comments, int version,
                 Customer customer, Status status) {
        this.ordered = ordered;
        this.required = required;
        this.shipped = shipped;
        this.comments=comments;
        this.version = version;
        this.customer=customer;
        this.status=status;
        this.orderdetailSet = new LinkedHashSet<>();
    }
    public boolean add(Orderdetail orderdetail) {
        if(orderdetail == null){
            throw new NullPointerException();
        }
        return  orderdetailSet.add(orderdetail);
    }
    public LocalDate getOrdered() {
        return ordered;
    }

    public LocalDate getRequired() {
        return required;
    }

    public LocalDate getShipped() {
        return shipped;
    }

    public int getVersion() {
        return version;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getId() {
        return id;
    }

    public Set<Orderdetail> getOrderdetailSet() {
        return Collections.unmodifiableSet(orderdetailSet);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setShipped(LocalDate shipped) {
        this.shipped = shipped;
    }
    public void updateShipped(LocalDate shippedValue){
        if(shippedValue==null){
            throw new IllegalArgumentException();
        }
        shipped=shippedValue;
    }
    public void updateStatus(Status statusValue){
        if(status==null){
            throw new IllegalArgumentException();
        }
        status=statusValue;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

   /* public BigDecimal totalValue(){
        var totalPrijs = BigDecimal.ZERO;
        orderdetailSet.forEach(orderdetail -> {
            totalPrijs.add(orderdetail.getPriceEach()
                    .multiply(orderdetail.getOrdered()));
        });
        return totalPrijs;
    }*/
    public boolean addMany(Product product) {
        var toegevoegd = products.add(product);
        if ( ! product.getOrders().contains(this)) {
            product.addMany(this);
        }
        return toegevoegd;
    }

    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
    }

}
