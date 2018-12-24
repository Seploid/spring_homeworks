package com.epam.spring.homework.spring.homework.services;

import com.epam.spring.homework.spring.homework.domain.Auditorium;
import com.epam.spring.homework.spring.homework.domain.Event;
import com.epam.spring.homework.spring.homework.domain.EventRating;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Component
public class EventService implements IEventService {


    private Event selectedEvent;
    private LocalDateTime selectedAirDate;
    private Set<Long> selectedSeats;

    @Autowired
    JdbcTemplate jt;

    @Autowired
    AuditoriumService auditoriumService;

    private RowMapper<Event> eventRowMapper = new RowMapper<Event>() {
        @org.springframework.lang.Nullable
        @Override
        public Event mapRow(ResultSet resultSet, int i) throws SQLException {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Long basePrice = resultSet.getLong("basePrice");
            String raiting = resultSet.getString("rating");
            Event event = new Event();
            event.setName(name);
            event.setBasePrice(basePrice);
            event.setRating(EventRating.valueOf(raiting));
            event.setId(id);
            return event;
        }
    };

    @PostConstruct
    private void init(){
        //Events table
        jt.execute("CREATE TABLE events (id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1), name varchar(255), basePrice int, rating varchar(6))");
        jt.execute("INSERT INTO events (name, basePrice, rating) values ('Aquaman', 5, 'HIGH')");
        jt.execute("INSERT INTO events (name, basePrice, rating) values ('Spider-Man', 7, 'LOW')");

        //AirDates table
        jt.execute("CREATE TABLE airdates (id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1), event_id int, auditorium_id int, dateTime varchar(255))");
        jt.execute("INSERT INTO airdates (event_id, auditorium_id, dateTime) values (1, 1, '2018-12-15 10:00')");
        jt.execute("INSERT INTO airdates (event_id, auditorium_id, dateTime) values (1, 3, '2018-12-15 19:00')");
        jt.execute("INSERT INTO airdates (event_id, auditorium_id, dateTime) values (2, 3, '2018-12-15 11:00')");
        jt.execute("INSERT INTO airdates (event_id, auditorium_id, dateTime) values (2, 2, '2018-12-15 20:00')");

    }

    @Override
    public NavigableMap<LocalDateTime, Auditorium> getAuditoriums(Event event) {
        return jt.query("SELECT dateTime, auditorium_id FROM airdates WHERE event_id = ?",
                new Object[]{event.getId()}, new ResultSetExtractor<NavigableMap<LocalDateTime, Auditorium>>() {
                    @org.springframework.lang.Nullable
                    @Override
                    public NavigableMap<LocalDateTime, Auditorium> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

                        NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();
                        while(resultSet.next()){

                            auditoriums.put(
                                    LocalDateTime.parse(resultSet.getString(1), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                                    auditoriumService.getById(resultSet.getLong(2)));
                        }
                        return auditoriums;
                    }
                });
    }

    @Override
    public void clear() {
        this.selectedEvent = null;
        this.selectedAirDate = null;
        this.selectedSeats = null;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
        this.selectedAirDate = null;
        this.selectedSeats = null;
    }

    public void setSelectedAirDate(LocalDateTime selectedAirDate) {
        this.selectedAirDate = selectedAirDate;
        this.selectedSeats = new TreeSet<>();
    }

    @Override
    public void selectSeat(Long seat) {
        selectedSeats.add(seat);
    }

    public boolean isEventSelected() {
        return selectedEvent != null;
    }

    public boolean isAirDateSelected() {
        return selectedAirDate != null;
    }



    @Override
    public Event save(@Nonnull Event event) {
        jt.update("INSERT INTO events (name, basePrice, raiting) values (?, ?, ?)",
                event.getName(),
                event.getBasePrice(),
                event.getRating().toString());
        this.setSelectedEvent(event);
        return event;
    }

    @Override
    public void remove(@Nonnull Event event) {
        jt.update("DELETE FROM events WHERE id = ?",
                event.getId());
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return getAll()
                .stream()
                .filter(event -> event.getId().equals(id)).findFirst().get();
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return jt.query("SELECT * FROM events ",
                eventRowMapper
        );
    }

    @Nullable
    @Override
    public Collection<Event> getByName(@Nonnull String name) {
        return this.getAll()
                .stream()
                .filter( event -> event.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}
