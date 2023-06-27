package com.example.web.Servlets.ServletsForCypherOperations;

import com.example.web.Service.ServerService;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name= "DecypherServlet", urlPatterns = "/decypher")
public class DecypherServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServerService service = new ServerService();
        service.decypher(request,response);
    }
}

