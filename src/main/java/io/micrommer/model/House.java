package io.micrommer.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
public class House extends PanacheEntityBase {
    public enum Status {
        AVAILABLE, UNAVAILABLE, SOLD
    }
    @Id
    public BigInteger id;
    @ManyToOne
    public Client owner;
    public String address;
    public Integer area;
    public Integer price;
    @Enumerated(EnumType.STRING)
    public Status status;
}
