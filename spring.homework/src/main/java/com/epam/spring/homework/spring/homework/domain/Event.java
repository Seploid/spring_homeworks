package com.epam.spring.homework.spring.homework.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Yuriy_Tkach
 */
@Data
public class Event extends DomainObject {

    private static Long counterOfIds = 0L;

    private String name;

//    private Set<LocalDateTime> airDates = new TreeSet<>();

    private double basePrice;

    private EventRating rating;

//    private NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();

    public Event(){
        this.setId(counterOfIds++);
    }

    @Override
    public String toString() {
        return String.format("(#%s) %s", getId(), getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Event other = (Event) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}

