package com.example.predictor.controller;

import com.example.predictor.entity.Users;
import com.example.predictor.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "prof/")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "")
    private String profile(){
        return "profile";
    }

    @PostMapping(value = "changePassword")
    private String changePassword(
            @RequestParam(name = "oldPassword") String oldPassword,
            @RequestParam(name = "password1") String password1,
            @RequestParam(name = "password2") String password2,
            RedirectAttributes redirectAttributes
    ){

        Users user = getUserData();
        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            if(password1.equals(password2)){
                user.setPassword(passwordEncoder.encode(password1));
                userRepository.save(user);
                redirectAttributes.addFlashAttribute("changeSuccess", true);
            } else {
                redirectAttributes.addFlashAttribute("passwordNotMatch", true);
            }
        } else {
            redirectAttributes.addFlashAttribute("incorrectPassword", true);
        }

        return "redirect:/prof/";

    }

    public Users getUserData(){
        Users userData = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User)authentication.getPrincipal();
            userData = userRepository.findByEmail(secUser.getUsername()).get();
        }
        return userData;
    }

}
