<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Add new manager</title>
	<link rel="stylesheet" href="/timesheet/resources/style.css" type="text/css">
</head>
<body>
	<h2>Add new Manager</h2>
	<div id="list">
		<sf:form method="post" action="managers">
			<ul>
				<li><label for="name">Name:</label> <input name="name"
					id="name" value="${manager.name}" /></li>
				<li><input type="submit" value="Save" id="save" /></li>
			</ul>
		</sf:form>
	</div>

	<br />
	<br />
	<a href="managers">Go Back</a>
</body>
</html>