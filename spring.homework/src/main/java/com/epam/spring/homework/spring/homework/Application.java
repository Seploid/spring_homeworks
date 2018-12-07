package com.epam.spring.homework.spring.homework;

import com.epam.spring.homework.spring.homework.domain.Event;
import com.epam.spring.homework.spring.homework.domain.User;
import com.epam.spring.homework.spring.homework.services.EventService;
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
		this.userService.setSelectedUser(user);
		this.consoleService.write("User %s %s. was added and selected.", user.getLastName(), user.getFirstName().substring(0,1));
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

	@ShellMethod("Select an user")
	public void selectUser(User user) {
		userService.setSelectedUser(user);
		this.consoleService.write("Current user is %s.", user);
	}
}

@ShellComponent
class EventCommands{

	private final ConsoleService consoleService;
	private final EventService eventService;

	EventCommands(ConsoleService consoleService, EventService eventService) {
		this.consoleService = consoleService;
		this.eventService = eventService;
	}

	@ShellMethod("Select event.")
	public void selectEvent(Event event){
		this.eventService.setSelectedEvent(event);
		this.consoleService.write("Selected event is %s.", event.getName());
	}

	@ShellMethod("Show all events.")
	public void showAllEvents(){
		this.eventService.getAll()
				.stream()
				.forEach(event -> this.consoleService.write(event.toString()));
	}
}