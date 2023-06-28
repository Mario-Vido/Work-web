package com.example.web.Service;

import com.example.web.DataBase.DataBase;
import com.example.web.Interface.LoginInterface;
import com.example.web.Objects.Cypher;
import com.example.web.Objects.DatabaseValues;
import com.example.web.Objects.User;
import jakarta.servlet.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;


public class ServerService implements LoginInterface {
    UserService userService = new UserService();

    @Override
    public void createTable(Connection conn, HttpServletRequest request, HttpServletResponse response,String jsp)
            throws ServletException, IOException {
        List<DatabaseValues> databaseValuesList = new ArrayList<>();

        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cypherauditlog");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String input = resultSet.getString("input");
                String output = resultSet.getString("output");
                String cypher = resultSet.getString("cypher");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                Integer idOfUser = resultSet.getInt("idofuser");

                DatabaseValues databaseValues = new DatabaseValues(id, input, output, cypher, timestamp, idOfUser);
                databaseValuesList.add(databaseValues);
            }
        } catch (SQLException e) {
            throw new ServletException("Error retrieving data from the database", e);
        }

        request.setAttribute("databaseValuesList", databaseValuesList);
        RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
        dispatcher.forward(request, response);
    }


    public void encipher(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws IOException {
        HashMap<String, Cypher> cypherMap = (HashMap<String, Cypher>) request.getSession().getAttribute("HashMapOfCyphers");
        Connection databaseConnection = (Connection) context.getAttribute("databaseConnection");
        Connection connectionToUserDataBase = (Connection) context.getAttribute("userDataBase");

        String inputFromUser = request.getParameter("encodedValue");
        String typeOfCypher = request.getParameter("typeOfCypher");
        String username = request.getParameter("username");

        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            CypherService service = new CypherService();
            Cypher matchingCypher = cypherMap.get(typeOfCypher);

            String responseFromCypher;
            if (matchingCypher != null) {
                responseFromCypher = service.performEncryption(matchingCypher, inputFromUser);
            } else {
                responseFromCypher = "Invalid type of cypher";
            }

            DataBase dataBase = new DataBase();
            int idOfUser = userService.getUserIdByUsername(connectionToUserDataBase, username);
            dataBase.insertMassage(inputFromUser, responseFromCypher, typeOfCypher, databaseConnection, idOfUser);
            out.println(responseFromCypher);
        }
    }

    public void loginFromClient(HttpServletRequest request, HttpServletResponse response, ServletContext context)
            throws IOException {
        Connection connectionToUsedDatabase = (Connection) context.getAttribute("userDataBase");

        String username = request.getParameter("login");
        String password = request.getParameter("password");

        response.setContentType("text/plain");

        try (PrintWriter out = response.getWriter()) {
            boolean isAuthenticated = userService.authenticateUser(connectionToUsedDatabase, username, password);
            out.println(isAuthenticated);
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        session.removeAttribute("login");

        session.removeAttribute("userId");

        session.removeAttribute("role");

        session.removeAttribute("databaseValuesList");

        session.invalidate();

        response.sendRedirect("/");
    }

    public void registration(HttpServletRequest req, HttpServletResponse resp, Connection connection,ServletContext context){
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeat-password");

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);

        try {
            if(repeatPassword.equals(password)){
                if (userService.authenticateUser(connection, login, password)) {
                    req.setAttribute("error","User already exists");
                    context.getRequestDispatcher("/registration.jsp").forward(req,resp);

                } else {
                    if(userService.registerUser(user, connection).equals("Success")){
                        resp.sendRedirect("login.jsp");
                    }else{
                        req.setAttribute("errorForException","Registration not successful");
                        context.getRequestDispatcher("/registration.jsp").forward(req,resp);
                    }
                }
            }else {
                req.setAttribute("errorForPassword","Passwords are not matching");
                context.getRequestDispatcher("/registration.jsp").forward(req,resp);
            }
        } catch (SQLException | IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public void loginFromServer(HttpServletRequest request, HttpServletResponse response, Connection connectionToUsedDatabase,ServletContext context) throws IOException, ServletException {
        String username = request.getParameter("login");
        String password = request.getParameter("password");

        boolean isAuthenticated = userService.authenticateUser(connectionToUsedDatabase, username, password);

        if (isAuthenticated) {
            int userId = userService.getUserIdByUsername(connectionToUsedDatabase, username);

            request.getSession().setAttribute("userId", userId);
            List<String> userRole = userService.getUserRoleById(userId, connectionToUsedDatabase);

            request.getSession().setAttribute("role", userRole);
            response.sendRedirect("/table");
//            context.getRequestDispatcher("/table").forward(request,response);

        } else {
            request.setAttribute("errorForLogin","Wrong username or password");
            context.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }

    public void creatingCyphers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CypherService service = new CypherService();
        List<Cypher> cypherList = service.createCyphers(2);
        Map<String, Cypher> cypherMap = service.createCypherMap(cypherList);
        String namesString = service.generateStringFromKeys(cypherMap);

        request.getSession().setAttribute("HashMapOfCyphers", cypherMap);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println(namesString);
        }
    }

    public void decypher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String valueAfterCypher = request.getParameter("encodedValue");
        String typeOfCypher = request.getParameter("typeOfCypher");
        HashMap<String, Cypher> cypherMap = (HashMap<String, Cypher>) request.getSession().getAttribute("HashMapOfCyphers");

        response.setContentType("text/plain");

        try (PrintWriter out = response.getWriter()) {
            CypherService service = new CypherService();
            Cypher matchingCypher = cypherMap.get(typeOfCypher);

            String responseFromCypher;
            if (matchingCypher != null) {
                responseFromCypher = service.performDecryption(matchingCypher, valueAfterCypher);
            } else {
                responseFromCypher = "Invalid type of cypher";
            }

            out.println(responseFromCypher);
        }
    }
}
