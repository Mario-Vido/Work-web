<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
</head>
<body>
<h1>Login</h1>
<form action="login-servlet" method="post">
  <label for="login">Username:</label>
  <input type="text" id="login" name="login" required><br><br>

  <label for="password">Password:</label>
  <input type="password" id="password" name="password" required><br><br>

  <input type="submit" value="Login">
</form>
<form action="registration.jsp">
  <input type="submit" value="Register">
</form>
<% if (request.getParameter("error") != null) { %>
<p style="color: red;">Invalid username or password. Please try again.</p>
<% } %>
</body>
</html>
