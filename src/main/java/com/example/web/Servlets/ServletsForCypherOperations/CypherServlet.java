package com.example.web.Servlets.ServletsForCypherOperations;

import java.io.*;
import java.sql.Connection;
import java.util.HashMap;
import com.example.web.DataBase.DataBase;
import com.example.web.Objects.Cypher;
import com.example.web.Service.CypherService;
import com.example.web.Service.LoginService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;



@WebServlet(name= "CypherServlet", urlPatterns = "/cypher")
public class CypherServlet extends HttpServlet {
    String responseFromCypher = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginService loginService = new LoginService();

        HashMap<String,Cypher> cypherMap = (HashMap<String, Cypher>) request.getSession().getAttribute("HashMapOfCyphers");
        ServletContext context = getServletContext();

        Connection databaseConnection = (Connection) context.getAttribute("databaseConnection");
        Connection connectionToUserDataBase = (Connection) context.getAttribute("userDataBase");

        String inputFromUser = request.getParameter("param1");
        String typeOfCypher = request.getParameter("param2");
        String username = request.getParameter("param3");

        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            CypherService service = new CypherService();
            Cypher matchingCypher = cypherMap.get(typeOfCypher);

            if (matchingCypher != null) {
                responseFromCypher = service.performEncryption(matchingCypher, inputFromUser);
            } else {
                responseFromCypher = "Invalid type of cypher";
            }

            DataBase dataBase = new DataBase();
            int idOfUser = loginService.getUserIdByUsername(connectionToUserDataBase, username);
            dataBase.insertMassage(inputFromUser, responseFromCypher, typeOfCypher, databaseConnection, idOfUser);
            out.println(responseFromCypher);
        }
    }
}



