package com.diaries.wish.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishController {
    @GetMapping
    public String sayHello() {
        return "Hello World";
    }
}
