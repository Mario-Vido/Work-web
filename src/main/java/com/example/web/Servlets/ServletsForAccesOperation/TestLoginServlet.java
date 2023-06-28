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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        ServerService service = new ServerService();
        Connection connectionToUsedDatabase = (Connection) context.getAttribute("userDataBase");

        service.loginFromServer(request,response,connectionToUsedDatabase,context);
    }
}
