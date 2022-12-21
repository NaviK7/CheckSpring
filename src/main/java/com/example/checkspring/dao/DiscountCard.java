package com.example.checkspring.dao;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "discount_card")
public class DiscountCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer name;
    private Integer discountPercentage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard that = (DiscountCard) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(discountPercentage, that.discountPercentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, discountPercentage);
    }

    @Override
    public String toString() {
        return "discountCard{" +
                "number= " + name +
                ", discount % =" + discountPercentage +
                '}';
    }

}
