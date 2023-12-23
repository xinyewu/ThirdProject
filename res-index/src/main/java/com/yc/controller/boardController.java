package com.yc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class boardController {
    @GetMapping("/adminBoard")
    public String gotoBoard(){
        return "adminBoard";
    }
    @GetMapping("/newfood")
    public String gotoNewFood(){
        return "newfood";
    }
}
