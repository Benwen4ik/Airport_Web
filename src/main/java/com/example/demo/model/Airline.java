package com.example.demo.model;


import com.example.demo.DAO.AirlineDAO;
import com.example.demo.DAO.FlightDAO;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Airline {
    @NotEmpty
    private int id_a;

    private String name;

//    @NotEmpty
//    private  int rating;

}
