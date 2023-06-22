package com.example.web.Servlets.ServletsForDataBase;

import java.io.IOException;
import java.sql.*;
import com.example.web.Service.LoginService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", urlPatterns = "/table")
public class TableServlet extends HttpServlet {
    Connection connection = null;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Connection conn = (Connection) context.getAttribute("databaseConnection");
        String jsp = "cypherlog.jsp";

        response.setContentType("text/html");
        LoginService service = new LoginService();
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