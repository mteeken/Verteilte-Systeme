<%-- 
    Document   : register
    Created on : 04.02.2014, 18:27:36
    Author     : mteeken
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Terminkalender</title>
    </head>
    <body>
        <h1>Bitte geben sie Ihren Namen und Ihr Passwort ein: </h1>
         <form action="register.jsp" method="GET">
            <h2>Bitte geben Sie Ihren Namen ein:</h2>
            <input type="text" name="name" />
            <h2>Bitte geben Sie Ihr Passwort ein:</h2>
            <input type="password" name="password" />
            <BR /><BR />
            <input type="submit" value="Eingabe" />
        </form>
        <a href="login.jsp">Zum Login</a>
    </body>
</html>