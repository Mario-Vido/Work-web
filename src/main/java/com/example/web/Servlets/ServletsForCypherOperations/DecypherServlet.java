package com.example.web.Servlets.ServletsForCypherOperations;

import com.example.web.Objects.Cypher;
import com.example.web.Service.CypherService;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


@WebServlet(name= "DecypherServlet", urlPatterns = "/decypher")
public class DecypherServlet extends HttpServlet {
    String responseFromCypher = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String valueAfterCypher = request.getParameter("param1");
        String typeOfCypher = request.getParameter("param2");
        HashMap<String,Cypher> cypherMap = (HashMap<String, Cypher>) request.getSession().getAttribute("HashMapOfCyphers");

        response.setContentType("text/plain");

        try (PrintWriter out = response.getWriter()) {

            CypherService service = new CypherService();
            Cypher matchingCypher = cypherMap.get(typeOfCypher);

            if (matchingCypher != null) {
                responseFromCypher = service.performDecryption(matchingCypher, valueAfterCypher);
            } else {
                responseFromCypher = "Invalid type of cypher";
            }

            out.println(responseFromCypher);
        }
    }
}
