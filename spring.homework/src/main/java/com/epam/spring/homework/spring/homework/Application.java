package com.epam.spring.homework.spring.homework;

import com.epam.spring.homework.spring.homework.config.AppConfig;
import com.epam.spring.homework.spring.homework.domain.Event;
import com.epam.spring.homework.spring.homework.domain.Ticket;
import com.epam.spring.homework.spring.homework.domain.User;
import com.epam.spring.homework.spring.homework.services.*;
import com.epam.spring.homework.spring.homework.value_providers.EventValueProvider;
import com.epam.spring.homework.spring.homework.value_providers.LocalDateTimeProvider;
import com.epam.spring.homework.spring.homework.value_providers.SeatsValueProvider;
import com.epam.spring.homework.spring.homework.value_providers.UserValueProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

@SpringBootApplication
@Import(AppConfig.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

@ShellComponent
class UserCommands {

	@Autowired
	private ConsoleService consoleService;

	@Autowired
	private IUserService userService;

	@ShellMethod("Register new user")
	public void registerUser(String firstName, String lastName, String email, String password, String confirmPassword) {
		if (!password.equals(confirmPassword)){
			this.consoleService.writeln("Incorrect confirmPassword field");
		} else if (this.userService.getAll()
				.stream()
				.anyMatch(user -> user.getEmail().equalsIgnoreCase(email))) {
			this.consoleService.writeln("User with the same email already existed");
		} else {
			User user = new User(firstName, lastName, email, password);
			this.userService.save(user);
			this.userService.loginAs(user);
			this.consoleService.writeln("User %s %s. was added and selected.", user.getLastName(), user.getFirstName().substring(0, 1));
		}
	}

	public Availability registerUserAvailability(){
		return userService.isLoggedIn()? Availability.unavailable("You are already logged in.") : Availability.available();
	}

//	@ShellMethod("Show all users")
	public void showAllUsers() {
		this.userService.getAll()
				.stream()
				.forEach(user -> this.consoleService.writeln(user.toString()));
	}

	@ShellMethod("Login as existed user")
	public void loginAs(@ShellOption(valueProvider = UserValueProvider.class) User user, String password) {
		if (!user.getPassword().equals(password)) {
			this.consoleService.writeln("Incorrect user or password");
		} else {
			this.userService.setSelectedUser(user);
			this.consoleService.writeln("Current user is %s.", user);
		}
	}

	public Availability loginAsAvailability(){
		return userService.isLoggedIn()? Availability.unavailable("You are already logged in.") : Availability.available();
	}

	@ShellMethod("Log out")
	public void logOut() {
		this.userService.logOut();
		this.consoleService.writeln("You managed to log out.");
	}

	public Availability logOutAvailability(){
		return userService.isLoggedIn()? Availability.available() : Availability.unavailable("You are not logged in.");
	}

}

@ShellComponent
class EventCommands{

	@Autowired
	private ConsoleService consoleService;

	@Autowired
	private IEventService eventService;

	@ShellMethod("Select event.")
	public void selectEvent(@ShellOption(valueProvider = EventValueProvider.class) Event event){
		this.eventService.setSelectedEvent(event);
		this.consoleService.writeln("Selected event is %s.", event.getName());
		this.consoleService.writeln("Available air dates: ");
		event.getAuditoriums().forEach((localDateTime, auditorium) -> this.consoleService.write(String.format("%s[%s]  ", localDateTime, auditorium.getName())));
		this.consoleService.writeln("");
	}

	@ShellMethod("Show all events.")
	public void showAllEvents(){
		this.eventService.getAll()
				.stream()
				.forEach(event -> {
					this.consoleService.writeln(String.format("Event: %s",event.getName()));
					this.consoleService.writeln(String.format("Air Dates: %s ",event.getAuditoriums().keySet().toString()));
				});
	}

	@ShellMethod("Select available air date for selected event.")
	public void selectAidDate(@ShellOption(valueProvider = LocalDateTimeProvider.class) LocalDateTime localDateTime){
		this.eventService.setSelectedAirDate(localDateTime);
		this.consoleService.writeln("Selected air date is %s.", localDateTime.toString());
	}

	public Availability selectAidDateAvailability(){
		return eventService.isEventSelected()? Availability.available() : Availability.unavailable("You are not selected event.");
	}

	@ShellMethod("Select seats for selected Event/AirDate.")
	public void selectSeats(@ShellOption(valueProvider = SeatsValueProvider.class)Long seat){
		this.eventService.selectSeat(seat);
		this.consoleService.writeln("Selected seats is %s.", eventService.getSelectedSeats().toString());
	}

	public Availability selectSeatsAvailability(){
		return eventService.isAirDateSelected()? Availability.available() : Availability.unavailable("You are not selected Air Date.");
	}

}

@ShellComponent
class BookingCommand{

	@Autowired
	private ConsoleService consoleService;

	@Autowired
	private IEventService eventService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IBookingService bookingService;

	@ShellMethod("Book selected seats.")
	public void bookSelectedSeats(){
		Set<Ticket> tickets = new TreeSet();

		eventService.getSelectedSeats()
				.forEach(s -> tickets.add(new Ticket(userService.getSelectedUser(), eventService.getSelectedEvent(), eventService.getSelectedAirDate(), s)));


		bookingService.bookTickets(tickets);

		eventService.clear();

		consoleService.writeln("Tickets was managed to book.");
	}

	public Availability bookSelectedSeatsAvailability(){
		return eventService.getSelectedSeats() != null && userService.isLoggedIn() ? Availability.available() : Availability.unavailable("You are not selected event/airdate/seats.");
	}

	@ShellMethod("Show price of selected seats.")
	public void showPriceSelectedSeats(){
		consoleService.writeln("Price of selected tickets is %s", bookingService.getTicketsPrice(eventService.getSelectedEvent(), eventService.getSelectedAirDate(), userService.getSelectedUser(), eventService.getSelectedSeats()));
	}

	public Availability showPriceSelectedSeatsAvailability(){
		return eventService.getSelectedSeats() != null && userService.isLoggedIn() ? Availability.available() : Availability.unavailable("You are not selected user/event/airdate/seats.");
	}
}