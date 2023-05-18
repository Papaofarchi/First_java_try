package org.example;

import org.example.service.ConsoleService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Application {


    Connection con;
    Statement stmt;
    String url = "jdbc:mysql://localhost:3306/shem";
    String username = "root";
    String password = "root";


    public static void main(String[] args) throws SQLException {
        Application x = new Application();
        ConsoleService consoleService= new ConsoleService();
        System.out.println("Connecting database...");
        x.con = DriverManager.getConnection(x.url, x.username, x.password);
        x.stmt = x.con.createStatement();

        System.out.print(" Enter name, surname and email \n");
        String name = consoleService.getInput();
        String surname = consoleService.getInput();
        String email = consoleService.getInput();
        String query = "INSERT INTO shem.persons (name, surname, email) \n" +
                " VALUES ('" + name + "' , '" + surname + "' , '" +  email+ "');";
        x.stmt.executeUpdate(query);
    }
}