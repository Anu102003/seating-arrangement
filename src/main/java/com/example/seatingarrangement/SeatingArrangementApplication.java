package com.example.seatingarrangement;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication()
@EnableJpaAuditing
@EnableWebSecurity
public class SeatingArrangementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeatingArrangementApplication.class, args);
    }


}