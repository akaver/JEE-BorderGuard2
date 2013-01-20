<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script>
	function addParam(value) {
		var loc = window.location.href;
		var langIdx = loc.indexOf('lang=');
		
		if (langIdx == -1) {
			window.location.href = loc + '?' + value;
		}
		else {
			window.location.href = loc.replace(loc.substring(langIdx - 1, langIdx + 7), value);
		}
	}
</script>
<div class="milHeading">
	<spring:message code="header.appName"/> - Anu Kuusmaa <spring:message code="header.and"/> Andres Käver 
		(<a href="javascript:addParam('&lang=en');"><img src="<c:url value='/static/en.gif' />" /></a> | 
		<a href="javascript:addParam('&lang=et');"><img	src="<c:url value='/static/et.gif' />" /></a>)
		<br>
</div>
