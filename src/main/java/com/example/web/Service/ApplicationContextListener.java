package com.example.web.Service;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String username = "postgres";
        String password = "123";
        String jdbcURL = "jdbc:postgresql://localhost:5432/Skuska";

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Connection connection1 = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to PostgreSQL server");
            servletContext.setAttribute("databaseConnection", connection);
            servletContext.setAttribute("userRegistration", connection1);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        Connection connection = (Connection) servletContext.getAttribute("databaseConnection");
        try {
            connection.close();
            System.out.println("Disconnected from PostgreSQL server");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
