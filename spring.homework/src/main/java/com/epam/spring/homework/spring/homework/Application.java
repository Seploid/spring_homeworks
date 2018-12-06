package com.epam.spring.homework.spring.homework;

import com.epam.spring.homework.spring.homework.domain.User;
import com.epam.spring.homework.spring.homework.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@SpringBootApplication
@ImportResource("classpath:spring.xml")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

@ShellComponent
class UserCommands {

	private final ConsoleService consoleService;
	private final UserService userService;

	UserCommands(ConsoleService consoleService, UserService userService) {
		this.consoleService = consoleService;
		this.userService = userService;
	}

	@ShellMethod("Add new user")
	public void addUser(String firstName, String lastName, String email) {
		User user = new User(firstName, lastName, email);
		this.userService.save(user);
		this.consoleService.write("User %s was added.", user);
	}

	@ShellMethod("Show all users")
	public void showAllUsers() {
		this.userService.getAll()
				.stream()
				.forEach(user -> this.consoleService.write(user.toString()));
	}

	@ShellMethod("Get user by email")
	public void getUserByEmail(String email) {
		userService.getUserByEmail(email);
	}

	@ShellMethod("Take user")
	public void takeUser(User user) {
		userService.setCurrentUser(user);
		this.consoleService.write("Currnt user is %s.", user);
	}
}
