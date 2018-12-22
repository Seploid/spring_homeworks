package com.epam.spring.homework.spring.homework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({UsersConfig.class, EventsConfig.class})
public class AppConfig {


}
