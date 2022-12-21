package com.example.checkspring.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckLine {
    private int countProduct;

    public Product product;

    private double commonPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckLine checkLine = (CheckLine) o;
        return countProduct == checkLine.countProduct && Double.compare(checkLine.commonPrice, commonPrice) == 0 && Objects.equals(product, checkLine.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countProduct, product, commonPrice);
    }

    @Override
    public String toString() {
        return countProduct +
                "   " + product +
                "  common: " + commonPrice + " BYN" + "\n";
    }
}

