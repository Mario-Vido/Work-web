package com.example.web.DataBase;

import com.example.web.Objects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRegistration {

    public void registerUser(User user, Connection connection) throws ClassNotFoundException, SQLException {
        String INSERT_USERS_SQL = "INSERT INTO users (login, password, role) VALUES (?, ?, ?);";

        PreparedStatement statement = connection.prepareStatement(INSERT_USERS_SQL);
        statement.setString(1,user.getLogin());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getRole());

        System.out.println(statement);

        statement.executeUpdate();

    }
}
