package com.example.web.Servlets;

import java.io.*;
import java.sql.Connection;
import java.util.HashMap;

import com.example.web.DataBase.DataBase;
import com.example.web.Objects.Cypher;
import com.example.web.Service.CypherService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;



@WebServlet(name= "CypherServlet", urlPatterns = "/cypher")
public class CypherServlet extends HttpServlet {
    String responseFromCypher = null;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext context = getServletContext();
        Connection conn = (Connection) context.getAttribute("databaseConnection");
        String inputFromUser = request.getParameter("param1");
        String typeOfCypher = request.getParameter("param2");

        response.setContentType("text/plain");


        try (PrintWriter out = response.getWriter()) {
            CypherService service = new CypherService();
            switch (typeOfCypher) {
                case "Cypher 0":
                    responseFromCypher = service.callEncryptionType1(inputFromUser);
                    break;
                case "Cypher 1":
                    responseFromCypher = service.callEncryptionType2(inputFromUser);
                    break;
            }

            DataBase dataBase = new DataBase();
            dataBase.insertMassage(inputFromUser, responseFromCypher, typeOfCypher, conn);
            out.println(responseFromCypher);
        }
    }
}


