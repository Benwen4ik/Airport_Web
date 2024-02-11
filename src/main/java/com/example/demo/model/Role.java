package com.example.demo.model;

import com.example.demo.DAO.RoleDAO;
import com.example.demo.DAO.UserDAO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collection;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

//    @ManyToMany(mappedBy = "roles")
//    private Collection<User> users;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

}
