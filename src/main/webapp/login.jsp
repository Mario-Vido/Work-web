<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
</head>
<body>
<h1>Login</h1>
<form action="test-login" method="get">
  <label for="login">Username:</label>
  <input type="text" id="login" name="login" required><br><br>

  <label for="password">Password:</label>
  <input type="password" id="password" name="password" required><br><br>

  <input type="submit" value="Login">
</form>
<form action="registration.jsp">
  <input type="submit" value="Register">
</form>
<p style="color: red;">${errorForLogin}</p>

</html>
