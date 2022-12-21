package com.example.checkspring.service;

import com.example.checkspring.dao.Product;
import com.example.checkspring.repository.RepositoryProduct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final RepositoryProduct repositoryProduct;


    @Override
    public List<Product> getAll() {
        return repositoryProduct.findAll();
    }

    @Override
    public Product getById(Long id) {
        Optional<Product> byId = repositoryProduct.findById(id);
        return byId.get();
    }

    @Override
    public Product save(Product product) {
        return repositoryProduct.save(product);
    }

    @Override
    public void remove(Long id) {
        repositoryProduct.deleteById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return repositoryProduct.existsById(id);
    }
}
