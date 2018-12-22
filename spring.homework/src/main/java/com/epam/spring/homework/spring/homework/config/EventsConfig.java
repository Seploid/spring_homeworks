package com.epam.spring.homework.spring.homework.config;

import com.epam.spring.homework.spring.homework.domain.Auditorium;
import com.epam.spring.homework.spring.homework.domain.Event;
import com.epam.spring.homework.spring.homework.domain.EventRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Configuration
@Import(AuditoriumsConfig.class)
public class EventsConfig {

    @Autowired()
    @Qualifier("redAuditorium")
    Auditorium red;

    @Autowired
    @Qualifier("blueAuditorium")
    Auditorium blue;

    @Autowired
    @Qualifier("premierAuditorium")
    Auditorium premier;

    @Bean
    List<Event> events(){
        List<Event> events = new ArrayList<>();

        Event event = new Event();
        event.setName("Aquaman");
        event.setBasePrice(5);
        event.setRating(EventRating.HIGH);
        NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();
        auditoriums.put(LocalDateTime.of(2018, Month.DECEMBER, 15, 10, 0), red);
        auditoriums.put(LocalDateTime.of(2018, Month.DECEMBER, 15, 19, 0), premier);
        auditoriums.put(LocalDateTime.of(2018, Month.DECEMBER, 16, 10, 0), red);
        auditoriums.put(LocalDateTime.of(2018, Month.DECEMBER, 16, 19, 0), premier);
        auditoriums.put(LocalDateTime.of(2018, Month.DECEMBER, 17, 10, 0), red);
        auditoriums.put(LocalDateTime.of(2018, Month.DECEMBER, 17, 19, 0), premier);
        event.setAuditoriums(auditoriums);
        event.setAirDates(auditoriums.keySet());
        events.add(event);

        event = new Event();
        event.setName("Spider-Man: Into the Spider-Verse");
        event.setBasePrice(7);
        event.setRating(EventRating.LOW);
        auditoriums = new TreeMap<>();
        auditoriums.put(LocalDateTime.of(2018, Month.DECEMBER, 15, 11, 0), premier);
        auditoriums.put(LocalDateTime.of(2018, Month.DECEMBER, 15, 20, 0), blue);
        auditoriums.put(LocalDateTime.of(2018, Month.DECEMBER, 16, 11, 0), premier);
        auditoriums.put(LocalDateTime.of(2018, Month.DECEMBER, 16, 20, 0), blue);
        auditoriums.put(LocalDateTime.of(2018, Month.DECEMBER, 17, 11, 0), premier);
        auditoriums.put(LocalDateTime.of(2018, Month.DECEMBER, 17, 20, 0), blue);
        event.setAuditoriums(auditoriums);
        event.setAirDates(auditoriums.keySet());
        events.add(event);

        return events;
    }
}
