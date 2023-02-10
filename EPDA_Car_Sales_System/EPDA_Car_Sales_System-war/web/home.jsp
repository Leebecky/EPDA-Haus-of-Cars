<%-- 
    Document   : home
    Created on : 02-Feb-2023, 17:10:25
    Author     : leebe
--%>

<%@page import="model.MstMember"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    MstMember user = (session.getAttribute("user") != null) ? (MstMember) session.getAttribute("user") : new MstMember();
    String username = user.getUsername();
    System.out.println(user);
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Haus of Cars</title>
        <jsp:include page="html_head.jspf"/>
    </head>
    <body>
        <jsp:include page="_layout/header.jsp"/>

        <h1>Home page</h1>
        <p>Welcome, <%out.print(username);%></p>
    </body>
</html>
