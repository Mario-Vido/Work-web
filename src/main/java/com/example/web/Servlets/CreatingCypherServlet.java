package com.example.web.Servlets;

import com.example.web.Objects.Cypher;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;

@WebServlet(name= "CreatingCypher", urlPatterns = "/creatingcypher")
public class CreatingCypherServlet extends HttpServlet {

    Cypher[] cyphers = new Cypher[2];
    HashMap<String, Cypher> cypherMap = new HashMap<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (int i=0;i< cyphers.length;i++){
            String name = "Cypher " + i;
            int key = i;

            cyphers[i] = new Cypher(key, name);
            cypherMap.put(name, cyphers[i]);
        }
//        Gson gson = new Gson();
//        String json = gson.toJson(cyphers);
//
//        response.setContentType("application/json");
//        response.setContentLength(json.getBytes(StandardCharsets.UTF_8).length);
//
//        OutputStream outputStream = response.getOutputStream();
//        outputStream.write(json.getBytes(StandardCharsets.UTF_8));
//        outputStream.flush();
//        outputStream.close();
        Set<String> names = cypherMap.keySet();
        String namesString = String.join(",", names);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println(namesString);
        }
        System.out.println(namesString);
    }
}
