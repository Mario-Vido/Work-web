package com.example.web.Interface;

import com.example.web.Objects.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserInterface {

    boolean findUserInDataBase(Connection connection, String login);

    boolean authenticateUser(Connection connection, String login, String password);

    int getUserIdByUsername(Connection connection, String username);

    List<String> getUserRoleById(Integer userId, Connection connection);

    String registerUser(User user, Connection connection) throws ClassNotFoundException, SQLException;
}
