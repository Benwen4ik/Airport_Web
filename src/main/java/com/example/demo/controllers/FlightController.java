package com.example.demo.controllers;

import com.example.demo.DAO.AirlineDAO;
import com.example.demo.DAO.FlightDAO;
import com.example.demo.DAO.PlaceDAO;
import com.example.demo.model.Flight;
import com.example.demo.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/flight")
public class FlightController
{
    private final FlightDAO flightDAO;
    private final AirlineDAO airlineDAO;

    private final PlaceDAO placedao;

    @Autowired
    public FlightController(FlightDAO flightDAO, AirlineDAO airlineDAO, PlaceDAO placedao) {
        this.flightDAO = flightDAO;
        this.airlineDAO = airlineDAO;
        this.placedao = placedao;
    }


    @GetMapping // показывает всех рейсы
    public String index(Model model, @RequestParam("sort") int sort) {
        if (sort == 2) {
            model.addAttribute("flights", flightDAO.sortFromCity());
        }
        if (sort == 1) {
            model.addAttribute("flights", flightDAO.sortNumber());
        }
        if (sort == 3) {
            model.addAttribute("flights", flightDAO.sortTime());
        }
        model.addAttribute("airlinesdao", airlineDAO);
//        model.addAttribute("planes", planedao.getListName());
        return "frontend/show_flight";
    }

    @GetMapping("/new")
    public String newPerson() {
        return "frontend/new_flight";
    }

    @PostMapping  ("/data")// получение данных о регистрации
    public String create(@Valid @RequestParam String fromcity, @RequestParam int number, @RequestParam String date, @RequestParam String time,
                         @RequestParam int cost, @RequestParam String status, @RequestParam int hall, @RequestParam String tocity,
                         @RequestParam String type) {
//        if (bindingResult.hasErrors())
//            return "login/registr";
        Flight flight = Flight.builder()
                .type(type)
                .tocity(tocity)
                .time(time)
                .number(number)
                .status(status)
                .hall(hall)
                .fromcity(fromcity)
                .date(date)
                .cost(cost)
                .build();
        flightDAO.save(flight);
        airlineDAO.insertAirlineFlight(flightDAO.showNum(number),1);
       // placedao.insertplace(30,25,"window");
        placedao.insertPlaceFlight(flightDAO.showNum(number),1);
        return "redirect:/flight?sort=2";
    }

    @GetMapping("/search")
    public String searchFlight(Model model,@ModelAttribute (name = "races") ArrayList<Flight> races) {
        model.addAttribute("flights",flightDAO.index());
//        Flight flight = new Flight();
//       model.addAttribute("data", flight);
        model.addAttribute("airlinesdao", airlineDAO);
        model.addAttribute("races", races);
        return "frontend/search_flight_result";
    }

    @PostMapping
    public String searchFlightPost(Model model, @Valid @RequestParam(defaultValue = "Minsk") String fromcity,
                                   @RequestParam(defaultValue = "Moskva") String  tocity,
                                   @RequestParam String date, @RequestParam String time,RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors())
//            return "login/registr";
         if(date == "" || time == "")   redirectAttributes.addFlashAttribute("races",flightDAO.search_flight(fromcity, tocity));
       else  redirectAttributes.addFlashAttribute("races",flightDAO.search_flight_time(fromcity, tocity,date,time));
        return "redirect:/flight/search" ;
    }



    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("flight", flightDAO.show(id));
        return "frontend/edit_flight";
    }

    @PostMapping("/{id}")
    public String update(@Valid @RequestParam String fromcity, @RequestParam int number, @RequestParam String date, @RequestParam String time,
                         @RequestParam int cost, @RequestParam String status, @RequestParam int hall, @RequestParam String tocity,
                         @RequestParam String type
                         , @PathVariable("id") int id) {
//        if (bindingResult.hasErrors())
//            return "people/edit";
       Flight flight = Flight.builder()
               .number(number)
               .cost(cost)
               .date(date)
               .fromcity(fromcity)
               .hall(hall)
               .status(status)
               .time(time)
               .tocity(tocity)
               .type(type)
               .build();
        flightDAO.update(id, flight);
        return "redirect:/flight?sort=2";
    }



    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        flightDAO.delete(id);
        return "redirect:/flight?sort=2";
    }

}
