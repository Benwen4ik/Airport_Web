package com.example.demo.controllers;

import com.example.demo.DAO.FlightDAO;
import com.example.demo.DAO.PlaceDAO;
import com.example.demo.DAO.UserDAO;
import com.example.demo.model.Ticket;
import com.example.demo.model.User;
import com.example.demo.service.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PlaceController {

    private final FlightDAO flightDAO;

    private  final PlaceDAO placeDAO;

    private  final UserDAO userDAO;

    int ID ;

    @Autowired
    public PlaceController(FlightDAO flightDAO, PlaceDAO placeDAO, UserDAO userDAO){
        this.placeDAO = placeDAO;
        this.flightDAO = flightDAO ;
        this.userDAO = userDAO;
    }

    @GetMapping("/ticket")
    public String ticketOrder(Model model) {
        model.addAttribute("flight",flightDAO.show(ID)) ;
        return "frontend/booking_flight" ;
    }

    @PostMapping("/ticket/{id}")
    public String ticketOrderPost(@AuthenticationPrincipal User user, @PathVariable("id") int idf,@Valid @RequestParam String type, @RequestParam int count ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails customUserDetails = (UserDetails) authentication.getPrincipal();
        User user1 = userDAO.findByLogin(customUserDetails.getUsername());
        placeDAO.save(user1.getId_u(),idf,type,count);
        return "redirect:/flight?sort=2";
    }

    @GetMapping("/booking")
    public String bookingNumber(Model model) {
//        model.addAttribute("flight", flightDAO.show(id));
        return "frontend/booking_flight_number";
    }

    @PostMapping("/booking")
    public String bookingNumberPost (@Valid @RequestParam int number) {
//        String id = Integer.toString(flightDAO.showNum(number).getId_f() );
//        String url = "redirect:/ticket{" + id + "}" ;
        ID = flightDAO.showNum(number).getId_f() ;
        if ( flightDAO.showNum(number) != null ) return "redirect:/ticket" ;
        return "redirect:/booking" ;
    }
}
