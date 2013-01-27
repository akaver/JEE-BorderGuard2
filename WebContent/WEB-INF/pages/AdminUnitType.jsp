<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code="adminUnitType.label.title" /></title>
<link href="<c:url value='/static/style.css' />" type="text/css"
	rel="stylesheet">
</head>
<body>
	<div style="float: right">
		<spring:message code="header.label.hello" />
		${formData.username}! <a href="<c:url value='/logout' />"><spring:message
				code="header.link.logout" /></a>
	</div>
	<jsp:include page="header.jsp" />

	<div class="withMargin">
		<form:form method="post" action="AdminUnitTypeForm"
			name="AdminUnitTypeForm" modelAttribute="formData"
			commandName="formData">
			<form:errors path="*" cssClass="errorblock" element="div" />

			<table width="800">
				<tr>
					<td colspan="2"><h3>
							<spring:message code="adminUnitType.label.title" />
						</h3></td>
				</tr>
				<tr>
					<td width="50%"><table width="100%">
							<tr>
								<td width="100px"><spring:message
										code="adminUnitType.label.code" /></td>
								<td><form:input path="adminUnitType.code" type="text"
										size="10" value="${formData.adminUnitType.code}" /> <form:errors
										path="adminUnitType.code" cssClass="error" /></td>
							</tr>
							<tr>
								<td><spring:message code="adminUnitType.label.name" /></td>
								<td><form:input path="adminUnitType.name" type="text"
										size="30" value="${formData.adminUnitType.name}" /> <form:errors
										path="adminUnitType.name" cssClass="error" /></td>
							</tr>
							<tr>
								<td valign="top"><spring:message
										code="adminUnitType.label.comment" /></td>
								<td><form:textarea path="adminUnitType.comment"
										value="${adminUnitType.comment}" cols="35" rows="10"></form:textarea></td>
							</tr>
							<c:if test="${formData.adminUnitType.adminUnitTypeID!=1}">
								<tr>
									<td><spring:message code="adminUnitType.label.belongsTo" /></td>
									<td><form:select path="adminUnitTypeMasterID">
											<form:options
												items="${formData.adminUnitTypeMasterListWithZero}"
												itemValue="adminUnitTypeID" itemLabel="name" />
										</form:select>
								</tr>
							</c:if>
						</table></td>
					<td style="position: relative">

						<table width="100%" class="borderedTable">
							<tr>
								<td class="allBorders" bgcolor="#CCCCCC"><spring:message
										code="adminUnitType.label.subordinates" /></td>
							</tr>

							<c:set var="counter" value="0" />
							<c:forEach var="entry"
								items="${formData.adminUnitTypesSubordinateList}">
								<tr>
									<td class="allBorders">
										<div>${entry.name}</div>
										<div>
											<input name="RemoveButton_${counter}" type="submit"
												value="<spring:message code="adminUnitType.button.remove" />">
										</div>
									</td>
								</tr>
								<c:set var="counter" value="${counter+1}" />
							</c:forEach>
							<c:if
								test="${formData.adminUnitTypesSubordinateListPossible.size()!=0}">
								<tr>
									<td class="allBorders">
										<div>
											<c:set var="counter" value="0" />
											<select name="AdminUnitType_NewSubordinateNo">
												<c:forEach var="entry"
													items="${formData.adminUnitTypesSubordinateListPossible}">
													<option value="${counter}" ${selected}>${entry.name}</option>
													<c:set var="counter" value="${counter+1}" />
												</c:forEach>
											</select>
										</div>
										<div>
											<input name="AddSubordinateButton" type="submit"
												value="<spring:message code="adminUnitType.button.add" />">
										</div>
									</td>
								</tr>
							</c:if>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input name="SubmitButton"
						type="submit"
						value="<spring:message code="adminUnitType.button.submit" />">
						<input name="CancelButton" type="submit"
						value="<spring:message code="adminUnitType.button.cancel" />"></td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>
