package com.example.web.Interface;

import com.example.web.Objects.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface LoginInterface {
    boolean authenticateUser(Connection connection, String login, String password);
    int getUserIdByUsername(Connection connection, String username);

    String checkUserAuthorization(HttpServletRequest request);

    String getUserRoleById(Integer userId,Connection connection);

    void registerUser(User user, Connection connection) throws ClassNotFoundException, SQLException;

    void createTable(Connection conn,HttpServletRequest request, HttpServletResponse response, String jsp) throws ServletException, IOException;
}
