package com.example.web;

import java.io.*;

import com.example.web.DataBase.DataBase;
import com.example.web.Encryption.EncryptionType1;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name= "CypherServlet", urlPatterns = "/cypher") //localhost:8080/cypher
public class CypherServlet extends HttpServlet {

    protected  void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

    }
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

        EncryptionType1 encryptionType1= new EncryptionType1();
        DataBase dataBase = new DataBase();

        String inputFromUser = request.getParameter("param1");
        String typeOfCypher = request.getParameter("param2");
        response.setContentType("text/plain");
        String responseFromCypher=encryptionType1.performEncryption(inputFromUser);
        dataBase.insertMassage(inputFromUser,responseFromCypher,typeOfCypher);
        PrintWriter out = response.getWriter();
        out.println(responseFromCypher);
    }
}
