package com.epam.spring.homework.spring.homework;

import com.epam.spring.homework.spring.homework.domain.User;
import com.epam.spring.homework.spring.homework.services.UserService;
import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;

public class ConnectedPromptProvider implements PromptProvider {

    private final UserService userService;

    public ConnectedPromptProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AttributedString getPrompt() {
        User user = this.userService.getCurrentUser();
        String userAlias = user!=null?user.getId().toString(): "User was not selected!";

        return new AttributedString(String.format("next command (USER:%s)> ", userAlias));
    }
}
