package com.example.eurekaprovider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/helloWorld")
    public String helloWorld(@RequestParam String name){
        return "hello "+ name +"，this is first messge";
    }
}
