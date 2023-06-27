package com.example.web.Servlets.ServletsForCypherOperations;

import java.io.*;
import com.example.web.Service.ServerService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name= "CypherServlet", urlPatterns = "/cypher")
public class CypherServlet extends HttpServlet {
    String responseFromCypher = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServerService serverService = new ServerService();
        ServletContext context = getServletContext();
        serverService.encipher(request,response,context);
    }

}



