package com.epam.spring.homework.spring.homework.services;

import com.epam.spring.homework.spring.homework.domain.Event;
import com.epam.spring.homework.spring.homework.domain.EventRating;
import com.epam.spring.homework.spring.homework.domain.Ticket;
import com.epam.spring.homework.spring.homework.domain.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Data
@Component
public class BookingService implements IBookingService {

    @Autowired
    JdbcTemplate jt;

    @Autowired
    IUserService userService;

    @Autowired
    IEventService eventService;

    private RowMapper<Ticket> ticketRowMapper = new RowMapper<Ticket>() {
        @org.springframework.lang.Nullable
        @Override
        public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
            Long id = resultSet.getLong("id");
            User user = userService.getById(resultSet.getLong("user_id"));
            Event event = eventService.getById(resultSet.getLong("event_id"));
            LocalDateTime localDateTime = LocalDateTime.parse(resultSet.getString("dateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            Long seat = resultSet.getLong("seat");
            Ticket ticket = new Ticket(user, event, localDateTime, seat);
            return ticket;
        }
    };

    @PostConstruct
    private void init(){
        //Booked Tickets table
        jt.execute("CREATE TABLE bookedTickets (id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1), event_id int, dateTime varchar(255), user_id int, seat int)");
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        double price = event.getBasePrice();

        //if more then 3 tickets, them we provide discount = 10%
        if (seats.size()>3) price = price * 0.9;

        return price*seats.size();
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        tickets.stream()
                .forEach(ticket -> jt.update("INSERT INTO bookedTickets (event_id, dateTime, user_id, seat) values (?, ?, ?, ?)",
                        ticket.getEvent().getId(), ticket.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), ticket.getUser().getId(), ticket.getSeat()));
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {

        return jt.query("SELECT * FROM bookedTickets ",
                ticketRowMapper)
                .stream()
                .filter(t -> t.getEvent().equals(event) && t.getDateTime().equals(dateTime))
                .collect(Collectors.toSet());
    }
}
