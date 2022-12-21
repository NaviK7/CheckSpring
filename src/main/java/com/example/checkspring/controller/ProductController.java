package com.example.checkspring.controller;

import com.example.checkspring.dao.Product;
import com.example.checkspring.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    @GetMapping("/findAll")
    List<Product> findAll() {
        return productService.getAll();
    }

    @GetMapping("/findById/{id}")
    Product findById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PostMapping("/post")
    Product post(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/putById/{id}")
    ResponseEntity<Product> putById(@PathVariable Long id,
                                    @RequestBody Product client) {
        return (!productService.existById(id)
                ? new ResponseEntity<>(productService.save(client),
                HttpStatus.CREATED)
                : new ResponseEntity<>(productService.save(client),
                HttpStatus.OK));
    }

    @DeleteMapping("/delete")
    public void delete(Long id) {
        productService.remove(id);
    }
}
