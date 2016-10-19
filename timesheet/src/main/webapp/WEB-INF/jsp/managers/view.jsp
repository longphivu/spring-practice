<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Manager page</title>
<link rel="stylesheet" href="/timesheet/resources/style.css" type="text/css">
</head>
<body>
	<h2>Manager info</h2>
	<div id="list">
		<sf:form method="post">
			<ul>
				<li><label for="name">Name:</label> <input name="name"
					id="name" value="${manager.name}" disabled="true" /></li>
				<li><input type="button" value="Unlock" id="unlock" /> <input
					type="submit" value="Save" id="save" class="hidden" /></li>
			</ul>
		</sf:form>
	</div>

	<br />
	<br />
	<a href="../managers">Go Back</a>

	<script src="/timesheet/resources/jquery.min.js"></script>
	<script>
		(function() {
			$("#unlock").on("click", function() {
				$("#unlock").addClass("hidden");

				// enable stuff
				$("#name").removeAttr("disabled");
				$("#save").removeClass("hidden");
			});
		})();
	</script>
</body>
</html>