package com.example.checkspring.service;


import com.example.checkspring.dao.DiscountCard;

import java.util.List;

public interface CardService {
    List<DiscountCard> getAll();

    DiscountCard getById(Long id);

    DiscountCard getByName(Integer name);

    DiscountCard save(DiscountCard discountCard);

    void remove(Long id);

    Boolean existById(Long id);
}
