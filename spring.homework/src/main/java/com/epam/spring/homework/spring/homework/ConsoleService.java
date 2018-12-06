package com.epam.spring.homework.spring.homework;

import java.io.PrintStream;

public class ConsoleService {

    private final static String ANTI_RESET = "\u001B[0m";
    private final static String ANTI_YELLOW = "\u001B[33m";

    private final PrintStream out = System.out;

    public void write(String msg, Object ... args){
        this.out.print("> ");
        this.out.print(ANTI_YELLOW);
        this.out.printf(msg, args);
        this.out.print(ANTI_RESET);
        this.out.println();
    }

}
