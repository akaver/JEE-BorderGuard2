<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Haldusüksuse redaktor</title>
<link href="<c:url value='/static/style.css' />" type="text/css"
	rel="stylesheet">
<link rel="stylesheet"
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="../js/changeButtonHelper.js"></script>
</head>
<body>
	<jsp:include page="header.jsp" />
	<form:errors path="*" cssClass="errorblock" element="div" />
	<form:form method="post" action="AdminUnitForm" name="AdminUnitForm"
		modelAttribute="formData" commandName="formData">

		<table width="800">
			<tr>
				<td colspan="2"><h3>Haldusüksuse redaktor</h3></td>
			</tr>
			<tr>
				<td width="50%"><table width="100%">
						<tr>
							<td width="100px">Kood</td>
							<td><form:input path="adminUnit.code" type="text" size="10"
									value="${formData.adminUnit.code}" /> <form:errors
									path="adminUnit.code" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Nimi</td>
							<td><form:input path="adminUnit.name" type="text" size="30"
									value="${formData.adminUnit.name}" /> <form:errors
									path="adminUnit.name" cssClass="error" /></td>
						</tr>
						<tr>
							<td valign="top">Kommentaar</td>
							<td><form:textarea path="adminUnit.comment" type="text"
									cols="42" rows="10" value="${formData.adminUnit.comment}" /> <form:errors
									path="adminUnit.comment" cssClass="error" /></td>
						</tr>
						<tr>
							<td valign="top">Liik</td>
							<td>
								<div>${formData.adminUnitType.name}</div>
								<div>
									<button type="button" onclick="chooseNewUnitType()">Muuda</button>
								</div>
								<div>
									<form:errors path="adminUnit.adminUnitTypeID" cssClass="error" />
								</div>
							</td>
						</tr>

						<c:if test="${formData.adminUnit.adminUnitTypeID!=1}">
							<tr>
								<td>Allub</td>
								<td><form:select path="adminUnitMasterID">
										<form:options items="${formData.adminUnitMasterListWithZero}"
											itemValue="adminUnitID" itemLabel="name" />
									</form:select>
							</tr>
						</c:if>

					</table></td>
				<td style="position: relative"><table width="100%"
						class="borderedTable">
						<tr>
							<td class="allBorders" bgcolor="#CCCCCC">Alluvad</td>
						</tr>

						<c:set var="counter" value="0" />
						<c:forEach var="entry"
							items="${formData.adminUnitsSubordinateList}">
							<tr>
								<td class="allBorders">
									<div>${entry.name}</div>
									<div>
										<input name="RemoveButton_${counter}" type="submit"
											value="Eemalda">
									</div>
								</td>
							</tr>
							<c:set var="counter" value="${counter+1}" />
						</c:forEach>
						<c:if
							test="${formData.adminUnitsSubordinateListPossible.size()!=0}">
							<tr>
								<td class="allBorders">
									<div>
										<c:set var="counter" value="0" />
										<select name="AdminUnit_NewSubordinateNo">
											<c:forEach var="entry"
												items="${formData.adminUnitsSubordinateListPossible}">
												<option value="${counter}" ${selected}>${entry.name}</option>
												<c:set var="counter" value="${counter+1}" />
											</c:forEach>
										</select>
									</div>
									<div>
										<input name="AddSubordinateButton" type="submit" value="Lisa">
									</div>
								</td>
							</tr>
						</c:if>
					</table></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input name="SubmitButton"
					type="submit" value="Salvesta"> <input name="CancelButton"
					type="submit" value="Loobu"></td>
			</tr>
			<tr>
				<td><form:input type="hidden" id="forSending"
						path="adminUnit.adminUnitTypeID"
						value="${formData.adminUnit.adminUnitTypeID}" /></td>
			</tr>
		</table>
		<div id="forUnitTypeChoosing"
			style="display: none; font-family: 'Comic Sans MS', cursive, sans-serif;"
			title="Vali uus liik">
			<form:select path="adminUnit.adminUnitTypeID"
				onchange="changeDocData(this)">
				<form:options items="${formData.adminUnitTypeList}"
					itemValue="adminUnitTypeID" itemLabel="name" />
			</form:select>


			<%-- <select id="selectbox" name="AdminUnitType_adminUnitTypeID_orig"
				onchange="changeDocData(this)">
				<c:forEach var="entry" items="${formData.adminUnitTypeList}">
					<option value="${entry.adminUnitTypeID}" ${selected}>${entry.name}</option>
				</c:forEach>
			</select> --%>
		</div>
	</form:form>
</body>
</html>