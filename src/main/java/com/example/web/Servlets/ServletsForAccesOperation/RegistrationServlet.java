package com.example.web.Servlets.ServletsForAccesOperation;

import com.example.web.Objects.User;
import com.example.web.Service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "RegistrationServlet", value = "/registration-servlet")
public class RegistrationServlet extends HttpServlet {

    private LoginService loginService;

    @Override
    public void init() throws ServletException {
        super.init();
        loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("registration.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Connection connection = (Connection) getServletContext().getAttribute("userDataBase");

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String role = "User";

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);

        try {
            if (loginService.authenticateUser(connection, login, password)) {
                resp.sendRedirect("registration.jsp?error=1");
            } else {
                loginService.registerUser(user, connection);
                resp.sendRedirect("login.jsp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
