package com.example.web.Servlets.ServletsForAccesOperation;

import com.example.web.Service.ServerService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "logoutServlet", value = "/logout")

public class LogoutServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext context = getServletContext();
        ServerService serverService = (ServerService) context.getAttribute("serverService");
        serverService.logout(request,response);
    }

}
