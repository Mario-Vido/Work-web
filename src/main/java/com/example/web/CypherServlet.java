package com.example.web;

import java.io.*;

import com.example.web.DataBase.DataBase;
import com.example.web.Decryption.DecryptionType1;
import com.example.web.Decryption.DecryptionType2;
import com.example.web.Encryption.EncryptionType1;
import com.example.web.Encryption.EncryptionType2;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name= "CypherServlet", urlPatterns = "/cypher") //localhost:8080/cypher
public class CypherServlet extends HttpServlet {

    String responseFromCypher = null;
    protected  void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

    }
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{



        EncryptionType1 encryptionType1= new EncryptionType1();
        EncryptionType2 encryptionType2 = new EncryptionType2();
        DecryptionType1 decryptionType1 =new DecryptionType1();
        DecryptionType2 decryptionType2 =new DecryptionType2();
        DataBase dataBase = new DataBase();

        String inputFromUser = request.getParameter("param1");
        String typeOfCypher = request.getParameter("param2");
        String helper = request.getParameter("param3");
        response.setContentType("text/plain");

        if(typeOfCypher.equals("Encryption type 1") && helper.equals("Encryption")) {
            responseFromCypher = encryptionType1.performEncryption(inputFromUser);
            dataBase.insertMassage(inputFromUser,responseFromCypher,typeOfCypher);
        }
        else if(typeOfCypher.equals("Encryption type 2") && helper.equals("Encryption")){
            responseFromCypher=encryptionType2.performEncryption(inputFromUser);
            dataBase.insertMassage(inputFromUser,responseFromCypher,typeOfCypher);
        }

        if(helper.equals("Decryption") && (responseFromCypher!=null) && !responseFromCypher.equals(inputFromUser)){
            if(typeOfCypher.equals("Encryption type 1")) {
                responseFromCypher=decryptionType1.performDecryption(responseFromCypher);
            }
            else if(typeOfCypher.equals("Encryption type 2")){
                responseFromCypher=decryptionType2.performDecryption(responseFromCypher);
            }

        }
        PrintWriter out = response.getWriter();
        out.println(responseFromCypher);
    }
}
