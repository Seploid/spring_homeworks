package com.epam.spring.homework.spring.homework.services;

import com.epam.spring.homework.spring.homework.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IUserService extends IAbstractDomainObjectService<User> {

    boolean isLoggedIn();

    void loginAs(User user);

    void logOut();

}