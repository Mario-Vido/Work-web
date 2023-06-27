package com.example.web.Servlets.ServletsForAccesOperation;

import com.example.web.Service.LoginService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "logoutServlet", value = "/logout")

public class LogoutServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginService service = new LoginService();
        service.Logout(request,response);
//        HttpSession session = request.getSession();
//
//        session.removeAttribute("login");
//
//        session.removeAttribute("userId");
//
//        session.removeAttribute("role");
//
//        session.removeAttribute("databaseValuesList");
//
//        session.invalidate();
//
//        response.sendRedirect("/");

    }

}
