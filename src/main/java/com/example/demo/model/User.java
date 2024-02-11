package com.example.demo.model;


import com.example.demo.DAO.UserDAO;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private int id_u;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should " +
            "be between 2 and 30 characters")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 30, message = "Surname " +
            "should be between 2 and 30 characters")
    private String surname;

    @NotEmpty
    @Size(min = 6, max = 20, message = "Login " +
            "should be between 6 and 20 characters")
    private String login;

    @NotEmpty
    @Size(min = 6, max = 20, message = "Password " +
            "should be between 6 and 20 characters")
    private String password;


    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "id_u"),
            inverseJoinColumns = @JoinColumn(name = "id_r"))
    private Collection<Role> roles  = new ArrayList<>();

}
