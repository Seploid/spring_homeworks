package com.epam.spring.homework.spring.homework.config;

import com.epam.spring.homework.spring.homework.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class UsersConfig {

    @Bean(name = "yuriy")
    User yuriy(){
        return new User("Yuriy", "Razzhivin", "yuriy_rzzhivin@epam.com", "password1");
    }

    @Bean(name = "roman")
    User roman(){
        return new User("Roman", "Pechersky", "roman_pechersky@epam.com", "password1");
    }

    @Bean(name = "anton")
    User anton(){
        return new User("Anton", "Shaklein", "anton_shaklein@epam.com", "password1");
    }

    @Bean(name = "defaultUsers")
    Map<Long, User> users() {
        Map<Long, User> users = new HashMap<>();
        User yuriy = new User("Yuriy", "Razzhivin", "yuriy_rzzhivin@epam.com", "password1");
        users.put(yuriy.getId(), yuriy);
        User roman = new User("Roman", "Pechersky", "roman_pechersky@epam.com", "password1");
        users.put(roman.getId(), roman);
        User anton = new User("Anton", "Shaklein", "anton_shaklein@epam.com", "password1");
        users.put(anton.getId(),anton);
        return users;
    }
}
