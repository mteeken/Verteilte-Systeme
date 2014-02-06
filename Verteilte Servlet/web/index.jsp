<%-- 
    Document   : index
    Created on : 04.02.2014, 18:19:57
    Author     : mteeken
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="resources/custom.css">
        <title>Terminkalender</title>
    </head>
    <body>
        <div class="wrapper">
            <div class="spacer">
                <h1>Terminkalender</h1>
                <BR />
                <h2>Zum Verwalten der Termine loggen sie sich bitte ein</h2>
                <BR />
                 <form action="login.jsp" method="POST">
                    <label for="name">Namen:</label>
                    <input type="text" name="name" id="name"/>
                    <BR /><BR />
                    <label for="password">Passwort:</label>
                    <input type="password" name="password" id="password"/>
                    <BR /><BR />
                    <input type="submit" value="Eingabe" />
                </form>
                 <BR /><BR />
                <a href="register.jsp">Registrieren</a>
                 <BR /><BR />
            </div>
        </div>
    </body>
</html>