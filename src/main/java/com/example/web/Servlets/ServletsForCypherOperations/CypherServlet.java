package com.example.web.Servlets.ServletsForCypherOperations;

import java.io.*;
import com.example.web.Service.ServerService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name= "CypherServlet", urlPatterns = "/cypher")
public class CypherServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        ServletContext context = getServletContext();
        ServerService serverService = (ServerService) context.getAttribute("serverService");
        serverService.encipher(request,response,context);
    }

}



