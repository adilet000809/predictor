package com.example.predictor.controller

import com.example.predictor.entity.Users
import com.example.predictor.repositories.RoleRepository
import com.example.predictor.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class MainController {

    @Autowired
    val roleRepository: RoleRepository? = null

    @Autowired
    val userRepository: UserRepository? = null

    @Autowired
    val passwordEncoder: PasswordEncoder? = null

    @GetMapping(path = ["/"])
    fun index(): String{
        return "index"
    }

    @GetMapping(path = ["/login"])
    fun login(): String{
        return "login"
    }

    @GetMapping(path = ["/register"])
    fun register(): String{
        return "register"
    }

    @PostMapping(path = ["/signup"])
    fun signUp(
        @RequestParam(name = "first_name") firstName: String,
        @RequestParam(name = "last_name") lastName: String,
        @RequestParam(name = "email") email: String,
        @RequestParam(name = "password1") password1: String,
        @RequestParam(name = "password2") password2: String,
        redirectAttrs: RedirectAttributes
    ): String{
        if(password1 == password2){
            if (!userRepository!!.existsByEmail(email)){
                val userRole = roleRepository?.getOne(2)
                val user = Users(null, email, passwordEncoder!!.encode(password1), "$firstName $lastName", setOf(userRole))
                userRepository!!.save(user)
                redirectAttrs.addFlashAttribute("success", true)
            } else{
                redirectAttrs.addFlashAttribute("notUniqueEmail", true)
            }
        } else{
            redirectAttrs.addFlashAttribute("passwordNotMatch", true)
        }
        return "redirect:/register"
    }

}