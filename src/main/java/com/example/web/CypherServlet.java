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
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name= "CypherServlet", urlPatterns = "/cypher") //localhost:8080/cypher
public class CypherServlet extends HttpServlet {
    private final Encryption encryptionType1 = new EncryptionType1();
    private final Encryption encryptionType2 = new EncryptionType2();
    private final Decryption decryptionType1 = new DecryptionType1();
    private final Decryption decryptionType2 = new DecryptionType2();
    private final DataBase dataBase = new DataBase();
    String responseFromCypher = null;

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        ServletContext context = getServletContext();
        Connection conn = (Connection) context.getAttribute("databaseConnection");
        String inputFromUser = request.getParameter("param1");
        String typeOfCypher = request.getParameter("param2");
        String helper = request.getParameter("param3");
        int convertedHelper=Integer.parseInt(helper);
        response.setContentType("text/plain");
        if (convertedHelper==0) {
            if (typeOfCypher.equals("Encryption type 1")) {
                responseFromCypher = encryptionType1.performEncryption(inputFromUser);
            } else if (typeOfCypher.equals("Encryption type 2")) {
                responseFromCypher = encryptionType2.performEncryption(inputFromUser);
            }
            dataBase.insertMassage(inputFromUser, responseFromCypher, typeOfCypher,conn);
        } else if (convertedHelper==1 && responseFromCypher != null && !responseFromCypher.equals(inputFromUser)) {
            if (typeOfCypher.equals("Encryption type 1")) {
                responseFromCypher = decryptionType1.performDecryption(responseFromCypher);
            } else if (typeOfCypher.equals("Encryption type 2")) {
                responseFromCypher = decryptionType2.performDecryption(responseFromCypher);
            }
        }
        try (PrintWriter out = response.getWriter()) {
            out.println(responseFromCypher);
        }
    }
}
