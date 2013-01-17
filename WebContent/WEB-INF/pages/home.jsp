<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

    <jsp:include page="lang.jsp" />

    <p><spring:message code="label.user"/>: ${username}</p>

    <sec:authorize ifAnyGranted="ROLE_ADMIN">
        <p><a href="admin/form"><spring:message code="link.newPerson"/></a><p>
    </sec:authorize>

    <a href="logout"><spring:message code="link.logout"/></a>

</body>
</html>