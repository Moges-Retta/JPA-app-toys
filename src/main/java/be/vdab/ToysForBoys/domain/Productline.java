package be.vdab.ToysForBoys.domain;
import javax.validation.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "productlines")
public class Productline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String name;
    private String description;
    @NotEmpty
    private int version;
    protected Productline(){}

    public Productline(String name, String description, int version) {
        this.name = name;
        this.description = description;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getVersion() {
        return version;
    }

    public int getId() {
        return id;
    }
}
