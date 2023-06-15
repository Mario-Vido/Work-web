package com.example.web;

import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    Connection connection = null;
    ResultSet resultSet;
    public void init() throws ServletException{
        String jdbcURL = "jdbc:postgresql://localhost:5432/Skuska";
        String username = "postgres";
        String password = "123";
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM cypherauditlog");
        }
        catch (SQLException e) {
            throw new ServletException("Database connection failed",e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        try  {
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<table style=\"border-collapse: collapse;\">");
            out.println("<tr style=\"border: 3px solid black;\">><th>ID</th><th>Input</th><th>Output</th><th>Cypher</th><th>Timestamp</th></tr>");

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
        } catch (SQLException e) {
            throw new ServletException("Database connection failed",e);
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