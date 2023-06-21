package com.example.web.Interface;

import java.sql.Connection;

public interface LoginInterface {
    boolean authenticateUser(Connection connection, String login, String password);
    int getUserIdByUsername(Connection connection, String username);
}
