package com.epam.spring.homework.spring.homework.domain;

import lombok.Data;

import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;

/**
 * @author Yuriy_Tkach
 */
@Data
public class User extends DomainObject {

    private static Long counterOfIds = 0L;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private NavigableSet<Ticket> tickets = new TreeSet<>();

    public User(String firstName, String lastName, String email, String password) {
        this.setId(counterOfIds++);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s)", firstName, lastName, email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, password);
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
        User other = (User) obj;
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        return true;
    }

}
