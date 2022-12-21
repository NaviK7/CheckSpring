package com.example.checkspring.service;


import com.example.checkspring.dao.DiscountCard;
import com.example.checkspring.repository.RepositoryCard;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CardServiceImpl implements CardService {
    private final RepositoryCard repositoryCard;


    @Override
    public List<DiscountCard> getAll() {
        return repositoryCard.findAll();
    }

    @Override
    public DiscountCard getById(Long id) {
        Optional<DiscountCard> byId = repositoryCard.findById(id);
        return byId.get();
    }

    @Override
    public DiscountCard getByName(Integer name) {
        return repositoryCard.findIdByName(name);
    }

    @Override
    public DiscountCard save(DiscountCard discountCard) {
        return repositoryCard.save(discountCard);
    }

    @Override
    public void remove(Long id) {
        repositoryCard.deleteById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return repositoryCard.existsById(id);
    }
}