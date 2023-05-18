package org.example.service;

import java.util.Scanner;

public class ConsoleService {
    Scanner in = new Scanner(System.in);
    public String getInput(){
        String input = in.nextLine();

        return input;
    }
    public void printInput(String input ){
        System.out.println(input);

    }

}

