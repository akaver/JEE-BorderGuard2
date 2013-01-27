<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="adminUnitReport.label.title" /></title>
<link rel="stylesheet" href="../static/style.css" type="text/css">
<link rel="stylesheet"
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
<script type="text/javascript">
	window.onload = function() {
		if ($('#forInfoBox').html() != '') {
			var dialog_buttons = {};
			dialog_buttons['OK'] = function() {
				$(this).dialog('close');
			};

			$('#infoBoxContent').dialog({
				buttons : dialog_buttons,
				closeOnEscape : true
			});
		}
	};
</script>
</head>
<body>
	<jsp:include page="logout.jsp" />
	<jsp:include page="header.jsp" />

	<div class="withMargin">
		<form:form method="post" action="AdminUnitReportForm"
			name="AdminUnitReportForm" modelAttribute="formData"
			commandName="formData">
			<form:errors path="*" cssClass="errorblock" element="div" />

			<table width="450">
				<tr>
					<td colspan="2"><h3>
							<spring:message code="adminUnitReport.label.title" />
						</h3></td>
				</tr>
				<tr>
					<td width="40%"><spring:message
							code="adminUnitReport.label.date" /></td>
					<td width="60%"><spring:message
							code="adminUnitReport.label.type" /></td>
				</tr>
				<tr>
					<td><form:input path="searchDate" type="text" size="30"
							disabled="true" value="${formData.searchDate}" />
					<td>
						<div>
							<form:select path="adminUnitType.adminUnitTypeID">
								<form:options items="${formData.adminUnitTypeList}"
									itemValue="adminUnitTypeID" itemLabel="name" />
							</form:select>
						</div>
						<div>
							<input name="RefreshButton" type="submit"
								value="<spring:message code="adminUnitReport.button.refresh"/>">
						</div>
					</td>
				</tr>
				<c:forEach var="subordinationSet"
					items="${formData.adminUnitMasterList}">
					<tr>
						<td colspan="2">
							<table class="allBorders" width="100%">
								<tr>
									<td class="allBorders" bgcolor="#CCCCCC">${subordinationSet.name}</td>
								</tr>
								<c:forEach var="subordinate"
									items="${subordinationSet.adminUnitSubordinationSubordinates}">
									<tr>
										<td class="allBorders">
											<div>${subordinate.adminUnitSubordinate.name}</div>
											<div>
												<input
													name="LookButton_${subordinate.adminUnitSubordinate.adminUnitID}"
													type="submit"
													value="<spring:message code="adminUnitReport.button.look"/>">
											</div>
										</td>
									</tr>
								</c:forEach>
								<c:if
									test="${fn:length(subordinationSet.adminUnitSubordinationSubordinates) == 0 }">
									<tr>
										<td class="allBorders">
											<div>-</div>
										</td>
									</tr>
								</c:if>
							</table>
						</td>
					</tr>
					<br>
				</c:forEach>
				<tr>
					<td colspan="2"><input name="BackButton" type="submit"
						value="<spring:message code="adminUnitReport.button.back"/>"
						style="float: right"></td>
				</tr>
			</table>
			<div id="forInfoBox" style="display: none;">
				<c:if test="${fn:length(formData.chosenSubordinate.name) > 0}">
					<div
						style="display: none; font-family: 'Comic Sans MS', cursive, sans-serif;"
						id="infoBoxContent" title="${formData.chosenSubordinate.name}">

						<form:label path="chosenSubordinate.name">
							<spring:message code="adminUnitReport.label.name" />: ${formData.chosenSubordinate.name}</form:label>
						<br>
						<form:label path="chosenSubordinate.code">
							<spring:message code="adminUnitReport.label.code" />: ${formData.chosenSubordinate.code}</form:label>
						<br>
						<form:label path="adminUnitTypeName">
							<spring:message code="adminUnitReport.label.type" />: ${formData.adminUnitTypeName}</form:label>
						<br>
						<form:label path="adminUnitMasterName">
							<spring:message code="adminUnitReport.label.belongsTo" />: ${formData.adminUnitMasterName}</form:label>
						<br>

						<c:if
							test="${fn:length(formData.chosenSubordinate.adminUnitSubordinationSubordinates) > 0}">
							<spring:message code="adminUnitReport.label.subordinates" />:<br>
						</c:if>
						<c:forEach var="subsubordinate"
							items="${formData.chosenSubordinate.adminUnitSubordinationSubordinates}">
							<span style="padding-left: 20px;"> -
								${subsubordinate.adminUnitSubordinate.name}</span>
							<br>
						</c:forEach>

						<c:if test="${fn:length(formData.chosenSubordinate.comment) > 0}">
							<spring:message code="adminUnitReport.label.comment" />: ${formData.chosenSubordinate.comment}												
				</c:if>
					</div>
				</c:if>
			</div>
		</form:form>
	</div>
</body>
</html>