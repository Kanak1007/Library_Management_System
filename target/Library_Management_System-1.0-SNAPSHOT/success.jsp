<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success</title>
</head>
<body>
    <h2>Registration Successful!</h2>
       <p>Welcome, <%= session.getAttribute("adminname") %></p>
    <a href="index.html">Go Back</a>
</body>
</html>
