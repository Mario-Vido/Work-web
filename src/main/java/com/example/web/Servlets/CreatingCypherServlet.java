package com.example.web.Servlets;

import com.example.web.Objects.Cypher;
import com.example.web.Service.CypherService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


@WebServlet(name= "CreatingCypher", urlPatterns = "/creatingcypher")
public class CreatingCypherServlet extends HttpServlet {

    Cypher[] cyphers = new Cypher[2];
    HashMap<String, Cypher> cypherMap = new HashMap<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CypherService service = new CypherService();
        service.creteCyphers(cyphers);

        String namesString =service.createStringFromKeys(cypherMap,cyphers);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println(namesString);
        }
        System.out.println(namesString);
    }
}
