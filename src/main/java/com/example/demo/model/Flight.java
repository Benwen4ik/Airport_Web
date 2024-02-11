package com.example.demo.model;

import com.example.demo.DAO.FlightDAO;
import com.example.demo.controllers.UserController;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight {

    private int id_f;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 10, message = "Name should be between 2 and 30 characters")
    private int number;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 30, message = "Name city should be between 2 and 30 characters")
    private String fromcity;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 30, message = "Name city should be between 2 and 30 characters")
    private String tocity;

    @NotEmpty
    private String time ;


    @NotEmpty
  //  @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME)
    private String date;

    @NotEmpty
    private String type;

    @NotEmpty
    private int cost;

    @NotEmpty
    private String status;

    @NotEmpty
    private int hall;
}
