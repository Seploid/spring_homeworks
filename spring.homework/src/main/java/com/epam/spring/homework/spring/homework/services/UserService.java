package com.epam.spring.homework.spring.homework.services;

import com.epam.spring.homework.spring.homework.domain.User;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Data
public class UserService implements IUserService {

    private Map<Long, User> users = new ConcurrentHashMap<>();
    private User selectedUser;

    public void setUsers(Map<Long, User> users) {
        this.users = users;
    }

    @Nullable
    public Collection<User> findByName(@Nonnull String name) {
        return users.values()
                .stream()
                .filter(usr -> usr.getEmail().contains(name) || usr.getFirstName().contains(name) || usr.getLastName().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public User save(@Nonnull User user) {
        return users.put(new Random().nextLong(), user);
    }

    @Override
    public void remove(@Nonnull User user) {
        users.entrySet().removeIf(entry -> entry.equals(user));
    }

    @Override
    public User getById(@Nonnull Long id) {
        return users.get(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return users.values();
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
