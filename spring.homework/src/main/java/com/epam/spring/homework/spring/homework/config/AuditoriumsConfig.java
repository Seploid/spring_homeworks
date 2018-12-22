package com.epam.spring.homework.spring.homework.config;

import com.epam.spring.homework.spring.homework.domain.Auditorium;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//todo read auditoriums from properties @PropertySource("redAuditorium.properties")
public class AuditoriumsConfig {

    @Bean(name = "redAuditorium")
    Auditorium red(){
        Auditorium red = new Auditorium();
        red.setName("Red Auditorium");
        red.setNumberOfSeats(50);
        return red;
    }

    @Bean(name = "blueAuditorium")
    Auditorium blue(){
        Auditorium blue = new Auditorium();
        blue.setName("Blue Auditorium");
        blue.setNumberOfSeats(50);
        return blue;
    }

    @Bean(name = "premierAuditorium")
    Auditorium premier(){
        Auditorium premier = new Auditorium();
        premier.setName("Premier Auditorium");
        premier.setNumberOfSeats(450);
        return premier;
    }

}
