package com.example.web.Interface;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.Connection;

public interface LoginInterface {
    boolean authenticateUser(Connection connection, String login, String password);
    int getUserIdByUsername(Connection connection, String username);

    String checkUserAuthorization(HttpServletRequest request);

    String getUserRoleById(Integer userId,Connection connection);
}
