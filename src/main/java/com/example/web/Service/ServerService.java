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

    @Override
    public boolean authenticateUser(Connection connection, String login, String password) {
        String query = "SELECT 1 FROM users WHERE login = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public int getUserIdByUsername(Connection connection, String username) {
        String query = "SELECT id FROM users WHERE login = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public String checkUserAuthorization(HttpServletRequest request) {
        Optional<String> roleOptional = Optional.ofNullable((String) request.getSession().getAttribute("role"));

        return roleOptional.map(role -> {
            if ("Admin".equals(role)) {
                return "Admin";
            } else if ("User".equals(role)) {
                return "User";
            } else {
                return "User is not authorized";
            }
        }).orElse("Role not found");
    }


    @Override
    public String getUserRoleById(Integer userId, Connection connection) {
        String query = "SELECT role_id FROM user_roles WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int roleId = resultSet.getInt("role_id");
                    if (roleId == 1) {
                        return "Admin";
                    } else if (roleId == 2) {
                        return "User";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Unknown";
    }


    @Override
    public void registerUser(User user, Connection connection) throws SQLException {
        String INSERT_USERS_SQL = "INSERT INTO users (login, password) VALUES (?, ?);";
        String INSERT_USER_ROLE_SQL = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?);";

        try (PreparedStatement userStatement = connection.prepareStatement(INSERT_USERS_SQL, Statement.RETURN_GENERATED_KEYS)) {
            userStatement.setString(1, user.getLogin());
            userStatement.setString(2, user.getPassword());
            userStatement.executeUpdate();

            try (ResultSet generatedKeys = userStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);

                    try (PreparedStatement roleStatement = connection.prepareStatement(INSERT_USER_ROLE_SQL)) {
                        roleStatement.setInt(1, userId);
                        roleStatement.setInt(2, 2);
                        roleStatement.executeUpdate();
                    }
                }
            }
        }
    }



    @Override
    public void createTable(Connection conn, HttpServletRequest request, HttpServletResponse response, String jsp)
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
            int idOfUser = getUserIdByUsername(connectionToUserDataBase, username);
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
            boolean isAuthenticated = authenticateUser(connectionToUsedDatabase, username, password);
            out.println(isAuthenticated);
        } catch (IOException e) {
            // Handle IO exception
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

    public void registration(HttpServletRequest req, HttpServletResponse resp, Connection connection){
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);

        try {
            if (authenticateUser(connection, login, password)) {
                resp.sendRedirect("registration.jsp?error=1");
            } else {
                registerUser(user, connection);
                resp.sendRedirect("login.jsp");
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loginFromServer(HttpServletRequest request, HttpServletResponse response, Connection connectionToUsedDatabase) throws IOException {
        String username = request.getParameter("login");
        String password = request.getParameter("password");

        boolean isAuthenticated = authenticateUser(connectionToUsedDatabase, username, password);

        if (isAuthenticated) {
            int userId = getUserIdByUsername(connectionToUsedDatabase, username);

            request.getSession().setAttribute("userId", userId);
            String userRole = getUserRoleById(userId, connectionToUsedDatabase);

            request.getSession().setAttribute("role", userRole);
            response.sendRedirect("index.jsp");

        } else {
            response.sendRedirect("login.jsp?error=1");
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
