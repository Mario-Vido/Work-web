package com.example.web;

import java.io.*;
import java.sql.Connection;

import com.example.web.DataBase.DataBase;
import com.example.web.Decryption.Decryption;
import com.example.web.Decryption.DecryptionType1;
import com.example.web.Decryption.DecryptionType2;
import com.example.web.Encryption.Encryption;
import com.example.web.Encryption.EncryptionType1;
import com.example.web.Encryption.EncryptionType2;
import com.example.web.Service.DecryptionService;
import com.example.web.Service.EncryptionService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name= "CypherServlet", urlPatterns = "/cypher") //localhost:8080/cypher
public class CypherServlet extends HttpServlet {
    String responseFromCypher = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext context = getServletContext();
        Connection conn = (Connection) context.getAttribute("databaseConnection");
        String inputFromUser = request.getParameter("param1");
        String typeOfCypher = request.getParameter("param2");
        String helper = request.getParameter("param3");
        int convertedHelper = Integer.parseInt(helper);

        response.setContentType("text/plain");

        try (PrintWriter out = response.getWriter()) {
            if (convertedHelper == 0) {
                Encryption encryption1 = new EncryptionType1();
                Encryption encryption2 = new EncryptionType2();
                EncryptionService service = new EncryptionService(encryption1, encryption2);

                switch (typeOfCypher) {
                    case "Encryption type 1":
                        responseFromCypher = service.callEncryptionType1(inputFromUser);
                        break;
                    case "Encryption type 2":
                        responseFromCypher = service.callEncryptionType2(inputFromUser);
                        break;
                }

                DataBase dataBase = new DataBase();
                dataBase.insertMassage(inputFromUser, responseFromCypher, typeOfCypher, conn);
            } else if (convertedHelper == 1 && responseFromCypher != null && !responseFromCypher.equals(inputFromUser)) {
                Decryption decryption1 = new DecryptionType1();
                Decryption decryption2 = new DecryptionType2();
                DecryptionService decryptionService = new DecryptionService(decryption1, decryption2);

                switch (typeOfCypher) {
                    case "Encryption type 1":
                        responseFromCypher = decryptionService.callDecryptionType1(responseFromCypher);
                        break;
                    case "Encryption type 2":
                        responseFromCypher = decryptionService.callDecryptionType2(responseFromCypher);
                        break;
                }
            }

            out.println(responseFromCypher);
        }
    }
}

