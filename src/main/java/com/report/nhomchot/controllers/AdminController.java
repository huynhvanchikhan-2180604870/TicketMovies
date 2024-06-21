package com.report.nhomchot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/cinemas")
    public String cinemas() {
        return "admin/cinemas";
    }

    @GetMapping("/categories")
    public String categories() {
        return "admin/categories";
    }

    @GetMapping("/movies")
    public String movies() {
        return "admin/movies";
    }

    @GetMapping("/theaters")
    public String theaters() {
        return "admin/theaters";
    }

    @GetMapping("/show-times")
    public String showTimes() {
        return "admin/show-times";
    }
}
