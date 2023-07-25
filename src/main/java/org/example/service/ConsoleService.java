package org.example.service;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
@Slf4j
public class ConsoleService {
    Scanner in = new Scanner(System.in);

    public String getInput() {
        return in.nextLine();
    }

    public void printInput(String input) {
       log.debug(input);

    }

}

