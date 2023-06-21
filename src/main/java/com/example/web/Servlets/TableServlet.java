package com.example.web.Servlets;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.web.Objects.DatabaseValues;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/table")
public class TableServlet extends HttpServlet {
    Connection connection = null;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Connection conn = (Connection) context.getAttribute("databaseConnection");

        response.setContentType("text/html");
        List<DatabaseValues> databaseValuesList = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cypherauditlog");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String input = resultSet.getString("input");
                String output = resultSet.getString("output");
                String cypher = resultSet.getString("cypher");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");

                DatabaseValues databaseValues = new DatabaseValues(id, input, output, cypher, timestamp);
                databaseValuesList.add(databaseValues);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("databaseValuesList", databaseValuesList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cypherlog.jsp");
        dispatcher.forward(request, response);
    }
    public void destroy () {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}