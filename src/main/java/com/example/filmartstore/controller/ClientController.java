package com.example.filmartstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/filmart/")
public class ClientController {

    @GetMapping(value = "home")
    public String indexClient() {
        return "client/List";
    }
}
