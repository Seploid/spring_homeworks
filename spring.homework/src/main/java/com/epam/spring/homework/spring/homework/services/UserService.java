package com.epam.spring.homework.spring.homework.services;

import com.epam.spring.homework.spring.homework.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class UserService implements IUserService {

    private Map<Long, User> users = new ConcurrentHashMap<>();

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return users.values().stream().filter(usr -> usr.getEmail().equals(email)).findFirst().get();
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

}
