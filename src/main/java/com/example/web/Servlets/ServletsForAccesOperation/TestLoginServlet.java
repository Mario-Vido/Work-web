package com.example.web.Servlets.ServletsForAccesOperation;

import com.example.web.Service.ServerService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet (name = "LoginWebServlet", value = "/test-login")
public class TestLoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        ServletContext context = getServletContext();
        ServerService serverService = (ServerService) context.getAttribute("serverService");
        Connection connectionToUsedDatabase = (Connection) context.getAttribute("databaseConnection");
        serverService.loginFromServer(request,response,connectionToUsedDatabase,context);
    }
}
