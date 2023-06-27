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

        service.loginFromServer(request,response,connectionToUsedDatabase);
    }

    private Connection getDatabaseConnection() {
        ServletContext context = getServletContext();
        return (Connection) context.getAttribute("userDataBase");
    }
}
