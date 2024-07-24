package com.hb.system.ecommerce.shoes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "layout";
    }
}
