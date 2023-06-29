package com.example.web.Servlets.ServletsForCypherOperations;

import com.example.web.Service.ServerService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;


@WebServlet(name= "CreatingCypher", urlPatterns = "/creatingcypher")
public class CreatingCypherServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        ServletContext context = getServletContext();
        ServerService serverService = (ServerService) context.getAttribute("serverService");
        serverService.creatingCyphers(request,response);
    }
}
