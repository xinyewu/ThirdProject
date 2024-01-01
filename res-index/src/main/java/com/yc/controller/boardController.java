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
    @GetMapping("/updateFood")
    public String gotoUpdateFood(){
        return "updatefood";
    }
    @GetMapping("/order")
    public String gotoOrder(){
        return "order";
    }
    @GetMapping("/upfood")
    public String gotoUpfood(){
        return "upfood";
    }
    @GetMapping("/user")
    public String gotoUser(){
        return "user";
    }
    @GetMapping("/report")
    public String gotoReport(){
        return "report";
    }
    @GetMapping("/admin")
    public String gotoAdmin(){
        return "admin";
    }
}
