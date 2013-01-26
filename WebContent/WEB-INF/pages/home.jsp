<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><spring:message code="home.label.title" /></title>
<link href="<c:url value='/static/style.css' />" type="text/css"
	rel="stylesheet">
</head>
<body>
	<jsp:include page="header.jsp" />
	<!-- plain old form, no model connected with it -->
	<form method="post" action="homeActivityForm">
		<div>
			<div class="withMargin">
				<spring:message code="home.label.chooseAction" />
				<br> <br> <a href="<c:url value='/populate' />"><spring:message
						code="home.label.reloadDatabase" /></a><br> <br>
			</div>

			<div class="milContent">
				<spring:message code="home.label.adminUnitType" />:<br> <select name="AdminUnitTypeID">
					<c:forEach var="entry" items="${formData.adminUnitTypeList}">
						<option value="${entry.adminUnitTypeID}">${entry.name}</option>
					</c:forEach>
				</select> <input type="submit" name="ViewAdminUnitType" value="<spring:message code="home.button.lookChange" />">
				<input type="submit" name="AddAdminUnitType" value="<spring:message code="home.button.addNew" />">
				<input type="submit" name="ReportAdminUnitType" value="<spring:message code="home.button.report" />">
			</div>

			<div class="milContent">
				<spring:message code="home.label.adminUnit" />: <br> <select name="AdminUnitID">
					<c:forEach var="entry" items="${formData.adminUnitList}">
						<option value="${entry.adminUnitID}">${entry.name}</option>
					</c:forEach>
				</select> <input type="submit" name="ViewAdminUnit" value="<spring:message code="home.button.lookChange" />">
				<input type="submit" name="AddAdminUnit" value="<spring:message code="home.button.addNew" />"> <input
					type="submit" name="ReportAdminUnit" value="<spring:message code="home.button.report" />">
			</div>
		</div>
	</form>
</body>
</html>