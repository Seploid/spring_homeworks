package com.epam.spring.homework.spring.homework.converters;

import com.epam.spring.homework.spring.homework.services.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SeatConverter implements Converter<String, Long> {

    @Autowired
    private IEventService eventService;

    // #12
    private final Pattern pattern = Pattern.compile("#(.*)");

    @Nullable
    @Override
    public Long convert(String source) {
        Matcher matcher = this.pattern.matcher(source);
        if (matcher.find()){
            String group = matcher.group(1);
            if (StringUtils.hasText(group)){
                Long seat = Long.parseLong(group);
                return this.eventService
                        .getSelectedEvent()
                        .getAuditoriums()
                        .get(eventService.getSelectedAirDate())
                        .getAllSeats().contains(seat)? seat: null;
//                todo: add booking service for excuding bought tickets(seats)

            }
        }
        return null;
    }
}
