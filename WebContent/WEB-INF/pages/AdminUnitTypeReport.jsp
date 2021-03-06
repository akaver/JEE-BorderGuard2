<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="adminUnitTypeReport.label.title" /></title>
<link href="<c:url value='/static/style.css' />" type="text/css"
	rel="stylesheet">
<link href="<c:url value='/js/jquery.treeview.css' />" type="text/css"
	rel="stylesheet">
<link rel="stylesheet"
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
<script src="<c:url value='/js/jquery.cookie.js' />"
	type="text/javascript"></script>
<script src="<c:url value='/js/jquery.treeview.js' />"
	type="text/javascript"></script>
<script src="<c:url value='/js/jquery.treeview.async.js' />"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#black").treeview({
			url : "tree"
		});
	});
</script>
</head>
<body>
	<div style="float: right">
		<spring:message code="header.label.hello" />
		${username}! <a href="<c:url value='/logout' />"><spring:message
				code="header.link.logout" /></a>
	</div>
	<jsp:include page="header.jsp" />
	<div id="main" class="withMargin">
		<p>
			<spring:message code="adminUnitTypeReport.label.title" />
		</p>
		<p>
			<a href="<c:url value='/' />"><spring:message
					code="adminUnitTypeReport.link.back" /></a>
		</p>
		<hr>
		<!-- here is the magic -->
		<ul id="black">
		</ul>
	</div>
</body>
</html>