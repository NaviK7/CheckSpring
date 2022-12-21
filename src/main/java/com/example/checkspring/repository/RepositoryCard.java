package com.example.checkspring.repository;


import com.example.checkspring.dao.DiscountCard;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryCard extends JpaRepository<DiscountCard, Long> {
    @Query(value = "SELECT name FROM discount_card  WHERE name=name")
    DiscountCard findIdByName(Integer name);

}
