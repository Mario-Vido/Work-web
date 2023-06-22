package com.example.web.Service;

import com.example.web.Interface.LoginInterface;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService implements LoginInterface {

    @Override
    public boolean authenticateUser(Connection connection, String login, String password) {
        String query = "SELECT * FROM users WHERE login = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getUserIdByUsername(Connection connection, String username) {
        String query = "SELECT id FROM users WHERE login = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public String checkUserAuthorization(HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("role");

        if (role == null) {
            // User ID not found in the session, user is not authorized
            return "role not found";
        }
        // Perform authorization checks based on the user's role
        if ("Admin".equals(role)) {
            // Allow access for admin role
            return "Admin";
        } else if ("User".equals(role)) {
            // Allow access for user role
            return "User";
        }

        // Default case: user is not authorized
        return "User is not authorized";
    }

    @Override
    public String getUserRoleById(Integer userId,Connection connection) {
        String query = "SELECT role FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("role");
            } else {
                return "Unknown";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

}
