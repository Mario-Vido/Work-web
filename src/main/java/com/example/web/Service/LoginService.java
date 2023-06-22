package com.example.web.Service;

import com.example.web.Interface.LoginInterface;
import com.example.web.Objects.DatabaseValues;
import com.example.web.Objects.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            return "role not found";
        }

        if ("Admin".equals(role)) {
            return "Admin";
        } else if ("User".equals(role)) {
            return "User";
        }


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

    @Override
    public void registerUser(User user, Connection connection) throws SQLException {
        String INSERT_USERS_SQL = "INSERT INTO users (login, password, role) VALUES (?, ?, ?);";

        PreparedStatement statement = connection.prepareStatement(INSERT_USERS_SQL);
        statement.setString(1,user.getLogin());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getRole());

        statement.executeUpdate();
    }

    @Override
    public void createTable(Connection conn,HttpServletRequest request, HttpServletResponse response,String jsp) throws ServletException, IOException {
        List<DatabaseValues> databaseValuesList = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cypherauditlog");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String input = resultSet.getString("input");
                String output = resultSet.getString("output");
                String cypher = resultSet.getString("cypher");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                Integer idOfUser = resultSet.getInt("idofuser");

                DatabaseValues databaseValues = new DatabaseValues(id, input, output, cypher, timestamp,idOfUser);
                databaseValuesList.add(databaseValues);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("databaseValuesList", databaseValuesList);
        RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
        dispatcher.forward(request, response);
    }

}
