package com.example.checkspring.dao;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Boolean discountStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(discountStatus, product.discountStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, discountStatus);
    }

    @Override
    public String toString() {
        return " " + name + "  - " + price + " BYN" + " stock: " + discountStatus;
    }
}