package com.example.demo.model;

import com.example.demo.DAO.PlaceDAO;
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
public class Ticket {

    @NotEmpty
    private int id_u ;

    @NotEmpty
    private  int id_p;

    @NotEmpty
    private  int number;

    @NotEmpty
    private int count;
}
