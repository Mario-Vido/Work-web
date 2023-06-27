package com.example.web.Servlets.ServletsForCypherOperations;

import com.example.web.Objects.Cypher;
import com.example.web.Service.CypherService;
import com.example.web.Service.LoginService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


@WebServlet(name= "CreatingCypher", urlPatterns = "/creatingcypher")
public class CreatingCypherServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginService service = new LoginService();
        service.CreatingCyphers(request,response);
//        CypherService service = new CypherService();
//
//        List<Cypher> cypherList = service.createCyphers(2);
//        Map<String, Cypher> cypherMap = service.createCypherMap(cypherList);
//        String namesString = service.generateStringFromKeys(cypherMap);
//        request.getSession().setAttribute("HashMapOfCyphers",cypherMap);
//        response.setContentType("text/plain");
//        response.setCharacterEncoding("UTF-8");
//
//        try (PrintWriter out = response.getWriter()) {
//            out.println(namesString);
//        }
    }
}
