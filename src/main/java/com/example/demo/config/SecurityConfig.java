package com.example.demo.config;

import com.example.demo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import java.security.cert.Extension;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService ;

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // InMemoryUserDetailsManager (see below)
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("userPass"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("adminPass"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean // кодер пароля
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // http builder configurations for authorize requests and form login (see below)
        http  // .csrf(Customizer.withDefaults())
//                .disable()
                .authorizeHttpRequests( authorize -> authorize
                .requestMatchers("resources/static/**", "/static/css/header_style.css","/user/registration", "/user/reg" ,
                        "/resources/**","/home","/search","/flight" , "/", "/css/**", "*/.css", "/*.js", "*/.jpg").permitAll()
                .requestMatchers("/user/login*").permitAll().anyRequest().authenticated()
                )
                .formLogin( form -> form
                .loginPage("/user/login") // польз страница входа в систему
//                .loginProcessingUrl("/user/log") // юрл для отправки имени польз и пароля
                .defaultSuccessUrl("/home", true) // страница после успешного входа
                .failureUrl("/user/login?error=true") // страница для неудачного входа
//                .failureHandler(authenticationFailureHandler())
                )
                .logout(logout -> logout
                                .logoutUrl("/user/logout") // польз выход из системы
                                .logoutSuccessUrl("/home")
//                                .invalidateHttpSession(true)
//                                .deleteCookies("JSESSIONID")
                                .permitAll()
                )
                .httpBasic(Customizer.withDefaults()); // базовая аутентификация
        return http.build();
    }

//    @Bean
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        return new CustomAuthenticationFailureHandler();
//    }
}
