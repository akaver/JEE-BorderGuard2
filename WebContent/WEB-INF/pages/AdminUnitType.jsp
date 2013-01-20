<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Admin Unit Type Editor</title>
<link href="<c:url value='/static/style.css' />" type="text/css"
	rel="stylesheet">
</head>
<body>
	<jsp:include page="header.jsp" />
	<form:errors path="*" cssClass="errorblock" element="div" />
	<form:form method="post" action="AdminUnitTypeForm"
		name="AdminUnitTypeForm" modelAttribute="formData"
		commandName="formData">
		<table width="800">
			<tr>
				<td colspan="2"><h3>Admin Unit Type Editor</h3></td>
			</tr>
			<tr>
				<td width="50%"><table width="100%">
						<c:if test="${not empty errors}">
							<tr>
								<td colspan="2">
									<div style="color: red">
										<c:forEach var="error" items="${errors}">
											<c:out value="${error}"></c:out>
											<br />
										</c:forEach>
									</div>
								</td>
							</tr>
						</c:if>
						<tr>
							<td width="100px">Code</td>
							<td><form:input path="adminUnitType.code" type="text"
									size="10" value="${formData.adminUnitType.code}" /> <form:errors
									path="adminUnitType.code" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Name</td>
							<td><form:input path="adminUnitType.name" type="text"
									size="30" value="${formData.adminUnitType.name}" /> <form:errors
									path="adminUnitType.name" cssClass="error" /></td>
						</tr>
						<tr>
							<td valign="top">Comment</td>
							<td><form:textarea path="adminUnitType.comment"
									value="${adminUnitType.comment}" cols="35" rows="10"></form:textarea></td>
						</tr>
						<!-- TODO see here:  http://static.springsource.org/spring/docs/3.0.5.RELEASE/reference/view.html#view-jsp-formtaglib-errorstag  -->
						<c:if test="${formData.adminUnitType.adminUnitTypeID!=1}">
							<tr>
								<td>Subordinate of</td>
								<!-- <select name="AdminUnitTypeMaster_adminUnitTypeID">  -->
								<td><select name="adminUnitTypeMasterID">
										<c:forEach var="entry"
											items="${formData.adminUnitTypeMasterListWithZero}">
											<c:set var="selected" value="" />
											<c:if
												test="${entry.adminUnitTypeID == formData.adminUnitTypeMasterID}">
												<c:set var="selected" value="selected=\"selected\"" />
											</c:if>
											<option value="${entry.adminUnitTypeID}" ${selected}>${entry.name}</option>
										</c:forEach>
								</select></td>
							</tr>
						</c:if>
					</table></td>
				<td style="position: relative">

					<table width="100%" class="borderedTable">
						<tr>
							<td class="allBorders" bgcolor="#CCCCCC">Subordinates</td>
						</tr>

						<c:set var="counter" value="0" />
						<c:forEach var="entry"
							items="${formData.adminUnitTypesSubordinateList}">
							<tr>
								<td class="allBorders">
									<div>${entry.name}</div>
									<div>
										<input name="RemoveButton_${counter}" type="submit"
											value="Remove">
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
										<input name="AddSubordinateButton" type="submit" value="Add">
									</div>
								</td>
							</tr>
						</c:if>
					</table> <!-- adminUnitType.adminUnitTypeSubordinationMasters - ie here this unit acts as master, so we get the list of children on it -->
					<!-- we could use it, if the form would be saved immeditaly. since its not, the list from entity does not represent viewstate after some editing on it  -->
					<!--
					<table width="100%"
						class="borderedTable">
						<tr>
							<td class="allBorders" bgcolor="#CCCCCC">Subordinates</td>
						</tr>

						<c:set var="counter" value="0" />
						<c:forEach var="entry"
							items="${formData.adminUnitType.adminUnitTypeSubordinationMasters}">
							<tr>
								<td class="allBorders">
									<div>${entry.adminUnitTypeSubordinate.name}</div>
									<div>
										<input name="RemoveButton_${counter}" type="submit"
											value="Remove">
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
										<input name="AddSubordinateButton" type="submit" value="Add">
									</div>
								</td>
							</tr>
						</c:if>
					</table>					
					-->

				</td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input name="SubmitButton"
					type="submit" value="Submit"> <input name="CancelButton"
					type="submit" value="Cancel"></td>
			</tr>
		</table>
	</form:form>
</body>
</html>
