package com.example.checkspring.controller;

import com.example.checkspring.dao.DiscountCard;
import com.example.checkspring.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/discount")
public class DiscountCardController {
    private CardService cardService;

    @GetMapping("/findAll")
    List<DiscountCard> findAll() {
        return cardService.getAll();
    }

    @GetMapping("/findById/{id}")
    DiscountCard findById(@PathVariable Long id) {
        return cardService.getById(id);
    }

    @PostMapping("/post")
    DiscountCard post(@RequestBody DiscountCard discountCard) {
        return cardService.save(discountCard);
    }

    @PutMapping("/putById/{id}")
    ResponseEntity<DiscountCard> putById(@PathVariable Long id,
                                         @RequestBody DiscountCard discountCard) {
        return (!cardService.existById(id)
                ? new ResponseEntity<>(cardService.save(discountCard),
                HttpStatus.CREATED)
                : new ResponseEntity<>(cardService.save(discountCard),
                HttpStatus.OK));
    }

    @DeleteMapping("/delete")
    public void delete(Long id) {
        cardService.remove(id);
    }

    @GetMapping("/findIdByName/{name}")
    DiscountCard findIdByName(@PathVariable Integer name) {
        return cardService.getByName(name);
    }
}
