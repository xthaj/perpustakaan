package com.uas.pbo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class UserController {
    @GetMapping("/user/index")
    public String showIndexUser(Model model) {
        return "/user/index";
    }

}
