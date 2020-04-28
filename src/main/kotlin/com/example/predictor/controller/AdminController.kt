package com.example.predictor.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(value = ["admin/"])
class AdminController {

    @GetMapping(value = ["category"])
    fun getCategory(): String{
        return "admin/category";
    }

    @GetMapping(value = ["tournament"])
    fun getTournament(): String{
        return "admin/tournament";
    }

    @GetMapping(value = ["event"])
    fun getEvent(): String{
        return "admin/event";
    }

}