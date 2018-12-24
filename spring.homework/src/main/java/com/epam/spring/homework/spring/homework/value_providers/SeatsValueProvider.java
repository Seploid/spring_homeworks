package com.epam.spring.homework.spring.homework.value_providers;

import com.epam.spring.homework.spring.homework.domain.Ticket;
import com.epam.spring.homework.spring.homework.services.IBookingService;
import com.epam.spring.homework.spring.homework.services.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SeatsValueProvider implements ValueProvider {

    @Autowired
    private IEventService eventService;

    @Autowired
    private IBookingService bookingService;

    @Override
    public boolean supports(MethodParameter methodParameter, CompletionContext completionContext) {
        return methodParameter.getParameterType().isAssignableFrom(Long.class);
    }

    @Override
    public List<CompletionProposal> complete(MethodParameter methodParameter, CompletionContext completionContext, String[] strings) {
        return eventService.getAuditoriums(eventService.getSelectedEvent())
                .get(eventService.getSelectedAirDate()).getAllSeats()
                .stream()
                .filter(s -> !eventService.getSelectedSeats().contains(s))
                .filter(s -> !bookingService.getPurchasedTicketsForEvent(eventService.getSelectedEvent(), eventService.getSelectedAirDate())
                        .stream().map(Ticket::getSeat).collect(Collectors.toList()).contains(s))
                .map(s -> String.format("seat #%s", s))
                .map(CompletionProposal::new)
                .collect(Collectors.toList());
    }
}
