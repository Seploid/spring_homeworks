package com.epam.spring.homework.spring.homework;

import com.epam.spring.homework.spring.homework.domain.User;
import com.epam.spring.homework.spring.homework.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@SpringBootApplication
@ImportResource("classpath:spring.xml")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

@ShellComponent
class UserCommands {

	private final UserService userService;

	UserCommands(UserService userService) {
		this.userService = userService;
	}

	@ShellMethod("Add new user")
	public void addUser(String firstName, String lastName, String email) {
		userService.save(new User(firstName, lastName, email));
	}
}
