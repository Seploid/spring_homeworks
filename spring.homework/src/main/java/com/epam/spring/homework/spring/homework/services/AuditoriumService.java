package com.epam.spring.homework.spring.homework.services;

import com.epam.spring.homework.spring.homework.domain.Auditorium;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

@Data
@Component
public class AuditoriumService implements IAuditoriumService {

    Set<Auditorium> auditoriums;

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return auditoriums;
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return auditoriums
                .stream()
                .filter(a -> a.getName().equals(name))
                .findFirst().get();
    }
}
