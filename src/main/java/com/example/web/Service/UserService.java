package com.example.web.Service;

import com.example.web.Interface.UserInterface;
import com.example.web.Objects.User;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService implements UserInterface {

    @Override
    public boolean authenticateUser(Connection connection, String login, String password) {
        String query = "SELECT 1 FROM users WHERE login = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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
            if ("Admin".equals(role)) {
                return "Admin";
            } else if ("User".equals(role)) {
                return "User";
            } else {
                return "User is not authorized";
            }
        }



    @Override
    public List<String> getUserRoleById(Integer userId, Connection connection) {
//        String query = "SELECT role_id FROM user_roles WHERE user_id = ?";
        List<String> roles = new ArrayList<>();

        String Query = String.format("SELECT role FROM roles WHERE id IN (SELECT role_id FROM user_roles WHERE user_id = %d)", userId);

        try(PreparedStatement statement = connection.prepareStatement(Query)){
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                String role = resultSet.getString("role");
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, userId);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    int roleId = resultSet.getInt("role_id");
//                    if (roleId == 1) {
//                        return "Admin";
//                    } else if (roleId == 2) {
//                        return "User";
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        return roles;
    }


    @Override
    public String registerUser(User user, Connection connection) throws SQLException {
        String INSERT_USERS_SQL = "INSERT INTO users (login, password) VALUES (?, ?);";
        String INSERT_USER_ROLE_SQL = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?);";

        connection.setAutoCommit(false);

        try (PreparedStatement userStatement = connection.prepareStatement(INSERT_USERS_SQL, Statement.RETURN_GENERATED_KEYS)) {
            userStatement.setString(1, user.getLogin());
            userStatement.setString(2, user.getPassword());
            userStatement.executeUpdate();

            try (ResultSet generatedKeys = userStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);

                    try (PreparedStatement roleStatement = connection.prepareStatement(INSERT_USER_ROLE_SQL)) {
                        roleStatement.setInt(1, userId);
                        roleStatement.setInt(2, 2);
                        roleStatement.executeUpdate();
                        connection.commit(); // Commit the transaction if everything succeeds
                        connection.setAutoCommit(true); // Restore auto-commit mode
                        return "Success";
                    }
                } else {
                    connection.rollback(); // Rollback the transaction if user_id is not generated
                    return "Failed"; // Exit the method to indicate a failed registration
                }
            }
        } catch (SQLException e) {
            connection.rollback();// Rollback the transaction in case of any exception
            throw e;// Rethrow the exception to indicate a failed registration
        }
    }
}
