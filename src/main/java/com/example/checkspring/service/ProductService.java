package com.example.checkspring.service;


import com.example.checkspring.dao.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product getById(Long id);

    abstract Product save(Product entity);

    void remove(Long id);

    Boolean existById(Long id);
}
