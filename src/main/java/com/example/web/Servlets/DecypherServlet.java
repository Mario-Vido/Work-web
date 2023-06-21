package com.example.web.Servlets;

import com.example.web.Service.CypherService;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name= "DecypherServlet", urlPatterns = "/decypher")
public class DecypherServlet extends HttpServlet {
    String responseFromCypher = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String valueAfterCypher = request.getParameter("param1");
        String typeOfCypher = request.getParameter("param2");

        response.setContentType("text/plain");

        try (PrintWriter out = response.getWriter()) {
            CypherService service = new CypherService();
            switch (typeOfCypher) {
                case "Cypher 0":
                    responseFromCypher = service.callDecryptionType1(valueAfterCypher);
                    break;
                case "Cypher 1":
                    responseFromCypher = service.callDecryptionType2(valueAfterCypher);
                    break;
            }
            out.println(responseFromCypher);
        }
    }
}
