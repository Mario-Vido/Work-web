package com.example.web.Servlets.ServletsForCypherOperations;

import com.example.web.Service.ServerService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;


@WebServlet(name= "CreatingCypher", urlPatterns = "/creatingcypher")
public class CreatingCypherServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServerService service = new ServerService();
        service.creatingCyphers(request,response);
    }
}
