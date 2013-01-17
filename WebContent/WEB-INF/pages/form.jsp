<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link href="<c:url value='/static/styles.css' />" type="text/css" rel="stylesheet">
</head>
<body>

    <jsp:include page="lang.jsp" />

    <br>

	<form:form method="POST" commandName="person" action="form">
		<c:if test="${not empty message}">
			<div class="messageblock">
				<spring:message code="${message}" />
			</div>
		</c:if>
		<form:errors path="*" cssClass="errorblock" element="div" />
		<spring:message code="person.name" /> :
		<form:input path="name" />
		<form:errors path="name" cssClass="error" />

        <br>

		<spring:message code="person.age" /> :
		<form:input path="age" />
		<form:errors path="age" cssClass="error" />

        <br><br>

		<td colspan="3"><input type="submit" value="<spring:message code="button.send"/>"/></td>

	</form:form>

</body>
</html>