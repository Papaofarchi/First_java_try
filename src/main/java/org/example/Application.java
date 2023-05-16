package org.example;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print(" Enter name and surname \n");
        String name = in.nextLine();
        String surname = in.nextLine();
        System.out.printf(" Name is " + name, "and surname is" + surname);
    }
}