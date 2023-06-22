package com.example.web.Servlets.ServletsForAccesOperation;

import com.example.web.Service.LoginService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

// Get the session ID
        String sessionID = session.getId();

// Set the session ID as a cookie in the response
        response.addCookie(new Cookie("JSESSIONID", sessionID));
        System.out.println(sessionID);

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
