package com.example.demo.DAO;

import com.example.demo.controllers.FlightController;
import com.example.demo.model.Flight;
import com.example.demo.model.Place;
import com.example.demo.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaceDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PlaceDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Flight> index() {
        return jdbcTemplate.query("SELECT * FROM flights;", new BeanPropertyRowMapper<>(Flight.class));
    }

    public List<Ticket> searchByIdUser(int idu) {
        return jdbcTemplate.query("SELECT * FROM ticket where id_u = ?;", new Object[]{idu}, new BeanPropertyRowMapper<>(Ticket.class));
    }

        public void save(int idu,int idf,String type, int count) {
        Place idp = jdbcTemplate.query("SELECT id_p FROM places where type_place =? and id_p =(Select id_p from flight_place where id_f = ?) ;",
                new Object[]{type,idf}, new BeanPropertyRowMapper<>(Place.class)) .stream().findAny().orElse(null);
        jdbcTemplate.update(" INSERT INTO public.ticket(id_u, id_p, count) VALUES (?, ?, ?);", idu,idp.getId_p(), count);
    }

    public void insertplace(int all,int free, String type) {
        jdbcTemplate.update( "INSERT INTO public.places( all_place, free_place, type_place) VALUES ( ?, ?, ?);",
              all,free,type );
    }

    public void insertPlaceFlight(Flight flight, int idpl) {
        jdbcTemplate.update( "INSERT INTO public.flight_place(id_f, id_p) VALUES (?, ?);",
                flight.getId_f(),idpl );
    }
}
