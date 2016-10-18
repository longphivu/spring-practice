<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/timesheet/resources/style.css" type="text/css">
<title>Managers</title>
</head>
<body>
	<h1>List of managers</h1>
	<a href="managers?new">Add new manager</a>
	<table cellspacing="5" class="main-table">
		<tr>
			<th>Name</th>
			<th>Details</th>
			<th>Delete</th>
		</tr>
		<c:forEach items="${managers}" var="man">
			<tr>
				<td>${man.name}</td>
				<td><a href="managers/${man.id}">Go to page</a></td>
				<td><sf:form action="managers/${man.id}" method="delete"
						cssClass="delete">
						<input type="submit" value="" class="delete-button" />
					</sf:form></td>
			</tr>
		</c:forEach>
	</table>

	<br />
	<a href="welcome">Go back</a>
</body>
</html>