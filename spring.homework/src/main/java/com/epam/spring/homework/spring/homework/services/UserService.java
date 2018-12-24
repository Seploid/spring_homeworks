package com.epam.spring.homework.spring.homework.services;

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
import java.util.Collection;

@Data
@Component
public class UserService implements IUserService {

    private User selectedUser;

    private RowMapper<User> rowMapper = new RowMapper<User>() {
        @org.springframework.lang.Nullable
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            Long id = resultSet.getLong("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            User user = new User(firstName,lastName,email,password);
            user.setId(id);
            return user;
        }
    };

    @Autowired
    JdbcTemplate jt;

    @PostConstruct
    private void init(){
        jt.execute("CREATE TABLE users (id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1), firstName varchar(255), lastName varchar(255), email varchar(255), password varchar(255), PRIMARY KEY(id))");
        jt.execute("INSERT INTO users (firstName, lastName, email, password) VALUES ('Irina', 'Razzhivina', 'yuriy_razzhivin@epam.com', 'password1')");
        jt.execute("INSERT INTO users (firstName, lastName, email, password) VALUES ('Roman', 'Pechersky', 'roman_pechersky@epam.com', 'password1')");
        jt.execute("INSERT INTO users (firstName, lastName, email, password) VALUES ('Anton', 'Shaklein', 'anton_shaklein@epam.com', 'password1')");
    }

    @Nullable
    //todo: select by name
    public Collection<User> findByName(@Nonnull String name) {
        return jt.query("SELECT * FROM users ",
                rowMapper
                );
    }

    @Override
    public User save(@Nonnull User user) {
        jt.update("INSERT INTO users (firstName, lastName, email, password) VALUES (?,?,?,?)",
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword());
        return user;
    }

    @Override
    public void remove(@Nonnull User user) {
        jt.update("DELETE FROM users WHERE id = ?",
                user.getId());
    }

    @Override
    public User getById(@Nonnull Long id) {
        return jt.queryForObject("SELECT * FROM users WHERE id = ?",
                new Object[]{id},
                rowMapper);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return jt.query("SELECT * FROM users ",
                rowMapper
        );
    }

    @Override
    public boolean isLoggedIn() {
        return getSelectedUser() != null;
    }

    @Override
    public void loginAs(User user) {
        this.selectedUser = user;
    }

    @Override
    public void logOut() {
        this.selectedUser = null;
    }


}
