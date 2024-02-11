package com.example.demo.controllers;

import com.example.demo.DAO.FlightDAO;
import com.example.demo.DAO.PlaceDAO;
import com.example.demo.DAO.RoleDAO;
import com.example.demo.DAO.UserDAO;
//import com.example.demo.RoleConverter;
//import com.example.demo.model.Role;
import com.example.demo.model.Role;
import com.example.demo.model.User;
//import com.example.demo.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserDAO userDAO;
    private  final RoleDAO roleDAO;

    private final PlaceDAO placeDAO;
    private final FlightDAO flightDAO;

//    PasswordEncoder passwordEncoder ;

//    private final RoleService roleService;

    @Autowired
    public UserController(UserDAO userDAO,  RoleDAO roleDAO, PlaceDAO placeDAO, FlightDAO flightDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.placeDAO = placeDAO;
        this.flightDAO = flightDAO;
//        this.roleService = roleService ;
    }

    @GetMapping("/list") // показывает всех пользователей
    public String index(Model model) {
        model.addAttribute("users", userDAO.index());
        return "frontend/test";
    }

    @GetMapping("/login")  // вход в акк
    public String login(Model model, @RequestParam("messageKey" ) final Optional<String> messageKey, @RequestParam("error" ) final Optional<String> error) {
        model.addAttribute("users", userDAO.index());
        return "frontend/login_users";
    }

//    @PostMapping("/log")
//    public String home(Authentication authentication) {
//        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//            return "redirect:/home"; // Перенаправить на страницу администратора
//        } else if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
//            return "redirect:/home"; // Перенаправить на страницу пользователя
//        } else {
//            return "redirect:/user/login"; // Перенаправить на страницу входа
//        }
//    }

    @GetMapping("/{id}") // личный акк юзера
    public String show(Authentication  authentication,@PathVariable("id") int id, Model model) {
//        User user = userDAO.findByLogin(authentication.getName()) ;
        model.addAttribute("aut", id) ;
        model.addAttribute("user", userDAO.show(id));
        model.addAttribute("tickets",placeDAO.searchByIdUser(id));
        model.addAttribute("flightdao",flightDAO);
        return "frontend/my_account";
    }

    @GetMapping("/registration") // регистрация пользователя
    public String newPerson(Model model) {
        User user = new User();
        model.addAttribute("user",user);
        return "frontend/registration";
    }

    @PostMapping ("/reg") // получение данных о регистрации
    public String create(@Valid @RequestParam String login,
                         @RequestParam String password,@RequestParam String name,
            @RequestParam String surname, @RequestParam String email) {
//        if (bindingResult.hasErrors())
//            return "login/registr";
       // RoleConverter roleConverter = new RoleConverter();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        Collection<Role> role = roleDAO.findRoleByName("ROLE_ADMIN");
//        Role role1 = roleDAO.findRole("ROLE_ADMIN");
        User user = User.builder()
                .login(login)
                .password(encodedPassword)
                .name(name)
                .surname(surname)
                .email(email)
                .roles(role)
                .build();
        userDAO.save(user);
        userDAO.assignRole(user.getLogin(), 1);
        return "redirect:/user/list";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userDAO.show(id));
        return "frontend/edit_user";
    }


    @PostMapping("/{id}")
    public String update(@Valid @RequestParam String login,
                         @RequestParam String password, @RequestParam String name, @RequestParam String surname,
                         @RequestParam String email, @PathVariable("id") int id) {
//        if (bindingResult.hasErrors())
//            return "people/edit";
        User user = User.builder()
                .login(login)
                .password(password)
                .name(name)
                .surname(surname)
                .email(email)
                .build();
        userDAO.update(id, user);
        return "redirect:/user/{id}";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userDAO.delete(id);
        return "redirect:/user";
    }


    public Role getRoleFromCollection(Collection<Role> roles, String roleName) {
        for (Role role : roles) {
            if (role.getName().equals(roleName)) {
                return role;
            }
        }
        return null;
    }

//    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

//    @GetMapping("/logout")
//    public String logout(){
//        return "/frontend/logout";
//    }
//
//    @PostMapping("/logout") // выход из системы
//    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
//        // .. perform logout
//        this.logoutHandler.logout(request, response, authentication);//Это позволит удалять SecurityContextHolderStrategy и SecurityContextRepository по мере необходимости.
//        return "redirect:/home";
//    }
}
