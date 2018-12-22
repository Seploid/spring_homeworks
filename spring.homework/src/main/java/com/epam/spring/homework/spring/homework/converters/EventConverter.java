package com.epam.spring.homework.spring.homework.converters;

import com.epam.spring.homework.spring.homework.domain.Event;
import com.epam.spring.homework.spring.homework.services.EventService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EventConverter implements Converter<String, Event> {

    private final EventService eventService;

    // (#45) foo bar
    private final Pattern pattern = Pattern.compile("^\\(#(.*)\\) .+$");

    public EventConverter(EventService eventService) {
        this.eventService = eventService;
    }

    @Nullable
    @Override
    public Event convert(String source) {
        Matcher matcher = this.pattern.matcher(source);
        if (matcher.find()){
            String group = matcher.group(1);
            if (StringUtils.hasText(group)){
                Long id = Long.parseLong(group);
                return this.eventService.getById(id);
            }
        }
        return null;
    }
}
