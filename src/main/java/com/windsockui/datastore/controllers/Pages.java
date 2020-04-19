package com.windsockui.datastore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Pages {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
