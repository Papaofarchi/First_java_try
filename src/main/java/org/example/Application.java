package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Application {
    private static Connection con;
    private static Statement stmt;
    private static final String url = "jdbc:mysql://localhost:3306/shem";
    private static final String username = "root";
    private static final String password = "root";



    public static void main(String[] args) throws SQLException {




        System.out.println("Connecting database...");
        con = DriverManager.getConnection(url, username, password);
        stmt = con.createStatement();


        Scanner in = new Scanner(System.in);
        System.out.print(" Enter name, surname and email \n");
        String name = in.nextLine();
        String surname = in.nextLine();
        String email = in.nextLine();
        String query = "INSERT INTO shem.persons (name, surname, email) \n" +
                " VALUES ('" + name + "' , '" + surname + "' , '" +  email+ "');";
        stmt.executeUpdate(query);
    }
}