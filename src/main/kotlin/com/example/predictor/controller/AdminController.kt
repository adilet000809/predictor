package com.example.predictor.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(value = ["admin/"])
class AdminController {

    @GetMapping(value = [""])
    fun admin(): String{
        return "admin/admin";
    }

    @GetMapping(value = ["category"])
    fun getCategory(): String{
        return "admin/admin";
    }

}