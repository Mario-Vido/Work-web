package com.example.web.Servlets.ServletsForDataBase;

import java.io.IOException;
import java.sql.*;
import com.example.web.Service.ServerService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "CypherLogForAdmin", urlPatterns = "/cypher-log-for-admin")
public class CypherLogTableForAdmin extends HttpServlet {
    Connection connection = null;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Connection conn = (Connection) context.getAttribute("databaseConnection");
        String jsp = "/cypherLogForAdmin.jsp";

        response.setContentType("text/html");
        ServerService service = new ServerService();
        service.createTable(conn,request,response,jsp);
    }
    public void destroy () {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}