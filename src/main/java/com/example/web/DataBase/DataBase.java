package com.example.web.DataBase;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBase {
    public void insertMassage(String textFromUser, String textAfterEncryption, String selectedItem){
        String jdbcURL = "jdbc:postgresql://localhost:5432/Skuska";
        String username = "postgres";
        String password = "123";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL,username,password);
            System.out.println("Connected ti PostgradeSQL server");
            String sql = "INSERT INTO cypherauditlog (input, output,cypher) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,textFromUser);
            statement.setString(2,textAfterEncryption);
            statement.setString(3,selectedItem);
            statement.executeUpdate();


            connection.close();
        } catch (SQLException e) {
            System.out.println("Error in connecting to PostgradeSQL()");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
