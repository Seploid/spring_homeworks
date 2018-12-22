package com.epam.spring.homework.spring.homework;

import com.epam.spring.homework.spring.homework.services.EventService;
import com.epam.spring.homework.spring.homework.services.UserService;
import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class ConsolePromptProvider implements PromptProvider {

    private final UserService userService;
    private final EventService eventService;

    public ConsolePromptProvider(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @Override
    public AttributedString getPrompt() {
        String tip = "INCORRECT FLOW";
        if (this.userService.getSelectedUser() == null) {
            tip = "You need to select existed or add new user:";
        } else if (this.eventService.getSelectedEvent() == null) {
            tip = "You need to select event:";
        } else {
            tip = "You need to select auditorium:";
        }
        return new AttributedString(String.format("TIP[%s]> ", tip));
    }
}
