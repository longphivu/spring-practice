<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
 
<html>
<head>
    <title>Employees</title>
    <!-- :TODO : should we include the application context? -->
    <link rel="stylesheet" href="/timesheet/resources/style.css" type="text/css">
</head>
<body>
    <h1>List of employees</h1>
    <a href="employees?new">Add new employee</a>
    <table cellspacing="5" class="main-table">
        <tr>
            <th>Name</th>
            <th>Department</th>
            <th>Details</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="#{employees}" var="emp">
            <tr>
                <td>${emp.name}</td>
                <td>${emp.department}</td>
                <td>
                    <a href="employees/${emp.id}">Go to page</a>
                </td>
                <td>
                    <!-- *Note: To stay Web 1.0 friendly, we unfortunatelly cannot directly use DELETE.
                    That's why sf:form is used here. MUST put Spring filter to web.xml -->
                    <sf:form action="employees/${emp.id}" method="delete" cssClass="delete">
                        <input type="submit" class="delete-button" value="" />
                    </sf:form>
                </td>
            </tr>
        </c:forEach>
    </table>
 
    <br />
    <a href="welcome">Go back</a>
</body>
</html>