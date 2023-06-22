package com.example.web.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBase {
    public void insertMassage(String textFromUser, String textAfterEncryption, String selectedItem, Connection connection, int idOfUser) {
        try {
            String sql = "INSERT INTO cypherauditlog (input, output,cypher,idofuser) VALUES (?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, textFromUser);
            statement.setString(2, textAfterEncryption);
            statement.setString(3, selectedItem);
            statement.setInt(4, (idOfUser));
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in connecting to PostgradeSQL()");
            throw new RuntimeException(e);
        }
    }
}
