package com.example.checkspring.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Check {

    private final List<CheckLine> checkLineList;
    private DiscountCard discountCard;

    public Check(List<CheckLine> checkLineList, DiscountCard discountCard) {
        this.checkLineList = checkLineList;
        this.discountCard = discountCard;
    }

    public Check(List<CheckLine> checkLineList) {
        this.checkLineList = checkLineList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Check check = (Check) o;
        return Objects.equals(checkLineList, check.checkLineList) && Objects.equals(discountCard, check.discountCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkLineList, discountCard);
    }

    @Override
    public String toString() {
        return
                checkLineList + "";
    }
}
