package com.epam.spring.homework.spring.homework.services;

import com.epam.spring.homework.spring.homework.domain.Auditorium;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Yuriy_Tkach
 */
public interface IAuditoriumService {

    /**
     * Getting all auditoriums from the system
     *
     * @return set of all auditoriums
     */
    public @Nonnull Set<Auditorium> getAll();

    /**
     * Finding auditorium by name
     *
     * @param name
     *            Name of the auditorium
     * @return found auditorium or <code>null</code>
     */
    public @Nullable Auditorium getByName(@Nonnull String name);

}

