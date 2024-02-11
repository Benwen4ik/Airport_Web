package com.example.demo;

import com.example.demo.DAO.AirlineDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class CourseProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseProjectApplication.class, args);

	}

}
