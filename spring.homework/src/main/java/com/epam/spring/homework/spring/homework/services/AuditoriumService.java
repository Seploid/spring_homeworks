package com.epam.spring.homework.spring.homework.services;

import com.epam.spring.homework.spring.homework.domain.Auditorium;
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
import java.util.HashSet;
import java.util.Set;

@Data
@Component
public class AuditoriumService implements IAuditoriumService {

    Set<Auditorium> auditoriums;

    @Autowired
    JdbcTemplate jt;

    private RowMapper<Auditorium> rowMapper = new RowMapper<Auditorium>() {
        @org.springframework.lang.Nullable
        @Override
        public Auditorium mapRow(ResultSet resultSet, int i) throws SQLException {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Long numberOfSeats = resultSet.getLong("numberOfSeats");
            Auditorium auditorium = new Auditorium();
            auditorium.setName(name);
            auditorium.setNumberOfSeats(numberOfSeats);
            auditorium.setId(id);
            return auditorium;
        }
    };

    @PostConstruct
    private void init(){
        jt.execute("CREATE TABLE auditoriums (id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1), name varchar(255), numberOfSeats int)");
        jt.execute("INSERT INTO auditoriums (name, numberOfSeats) values ('Red Auditorium', 50)");
        jt.execute("INSERT INTO auditoriums (name, numberOfSeats) values ('Blue Auditorium', 50)");
        jt.execute("INSERT INTO auditoriums (name, numberOfSeats) values ('Premier Auditorium', 450)");
    }

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return new HashSet<>(
                jt.query("SELECT * FROM auditoriums",
                rowMapper));
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return getAll()
                .stream()
                .filter(a -> a.getName().equals(name))
                .findFirst().get();
    }

    @Nullable
    @Override
    public Auditorium getById(@Nonnull Long id) {
        return jt.queryForObject("SELECT * FROM auditoriums WHERE id = ?",
                new Object[]{id},
                rowMapper);
    }
}
