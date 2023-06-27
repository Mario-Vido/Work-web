package com.example.web.Servlets.ServletsForAccesOperation;

import com.example.web.Service.LoginService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

@WebServlet (name = "LoginWebServlet", value = "/test-login")
public class TestLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginService service = new LoginService();
        Connection connectionToUsedDatabase = getDatabaseConnection();

        service.LoginFromServer(request,response,connectionToUsedDatabase);

//        String username = request.getParameter("login");
//        String password = request.getParameter("password");
//
//        boolean isAuthenticated = service.authenticateUser(connectionToUsedDatabase, username, password);
//
//        if (isAuthenticated) {
//            int userId = service.getUserIdByUsername(connectionToUsedDatabase, username);
//
//            request.getSession().setAttribute("userId", userId);
//            String userRole = service.getUserRoleById(userId, connectionToUsedDatabase);
//
//            request.getSession().setAttribute("role", userRole);
//            response.sendRedirect("index.jsp");
//
//        } else {
//            response.sendRedirect("login.jsp?error=1");
//        }
    }

    private Connection getDatabaseConnection() {
        ServletContext context = getServletContext();
        return (Connection) context.getAttribute("userDataBase");
    }
}
