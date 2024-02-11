package com.example.demo.service;

import com.example.demo.DAO.UserDAO;
//import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
//public class UserService {
//
//    @Autowired
//    private UserDAO userDAO;
//
//    public void assignRoleToUser(String username, String roleName) {
//        User user = userDAO.findByLogin(username);
//
//        if (user == null) {
//            throw new IllegalArgumentException("User not found");
//        }
//
//        Role role = new Role();
//        role.setName(roleName);
//
//        user.getRoles().add(role);
//
////        userRepository.save(user);
//    }
//}