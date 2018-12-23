package com.epam.spring.homework.spring.homework.aspects;

import com.epam.spring.homework.spring.homework.ConsoleService;
import com.epam.spring.homework.spring.homework.domain.Event;
import com.epam.spring.homework.spring.homework.services.IEventService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CounterAspect {

    @Autowired
    ConsoleService consoleService;

    @Autowired
    IEventService eventService;

    private Map<Event, Integer> eventCounter = new HashMap<>();

    private Map<Event, Integer> eventPriceCounter = new HashMap<>();

    private Map<Event, Integer> eventTiketsWasBookedCounter = new HashMap<>();

    @After("execution(* *.selectEvent(..))")
    public void counterHowOftenSelectEventAfter(JoinPoint joinPoint){
        if (!eventCounter.containsKey((Event) joinPoint.getArgs()[0])){
            eventCounter.put((Event) joinPoint.getArgs()[0], 0);
        }
        eventCounter.put((Event) joinPoint.getArgs()[0], eventCounter.get((Event) joinPoint.getArgs()[0]) + 1);
        consoleService.writeln("Selected event was selected already %s times", eventCounter.get((Event) joinPoint.getArgs()[0]));
    }


    @After("execution(* *.getBasePrice())")
    public void counterHowOftenPriceOfSelectedEventWasQuiredAfter(JoinPoint joinPoint){
        if (!eventPriceCounter.containsKey((Event) joinPoint.getTarget())){
            eventPriceCounter.put((Event) joinPoint.getTarget(), 0);
        }
        eventPriceCounter.put((Event) joinPoint.getTarget(), eventPriceCounter.get((Event) joinPoint.getTarget()) + 1);
        consoleService.writeln("Price of elected event was quired already %s times", eventPriceCounter.get((Event) joinPoint.getTarget()));
    }

    @Before("execution(* *.bookSelectedSeats())")
    public void counterHowOftenTicketsOfSelectEventWereBookedBefore(JoinPoint joinPoint){
        if (!eventTiketsWasBookedCounter.containsKey(eventService.getSelectedEvent())){
            eventTiketsWasBookedCounter.put(eventService.getSelectedEvent(), 0);
        }
        eventTiketsWasBookedCounter.put(eventService.getSelectedEvent(), eventTiketsWasBookedCounter.get(eventService.getSelectedEvent()) + 1);
        consoleService.writeln("Selected event was booked already %s times", eventTiketsWasBookedCounter.get(eventService.getSelectedEvent()));
    }


}
