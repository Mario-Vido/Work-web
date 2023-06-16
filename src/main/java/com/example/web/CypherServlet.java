package com.example.web;

import java.io.*;

import com.example.web.DataBase.DataBase;
import com.example.web.Encryption.EncryptionType1;
import com.example.web.Encryption.EncryptionType2;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name= "CypherServlet", urlPatterns = "/cypher") //localhost:8080/cypher
public class CypherServlet extends HttpServlet {

    protected  void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

    }
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

        String responseFromCypher = null;

        EncryptionType1 encryptionType1= new EncryptionType1();
        EncryptionType2 encryptionType2 = new EncryptionType2();
        DataBase dataBase = new DataBase();

        String inputFromUser = request.getParameter("param1");
        String typeOfCypher = request.getParameter("param2");
        response.setContentType("text/plain");
        if(typeOfCypher.equals("Encryption type 1")) {
           responseFromCypher = encryptionType1.performEncryption(inputFromUser);
        }
        else if(typeOfCypher.equals("Encryption type 2")){
            responseFromCypher=encryptionType2.performEncryption(inputFromUser);
        }
        dataBase.insertMassage(inputFromUser,responseFromCypher,typeOfCypher);
        PrintWriter out = response.getWriter();
        out.println(responseFromCypher);
    }
}
