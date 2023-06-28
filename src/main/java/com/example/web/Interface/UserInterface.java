package com.example.web.Interface;

import com.example.web.Objects.User;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserInterface {

    boolean authenticateUser(Connection connection, String login, String password);
    int getUserIdByUsername(Connection connection, String username);

    String checkUserAuthorization(HttpServletRequest request);

    List<String> getUserRoleById(Integer userId, Connection connection);

    String registerUser(User user, Connection connection) throws ClassNotFoundException, SQLException;
}
