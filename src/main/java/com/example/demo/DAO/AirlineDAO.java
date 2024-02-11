package com.example.demo.DAO;

import com.example.demo.controllers.FlightController;
import com.example.demo.model.Airline;
import com.example.demo.model.Flight;
import org.attoparser.dom.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AirlineDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AirlineDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Airline> getListName() {
        return jdbcTemplate.query("SELECT name FROM airlines;", new BeanPropertyRowMapper<>(Airline.class));
    }

    public List<Airline> IdByFlight (int idf){
        return jdbcTemplate.query("Select * from airlines where id_a = ( SELECT id_a FROM flight_airline where id_f=? );", new Object[]{idf}, new BeanPropertyRowMapper<>(Airline.class));
    }

    public Airline  showById(int idf){
        return jdbcTemplate.query("Select name from airlines where id_a = ( SELECT id_a FROM flight_airline where id_f=? );", new Object[]{idf}, new BeanPropertyRowMapper<>(Airline.class))
                .stream().findAny().orElse(null);
    }

    public void insertAirline(String name, int rate) {
        jdbcTemplate.update( "INSERT INTO public.airlines( name, rating) VALUES ( ?, ?);",
                 name,rate);
    }

    public void insertAirlineFlight(Flight flight, int ida) {
        jdbcTemplate.update( "INSERT INTO flight_airline (id_f, id_a) VALUES (?, ?);",
                flight.getId_f(),ida);
    }

}
