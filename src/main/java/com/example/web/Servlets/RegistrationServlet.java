package com.example.web.Servlets;

import com.example.web.DataBase.UserRegistration;
import com.example.web.Objects.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
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

    private final UserRegistration userRegistration = new UserRegistration();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("registration.jsp");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        Connection conn = (Connection) context.getAttribute("userDataBase");


        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String role = "User";

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);

        try {
            userRegistration.registerUser(user,conn);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
        dispatcher.forward(req,resp);
    }
}