package com.example.demo.DAO;

import com.example.demo.controllers.UserController;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Component
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> index() {
        return jdbcTemplate.query("SELECT * FROM users;", new BeanPropertyRowMapper<>(User.class));
    }

//    public boolean  CheckUser(String login, String pass){
//        User user = jdbcTemplate.queryForObject("SELECT * FROM users where id_u = ?;", new Object[]{userid},new BeanPropertyRowMapper<>(User.class));
//        if ( user.)
//    }

    public User show(int id) {
        return jdbcTemplate.query("SELECT * FROM USERS WHERE id_u =?", new Object[]{id}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    public User findByLogin(String login) {
        return jdbcTemplate.query("SELECT * FROM USERS WHERE login =?", new Object[]{login}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    public void save(User user) {
        jdbcTemplate.update("INSERT INTO users(name,surname,login,password, email) VALUES( ?, ?, ?, ?, ?)", user.getName(), user.getSurname(), user.getLogin(),
            user.getPassword(),  user.getEmail()) ; // , user.getRoles());
    }

    public void assignRole(String name, int idr) {
        jdbcTemplate.update("INSERT INTO user_role (id_u, id_r) VALUES( ?, ?)", findByLogin(name).getId_u(), idr) ; // , user.getRoles());
    }


    public void update(int id, User updatedUser) {
        jdbcTemplate.update("UPDATE Users SET name=?, surname=?, email=?, login= ?, password = ? WHERE id_u=?", updatedUser.getName(),
                updatedUser.getSurname(), updatedUser.getEmail(),updatedUser.getLogin(), updatedUser.getPassword(), id);
    }



    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM users WHERE id_u=?", id);
    }

}
