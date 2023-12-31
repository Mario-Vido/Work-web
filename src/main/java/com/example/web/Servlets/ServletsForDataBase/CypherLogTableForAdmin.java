package com.example.web.Servlets.ServletsForDataBase;

import java.io.IOException;
import java.sql.*;
import com.example.web.Service.ServerService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "Cypher log for admin", urlPatterns = "/cypher-log-for-admin")
public class CypherLogTableForAdmin extends HttpServlet {
    Connection connection = null;

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String jsp = "/cypherLogForAdmin.jsp";;
        ServletContext context = getServletContext();
        ServerService serverService = (ServerService) context.getAttribute("serverService");
        Connection conn = (Connection) context.getAttribute("databaseConnection");

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