package com.example.web.Servlets.ServletsForDataBase;

import java.io.IOException;
import java.sql.*;
import com.example.web.Service.ServerService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "Cypher table", urlPatterns = "/table")
public class TableServlet extends HttpServlet {
    Connection connection = null;

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletContext context = getServletContext();
        ServerService serverService = (ServerService) context.getAttribute("serverService");
        Connection conn = (Connection) context.getAttribute("databaseConnection");
        String jsp = "/cypherlog.jsp";

        response.setContentType("text/html");
        serverService.createTable(conn,request,response,jsp);
    }
    public void destroy () {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}