package ru.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/admin")
    public String getAdminPanel() {
        return "admin_panel";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login_form";
    }

    @GetMapping(value = "/user")
    public String getAuthUser() {
        return "user_panel";
    }
}
