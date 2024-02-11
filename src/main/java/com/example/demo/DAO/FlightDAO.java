package com.example.demo.DAO;

import com.example.demo.controllers.FlightController;
import com.example.demo.controllers.PlaceController;
import com.example.demo.model.Airline;
import com.example.demo.model.Flight;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FlightDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FlightDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Flight> index() {
        return jdbcTemplate.query("SELECT * FROM flights;", new BeanPropertyRowMapper<>(Flight.class));
    }

    public List<Flight> sortTime() {
        return jdbcTemplate.query("SELECT * FROM flights order by time;", new BeanPropertyRowMapper<>(Flight.class));
    }

    public List<Flight> sortFromCity() {
        return jdbcTemplate.query("SELECT * FROM flights order by fromcity;", new BeanPropertyRowMapper<>(Flight.class));
    }

    public List<Flight> sortNumber() {
        return jdbcTemplate.query("SELECT * FROM flights order by number ;", new BeanPropertyRowMapper<>(Flight.class));
    }

    public List<Flight> search_flight(String fromcity, String tocity) {
        return jdbcTemplate.query("SELECT * FROM flights where fromcity= ? and tocity = ?;", new Object[]{fromcity,tocity}
                ,new BeanPropertyRowMapper<>(Flight.class));
    }

    public List<Flight> search_flight_time(String fromcity, String tocity, String date, String time) {
        return jdbcTemplate.query("SELECT * FROM flights where fromcity= ? and tocity = ? and time = ? and date = ?;", new Object[]{fromcity,tocity,time,date}
                ,new BeanPropertyRowMapper<>(Flight.class));
    }

    public List<Object> showList() {
        return jdbcTemplate.query("SELECT *FROM public.flights join public.flight_airline on flights.id_f = flight_airline.id_f join public.airlines on airlines.id_a  = flight_airline.id_a ;"
                ,new BeanPropertyRowMapper<>(Object.class));
    }


    public Flight show(int id) {
        return jdbcTemplate.query("SELECT * FROM flights WHERE id_f =?", new Object[]{id}, new BeanPropertyRowMapper<>(Flight.class))
                .stream().findAny().orElse(null);
    }

    public Flight showNum(int num) {
        return jdbcTemplate.query("SELECT * FROM flights WHERE number =?", new Object[]{num}, new BeanPropertyRowMapper<>(Flight.class))
                .stream().findAny().orElse(null);
    }

    public void save(Flight flight) {
        jdbcTemplate.update(" INSERT INTO public.flights(tocity, fromcity,number, date , time, cost, status, hall, type) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                 flight.getTocity(), flight.getFromcity(), flight.getNumber(),
                flight.getDate(), flight.getTime(), flight.getCost(),flight.getStatus(), flight.getHall(), flight.getType());
    }

    public void update(int id, Flight updatedflight) {
        jdbcTemplate.update("UPDATE Flights SET tocity=?,fromcity=?, number=?, date=?, time=?, cost=?, status=?, hall=?, type=? WHERE id_f = ?",
                updatedflight.getTocity(),updatedflight.getFromcity(), updatedflight.getNumber(),
                updatedflight.getDate(), updatedflight.getTime(), updatedflight.getCost(),updatedflight.getStatus(),
                updatedflight.getHall(), updatedflight.getType(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM flights WHERE id_f = ?", id);
    }
}

