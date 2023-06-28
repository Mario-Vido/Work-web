<%@ page import="com.example.web.Objects.DatabaseValues" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<% if (session==null || session.getAttribute("userId")==null){
  response.sendRedirect("login.jsp");
  return;
}%>

<html>
<head>
  <title>Logs of encryption</title>
</head>
<body>
<% response.setHeader("Cache-Control","no-cache ,no-store , must-revalidate"); %>
<table style="border-collapse: collapse;">
  <tr style="border: 1px solid black;">
    <th style="border: 1px solid black; padding: 5px;">ID</th>
    <th style="border: 1px solid black; padding: 5px;">Input</th>
    <th style="border: 1px solid black; padding: 5px;">Output</th>
    <th style="border: 1px solid black; padding: 5px;">CypherInterface</th>
    <th style="border: 1px solid black; padding: 5px;">Timestamp</th>
    <th style="border: 1px solid black; padding: 5px;">IdOfUser</th>
  </tr>
  <% List<DatabaseValues> databaseValuesList = (List<DatabaseValues>) request.getAttribute("databaseValuesList");
    for (DatabaseValues values : databaseValuesList) { %>
  <tr>
    <td style="border: 1px solid black; padding: 5px;"><%= values.getId() %></td>
    <td style="border: 1px solid black; padding: 5px;"><%= values.getInput() %></td>
    <td style="border: 1px solid black; padding: 5px;"><%= values.getOutput() %></td>
    <td style="border: 1px solid black; padding: 5px;"><%= values.getCypher() %></td>
    <td style="border: 1px solid black; padding: 5px;"><%= values.getTimestamp() %></td>
    <td style="border: 1px solid black; padding: 5px;"><%= values.getIdOfUser() %></td>
  </tr>
  <% } %>
  <tr>
    <td colspan="6" style="text-align: center;">
      <form action="table" method="get">
        <input type="submit" value="View my cifers">
      </form>
    </td>
  </tr>
  <tr>
    <td colspan="6" style="text-align: center;">
      <form action="logout" method="post">
        <input type="submit" value="Logout">
      </form>
    </td>
  </tr>
</table>
</body>
</html>
