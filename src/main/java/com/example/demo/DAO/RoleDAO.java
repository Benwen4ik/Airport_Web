package com.example.demo.DAO;


//import com.example.demo.model.Role;
import com.example.demo.controllers.UserController;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.List;

@Controller
public class RoleDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Role> findRoleByName(String name) {
        return jdbcTemplate.query("SELECT * FROM roles WHERE name=? ", new Object[]{name}, new BeanPropertyRowMapper<>(Role.class));
//                .stream().findAny().orElse(null);
    }

    public Role findRole(String name) {
        return jdbcTemplate.query("SELECT * FROM roles WHERE name=? ", new Object[]{name}, new BeanPropertyRowMapper<>(Role.class))
                .stream().findAny().orElse(null);
    }
}
