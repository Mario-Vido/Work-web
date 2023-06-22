package com.example.web.Servlets.ServletsForAccesOperation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;



@WebServlet(name = "logoutServlet", value = "/logout")

public class LogoutServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        session.removeAttribute("login");

        session.removeAttribute("id");

        session.removeAttribute("role");

        session.invalidate();

        response.sendRedirect("/");

    }

}
