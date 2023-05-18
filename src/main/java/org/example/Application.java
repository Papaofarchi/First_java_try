package org.example;

import org.example.service.ConsoleService;

public class Application {
    public static void main(String[] args) {
        ConsoleService consoleService = new ConsoleService();
        consoleService.printInput("Enter name and surname ");
        String name = consoleService.getInput();
        String surname = consoleService.getInput();
        consoleService.printInput(" Name is " + name + " and surname is " + surname);
    }
}