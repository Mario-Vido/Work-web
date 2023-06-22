package com.example.web.Servlets.ServletsForAccesOperation;

import com.example.web.Service.LoginService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet (name = "LoginWebServlet", value = "/test-login")
public class TestLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        String sessionID = session.getId();

        response.addCookie(new Cookie("JSESSIONID", sessionID));

        LoginService service = new LoginService();
        Connection connectionToUsedDatabase = getDatabaseConnection();

        String username = request.getParameter("login");
        String password = request.getParameter("password");

        boolean isAuthenticated = service.authenticateUser(connectionToUsedDatabase, username, password);

        if (isAuthenticated) {
            int userId = service.getUserIdByUsername(connectionToUsedDatabase, username);

            request.getSession().setAttribute("userId", userId);
            String userRole = service.getUserRoleById(userId, connectionToUsedDatabase);

            request.getSession().setAttribute("role", userRole);
            response.sendRedirect("index.jsp");

        } else {
            response.sendRedirect("login.jsp?error=1");
        }
    }

    private Connection getDatabaseConnection() {
        ServletContext context = getServletContext();
        return (Connection) context.getAttribute("userDataBase");
    }
}
