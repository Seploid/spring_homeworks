package com.epam.spring.homework.spring.homework.value_providers;

import com.epam.spring.homework.spring.homework.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocalDateTimeProvider implements ValueProvider {

    @Autowired
    private EventService eventService;

    @Override
    public boolean supports(MethodParameter methodParameter, CompletionContext completionContext) {
        return methodParameter.getParameterType().isAssignableFrom(LocalDateTime.class);
    }

    @Override
    public List<CompletionProposal> complete(MethodParameter methodParameter, CompletionContext completionContext, String[] strings) {
//        String currentInput = completionContext.currentWordUpToCursor();
        return eventService.getAuditoriums(eventService.getSelectedEvent()).keySet()
                .stream()
                .map( ldt -> ldt.toString())
                .map(CompletionProposal::new)
                .collect(Collectors.toList());
    }
}
