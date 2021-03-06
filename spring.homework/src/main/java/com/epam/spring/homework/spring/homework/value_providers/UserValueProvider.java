package com.epam.spring.homework.spring.homework.value_providers;

import com.epam.spring.homework.spring.homework.domain.User;
import com.epam.spring.homework.spring.homework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserValueProvider implements ValueProvider{

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(MethodParameter methodParameter, CompletionContext completionContext) {
        return methodParameter.getParameterType().isAssignableFrom(User.class);
    }

    @Override
    public List<CompletionProposal> complete(MethodParameter methodParameter, CompletionContext completionContext, String[] strings) {
        String currentInput = completionContext.currentWordUpToCursor();
        return userService.findByName(currentInput)
                .stream()
                .map( p -> String.format("(#%s) %s %s %s", p.getId(), p.getFirstName(), p.getLastName(), p.getEmail()))
                .map(CompletionProposal::new)
                .collect(Collectors.toList());
    }
}
