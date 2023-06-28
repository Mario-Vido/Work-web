package com.example.web.Interface;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

public interface LoginInterface {

    void createTable(Connection conn,HttpServletRequest request, HttpServletResponse response,String jsp) throws ServletException, IOException;

    void encipher(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws IOException;

    void loginFromClient(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws IOException;

    void logout(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void registration(HttpServletRequest req, HttpServletResponse resp, Connection connection,ServletContext context);

    void loginFromServer(HttpServletRequest request, HttpServletResponse response, Connection connectionToUsedDatabase,ServletContext context) throws IOException, ServletException;

    void creatingCyphers(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void decypher(HttpServletRequest request, HttpServletResponse response) throws IOException;


}
