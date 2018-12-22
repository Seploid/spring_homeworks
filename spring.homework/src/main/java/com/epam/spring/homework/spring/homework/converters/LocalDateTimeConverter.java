package com.epam.spring.homework.spring.homework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

//    format of LocalDateTime string 2018-12-15T19:00

    @Nullable
    @Override
    public LocalDateTime convert(String s) {
        return LocalDateTime.parse(s);
    }
}
