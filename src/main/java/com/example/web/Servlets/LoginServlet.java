package com.example.web.Servlets;

import com.example.web.Service.LoginService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginService service = new LoginService();
        ServletContext context = getServletContext();
        Connection connectionToUsedDatabase = (Connection) context.getAttribute("userDataBase");

        String username = request.getParameter("login");
        String password = request.getParameter("password");

        boolean isAuthenticated = service.authenticateUser(connectionToUsedDatabase, username, password);

        if (isAuthenticated) {
            int userId = service.getUserIdByUsername(connectionToUsedDatabase, username);

            request.getSession().setAttribute("userId", userId);

            String userRole = service.getUserRoleById(userId,connectionToUsedDatabase);

            request.getSession().setAttribute("role",userRole);

            response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("login.jsp?error=1");
        }
    }
}
