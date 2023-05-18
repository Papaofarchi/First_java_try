package org.example.service;

import java.util.Scanner;

public class ConsoleService {
    public String getInput(){
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        return input;
    }
    public void printInput(String input ){
        System.out.println(input);

    }

}

