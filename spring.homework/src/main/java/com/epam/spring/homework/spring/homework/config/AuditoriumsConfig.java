package com.epam.spring.homework.spring.homework.config;

import com.epam.spring.homework.spring.homework.domain.Auditorium;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@PropertySources({@PropertySource("redAuditorium.properties")
        , @PropertySource("blueAuditorium.properties")
        , @PropertySource("premierAuditorium.properties")})
public class AuditoriumsConfig {

    @Value("${redAuditoium.name}")
    String redAuditoriumName;

    @Value("${redAuditoium.number.of.seats}")
    long redAuditoiumNumberOfSeats;

    @Bean(name = "redAuditorium")
    @Scope("prototype")
    Auditorium red(){
        Auditorium red = new Auditorium();
        red.setName(redAuditoriumName);
        red.setNumberOfSeats(redAuditoiumNumberOfSeats);
        return red;
    }

    @Value("${blueAuditorium.name}")
    String blueAuditoriumName;

    @Value("${blueAuditorium.number.of.seats}")
    long blueAuditoriumNumberOfSeats;

    @Bean(name = "blueAuditorium")
    @Scope("prototype")
    Auditorium blue(){
        Auditorium blue = new Auditorium();
        blue.setName(blueAuditoriumName);
        blue.setNumberOfSeats(blueAuditoriumNumberOfSeats);
        return blue;
    }

    @Value("${premierAuditorium.name}")
    String premierAuditoriumName;

    @Value("${premierAuditorium.number.of.seats}")
    long premierAuditoriumNumberOfSeats;

    @Bean(name = "premierAuditorium")
    @Scope("prototype")
    Auditorium premier(){
        Auditorium premier = new Auditorium();
        premier.setName(premierAuditoriumName);
        premier.setNumberOfSeats(premierAuditoriumNumberOfSeats);
        return premier;
    }

}
