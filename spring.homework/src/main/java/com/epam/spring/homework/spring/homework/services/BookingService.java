package com.epam.spring.homework.spring.homework.services;

import com.epam.spring.homework.spring.homework.domain.Event;
import com.epam.spring.homework.spring.homework.domain.Ticket;
import com.epam.spring.homework.spring.homework.domain.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Data
@Component
public class BookingService implements IBookingService {

    Set<Ticket> bookedTickets = new TreeSet<>();

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        double price = event.getBasePrice();

        //if more then 3 tickets, them we provide discount = 10%
        if (seats.size()>3) price = price * 0.9;

        return price*seats.size();
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        bookedTickets.addAll(tickets);
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {

        return bookedTickets
                .stream()
                .filter(t -> t.getEvent().equals(event) && t.getDateTime().equals(dateTime))
                .collect(Collectors.toSet());
    }
}
