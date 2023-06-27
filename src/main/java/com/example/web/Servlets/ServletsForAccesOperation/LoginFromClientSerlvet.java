package com.example.web.Servlets.ServletsForAccesOperation;

import com.example.web.Service.LoginService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(value = "/login-from-client")
public class LoginFromClientSerlvet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean isAuthenticated;
        LoginService service = new LoginService();
        ServletContext context = getServletContext();
        Connection connectionToUsedDatabase = (Connection) context.getAttribute("userDataBase");

        String username = request.getParameter("login");
        String password = request.getParameter("password");

        response.setContentType("text/plain");

        try (PrintWriter out = response.getWriter()) {
            isAuthenticated = service.authenticateUser(connectionToUsedDatabase, username, password);
            if (isAuthenticated) {
                out.println("true");
            } else {
                out.println("false");
            }
        }
    }

}
