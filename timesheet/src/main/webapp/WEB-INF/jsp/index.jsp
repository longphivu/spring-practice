<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Welcome to Timesheet app!</title>
</head>
<body>
    <h1>Welcome to the Timesheet App!</h1>
 
    <ul>
        <li><a href="managers">List managers</a></li>
        <li><a href="employees">List employees</a></li>
        <li><a href="tasks">List tasks</a></li>
        <li><a href="timesheets">List timesheets</a></li>
    </ul>
 
    <h2>Also check out <a href="timesheet-service">extra services!</a></h2>
    Today is: <fmt:formatDate value="${today}" pattern="dd-MM-yyyy" />
</body>
</html>