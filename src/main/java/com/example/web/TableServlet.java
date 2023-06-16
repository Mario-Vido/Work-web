package com.example.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/table")
public class TableServlet extends HttpServlet {
    Connection connection = null;

    public void init() throws ServletException {
        String jdbcURL = "jdbc:postgresql://localhost:5432/Skuska";
        String username = "postgres";
        String password = "123";
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException e) {
            throw new ServletException("Database connection failed", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<table style=\"border-collapse: collapse;\">");
        out.println("<tr style=\"border: 1px solid black;\"><th style=\"border: 1px solid black; padding: 5px;\">ID</th><th style=\"border: 1px solid black; padding: 5px;\">Input</th>" +
                "<th style=\"border: 1px solid black; padding: 5px;\">Output</th><th style=\"border: 1px solid black; padding: 5px;\">Cypher</th><th style=\"border: 1px solid black; padding: 5px;\">Timestamp</th></tr>");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cypherauditlog");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String input = resultSet.getString("input");
                String output = resultSet.getString("output");
                String cypher = resultSet.getString("cypher");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");

                out.println("<tr>");
                out.println("<td style=\"border: 1px solid black; padding: 5px;\">" + id + "</td>");
                out.println("<td style=\"border: 1px solid black; padding: 5px;\">" + input + "</td>");
                out.println("<td style=\"border: 1px solid black; padding: 5px;\">" + output + "</td>");
                out.println("<td style=\"border: 1px solid black; padding: 5px;\">" + cypher + "</td>");
                out.println("<td style=\"border: 1px solid black; padding: 5px;\">" + timestamp + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new ServletException("Database connection failed", e);
        }
    }

    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}