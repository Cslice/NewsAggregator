<%-- 
    Document   : homepage
    Created on : Aug 25, 2015, 10:11:56 AM
    Author     : cameronthomas
--%>



<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${sessionScope.user == null}" > 
   <c:redirect url="index.jsp"/>
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Homepage</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    
    </body>
</html>
