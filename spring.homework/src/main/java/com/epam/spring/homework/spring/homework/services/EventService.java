package com.epam.spring.homework.spring.homework.services;

import com.epam.spring.homework.spring.homework.domain.Event;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Component
public class EventService implements IEventService {


    private Event selectedEvent;
    private LocalDateTime selectedAirDate;
    private Set<Long> selectedSeats;
    @Autowired
    private List<Event> events = new ArrayList<>();

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
        this.events.add(event);
        this.setSelectedEvent(event);
        return event;
    }

    @Override
    public void remove(@Nonnull Event event) {
        this.events.remove(event);
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return this.events
                .stream()
                .filter( event -> event.getId().equals(id))
                .findFirst().get();
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return this.events;
    }

    @Nullable
    @Override
    public Collection<Event> getByName(@Nonnull String name) {
        return this.events
                .stream()
                .filter( event -> event.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}
