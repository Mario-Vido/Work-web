package com.example.web.Servlets.ServletsForCypherOperations;

import java.io.*;
import com.example.web.Service.LoginService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name= "CypherServlet", urlPatterns = "/cypher")
public class CypherServlet extends HttpServlet {
    String responseFromCypher = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginService loginService = new LoginService();
        ServletContext context = getServletContext();
        loginService.Encypher(request,response,context);
    }

}



