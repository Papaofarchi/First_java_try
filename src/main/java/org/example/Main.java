package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print(" Enter name and surename \n");
        String name = in.nextLine();
        String surename = in.nextLine();
        System.out.printf(" Enter name is " + name, "and surename is" + surename);
    }
}