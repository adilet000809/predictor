package com.example.predictor.controller

import com.example.predictor.entity.PasswordToken
import com.example.predictor.entity.Users
import com.example.predictor.repositories.PasswordTokenRepository
import com.example.predictor.repositories.RoleRepository
import com.example.predictor.repositories.UserRepository
import com.example.predictor.services.EmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
class MainController {

    @Autowired
    val roleRepository: RoleRepository? = null

    @Autowired
    val userRepository: UserRepository? = null

    @Autowired
    val passwordEncoder: PasswordEncoder? = null

    @Autowired
    val emailService: EmailService? = null

    @Autowired
    val passwordTokenRepository: PasswordTokenRepository? = null

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

    @GetMapping(path = ["/forgot-password"])
    fun forgot(): String{
        return "forgotPassword"
    }

    @PostMapping(path = ["/forgot"])
    fun forgotPassword(
            @RequestParam("email") email: String,
            redirectAttrs: RedirectAttributes,
            request: HttpServletRequest
    ): String{

        var user: Optional<Users>? = userRepository!!.findByEmail(email)

        if(!user!!.isPresent){
            redirectAttrs.addFlashAttribute("notFound", true)
        } else {
            var token: PasswordToken = PasswordToken()
            token.token = UUID.randomUUID().toString()
            token.setExpiration(1)
            token.user = user.get()
            passwordTokenRepository!!.save(token)

            var url: String = request.scheme + "://" + request.serverName + ":8004"

            var mail: SimpleMailMessage = SimpleMailMessage()
            mail.setFrom("sake.bolatbek@gmail.com");
            mail.setTo(user.get().email)
            mail.setSubject("Reset password")
            mail.setText(url + "/reset?token=" + token.token)
            emailService!!.sendEmail(mail)
            redirectAttrs.addFlashAttribute("success", true)

        }

        return "redirect:/forgot-password"
    }


    @GetMapping(path = ["/reset"])
    fun reset(
            @RequestParam(name = "token") passwordToken: String,
            model: Model
    ): String{

        val token: PasswordToken = passwordTokenRepository!!.findByToken(passwordToken)
        if (token.isExpired){
            model.addAttribute("expired", true)
        } else {
            model.addAttribute("expired", false)
            model.addAttribute("token", token.token)
        }

        return "resetPassword"

    }

    @PostMapping(path = ["/reset-password"])
    fun resetPassword(
            @RequestParam(name = "token") passwordToken: String,
            @RequestParam(name = "password1") password: String,
            redirectAttrs: RedirectAttributes
    ): String{
        val token: PasswordToken = passwordTokenRepository!!.findByToken(passwordToken)

        if(!token.isExpired){
            var user: Users = token.user
            user.password = passwordEncoder!!.encode(password)
            userRepository!!.save(user)
            passwordTokenRepository!!.delete(token)
        } else {
            redirectAttrs.addFlashAttribute("expired", true)
            return "redirect:/reset"
        }

        return "redirect:/login"

    }

}