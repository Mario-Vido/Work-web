package com.example.web;

import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String jdbcURL = "jdbc:postgresql://localhost:5432/Skuska";
        String username = "postgres";
        String password = "123";

        try  {
            Connection connection = null;
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcURL,username,password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cypherauditlog");

            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Input</th><th>Output</th><th>Cypher</th><th>Timestamp</th></tr>");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String input = resultSet.getString("input");
                String output = resultSet.getString("output");
                String cypher = resultSet.getString("cypher");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + input + "</td>");
                out.println("<td>" + output + "</td>");
                out.println("<td>" + cypher + "</td>");
                out.println("<td>" + timestamp + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");
        } catch (SQLException e) {
            throw new ServletException("Database connection failed",e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

        public void destroy() {
    }
}