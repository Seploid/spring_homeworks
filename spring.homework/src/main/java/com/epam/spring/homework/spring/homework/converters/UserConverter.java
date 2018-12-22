package com.epam.spring.homework.spring.homework.converters;

import com.epam.spring.homework.spring.homework.domain.User;
import com.epam.spring.homework.spring.homework.services.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserConverter implements Converter<String, User> {

    private final UserService userService;

    // (#45) foo bar
    private final Pattern pattern = Pattern.compile("\\(#(.*)\\)");

    public UserConverter(UserService userService) {
        this.userService = userService;
    }

    @Nullable
    @Override
    public User convert(String source) {
        Matcher matcher = this.pattern.matcher(source);
        if (matcher.find()){
            String group = matcher.group(1);
            if (StringUtils.hasText(group)){
                Long id = Long.parseLong(group);
                return this.userService.getById(id);
            }
        }
        return null;
    }
}
