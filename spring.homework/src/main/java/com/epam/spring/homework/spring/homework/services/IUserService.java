package com.epam.spring.homework.spring.homework.services;

import com.epam.spring.homework.spring.homework.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Yuriy_Tkach
 */
public interface IUserService extends IAbstractDomainObjectService<User> {

    /**
     * Finding user by email
     *
     * @param email
     *            Email of the user
     * @return found user or <code>null</code>
     */
    public @Nullable User getUserByEmail(@Nonnull String email);

}