<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>

  <jsp:include page="lang.jsp" />

  <c:if test="${not empty error}">
      <spring:message code="${error}"/>
  </c:if>

  <form action="<c:url value="j_spring_security_check" />" method="POST">
    <input type="text" name="j_username" value="admin"><br>
    <input type="password" name="j_password" value="1" /><br>
    <input type="submit" value="<spring:message code="button.login"/>" />
  </form>

</body>
</html>
