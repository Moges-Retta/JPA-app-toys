package be.vdab.ToysForBoys.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "customers")
@NamedEntityGraph(name = "Customer.MET_COUNTRY",
        attributeNodes = @NamedAttributeNode("country"))
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String streetAndNumber;
    private String city;
    private String state;
    private String postalCode;
    private int version;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "countryId")
    private Country country;

    protected Customer(){}

    public Customer( String name,  String streetAndNumber,
                     String city, String state, String postalCode,
                     int version, Country country) {
        this.name = name;
        this.streetAndNumber = streetAndNumber;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.version = version;
        setCountry(country);
    }

    public String getName() {
        return name;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public int getVersion() {
        return version;
    }
    public void setCountry(Country country){
        this.country=country;
    }

    public Country getCountry() {
        return country;
    }

    public int getId() {
        return id;
    }
}
