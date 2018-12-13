package com.epam.spring.homework.spring.homework;

import com.epam.spring.homework.spring.homework.domain.Event;
import com.epam.spring.homework.spring.homework.domain.User;
import com.epam.spring.homework.spring.homework.services.EventService;
import com.epam.spring.homework.spring.homework.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.shell.Availability;
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

	@ShellMethod("Register new user")
	public void registerUser(String firstName, String lastName, String email, String password, String confirmPassword) {
		if (!password.equals(confirmPassword)){
			this.consoleService.write("Incorrect confirmPassword field");
		} else if (this.userService.getAll()
				.stream()
				.anyMatch(user -> user.getEmail().equalsIgnoreCase(email))) {
			this.consoleService.write("User with the same email already existed");
		} else {
			User user = new User(firstName, lastName, email, password);
			this.userService.save(user);
			this.userService.loginAs(user);
			this.consoleService.write("User %s %s. was added and selected.", user.getLastName(), user.getFirstName().substring(0, 1));
		}
	}

	public Availability registerUserAvailability(){
		return userService.isLoggedIn()? Availability.unavailable("You are already logged in.") : Availability.available();
	}

//	@ShellMethod("Show all users")
	public void showAllUsers() {
		this.userService.getAll()
				.stream()
				.forEach(user -> this.consoleService.write(user.toString()));
	}

	@ShellMethod("Login as existed user")
	public void loginAs(User user, String password) {
		if (!user.getPassword().equals(password)) {
			this.consoleService.write("Incorrect user or password");
		} else {
			this.userService.setSelectedUser(user);
			this.consoleService.write("Current user is %s.", user);
		}
	}

	public Availability loginAsAvailability(){
		return userService.isLoggedIn()? Availability.unavailable("You are already logged in.") : Availability.available();
	}

	@ShellMethod("Log out")
	public void logOut() {
		this.userService.logOut();
		this.consoleService.write("You managed to log out.");
	}

	public Availability logOutAvailability(){
		return userService.isLoggedIn()? Availability.available() : Availability.unavailable("You are not logged in.");
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
				.forEach(event -> {
					this.consoleService.write(event.getName());
					this.consoleService.write(event.getAuditoriums().keySet().toString());
				});
	}
}