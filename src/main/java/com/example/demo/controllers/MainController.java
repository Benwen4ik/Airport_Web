package com.example.demo.controllers;

import com.example.demo.DAO.UserDAO;
import com.example.demo.model.User;
import com.example.demo.service.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping
public class MainController {

    private final UserDAO userDAO;

    @Autowired
    public MainController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @GetMapping
    public String redirectHome(){
        return "redirect: /home" ;
    }

    @GetMapping("/home") // показывает всех пользователей
    public String main_page(Authentication authentication, Model model) {
            User user = new User();
           if (authentication != null) { user = userDAO.findByLogin(authentication.getName());
            model.addAttribute("idu", user.getId_u()); }
        return "frontend/main_page";
    }

    @GetMapping("/info") // показывает всех пользователей
    public String info_page(Model model) {
//        model.addAttribute("users", userDAO.index());
        return "frontend/info_page";
    }

}
