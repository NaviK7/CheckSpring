package com.example.checkspring.controller;

import com.example.checkspring.service.ServiceCheck;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/discount_card")
public class CheckController {

    private ServiceCheck serviceCheck;

    @PostMapping("/createCheck/{textLine}/{cardName}")
    public void createCheck(@PathVariable String textLine,
                            @PathVariable String cardName) throws IOException {
        serviceCheck.CheckRunner(textLine, cardName);
    }

}
