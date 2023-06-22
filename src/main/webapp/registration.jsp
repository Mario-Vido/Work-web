<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
</head>
<body>
<div align="center">
    <h1>Employee Register Form</h1>
    <form action="<%= request.getContextPath() %>/registration-servlet" method="post">
        <table style="with: 80%">
            <tr>
                <td>Login name</td>
                <td><input type="text" name="login" /></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="text" name="password" /></td>
            </tr>
        </table>
        <input type="submit" value="Submit" />
    </form>
    <% if (request.getParameter("error") != null) { %>
    <p style="color: red;">Username already used</p>
    <% } %>
</div>
</body>
</html>
