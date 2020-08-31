package be.vdab.ToysForBoys.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String streetAndNumber;
    @NotEmpty
    private String city;
    private String state;
    @Min(1000)
    @Max(99999)
    private String postalCode;
    @NotEmpty
    private int version;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "countryId")
    private Country country;

    protected Customer(){}

    public Customer(@NotEmpty String name, @NotEmpty String streetAndNumber,
                    @NotEmpty String city, String state, String postalCode,
                    @NotEmpty int version, Country country) {
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
