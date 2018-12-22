package com.epam.spring.homework.spring.homework.services;

import com.epam.spring.homework.spring.homework.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Yuriy_Tkach
 */
public interface IEventService extends IAbstractDomainObjectService<Event> {


    void setSelectedEvent(Event event);

    void setSelectedAirDate(LocalDateTime localDateTime);

    boolean isEventSelected();

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
