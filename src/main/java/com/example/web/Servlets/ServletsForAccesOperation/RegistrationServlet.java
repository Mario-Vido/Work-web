package com.example.web.Servlets.ServletsForAccesOperation;

import com.example.web.Service.ServerService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;


@WebServlet(name = "RegistrationServlet", value = "/registration-servlet")
public class RegistrationServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("registration.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        ServletContext context = getServletContext();
        Connection connection = (Connection) getServletContext().getAttribute("databaseConnection");
        ServerService serverService = (ServerService) context.getAttribute("serverService");
        serverService.registration(req,resp,connection,context);
    }
}
