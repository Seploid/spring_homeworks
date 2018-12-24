package com.epam.spring.homework.spring.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableAspectJAutoProxy
@PropertySource("jdbc.properties")
public class AppConfig {

    @Value("${jdbc.driverClassName}")
    String driverClassName;

    @Value("${jdbc.url}")
    String url;

    @Value("${jdbc.username}")
    String username;

    @Value("${jdbc.password}")
    String password;


    @Bean
    public JdbcTemplate getJDBCTemplate(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driverClassName);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        return new JdbcTemplate(driverManagerDataSource);
    }


}
