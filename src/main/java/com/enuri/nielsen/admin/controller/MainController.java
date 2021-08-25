package com.enuri.nielsen.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String indexView() {
        return "index";
    }

    @GetMapping("/admin/main")
    public String adminMainView() {
        return "admin/main";
    }

}
