package com.epam.spring.homework.spring.homework.services;

import com.epam.spring.homework.spring.homework.domain.Auditorium;
import com.epam.spring.homework.spring.homework.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.Set;

/**
 * @author Yuriy_Tkach
 */
public interface IEventService extends IAbstractDomainObjectService<Event> {

    NavigableMap<LocalDateTime, Auditorium> getAuditoriums(Event event);

    void clear();

    void setSelectedEvent(Event event);

    Event getSelectedEvent();

    void setSelectedAirDate(LocalDateTime localDateTime);

    LocalDateTime getSelectedAirDate();

    void selectSeat(Long seat);

    Set<Long> getSelectedSeats();

    boolean isEventSelected();

    boolean isAirDateSelected();

    /**
     * Finding event by name
     *
     * @param name
     *            Name of the event
     * @return found event or <code>null</code>
     */
    public @Nullable
    Collection<Event> getByName(@Nonnull String name);

    /*
     * Finding all events that air on specified date range
     *
     * @param from Start date
     *
     * @param to End date inclusive
     *
     * @return Set of events
     */
    // public @Nonnull Set<Event> getForDateRange(@Nonnull LocalDate from,
    // @Nonnull LocalDate to);

    /*
     * Return events from 'now' till the the specified date time
     *
     * @param to End date time inclusive
     * s
     * @return Set of events
     */
    // public @Nonnull Set<Event> getNextEvents(@Nonnull LocalDateTime to);

}
