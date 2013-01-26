<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script>
	function addLangParam(value) {
		var loc = window.location.href;
		var regex = new RegExp("[?//&]lang=");
		if(regex.test(loc)) {
			var langIdx = loc.search(regex);
			window.location.href = loc.replace(loc.substring(langIdx + 1, langIdx + 8), 'lang=' + value);
		}
		else if (loc.indexOf('?') == -1) {
			window.location.href = loc + "?lang=" + value;
		}
		else {
			window.location.href = loc + "&lang=" + value;
		}
	}
</script>
<div class="milHeading">
	<spring:message code="header.label.appName"/> - Anu Kuusmaa <spring:message code="header.label.and"/> Andres Käver 
		(<a href="javascript:addLangParam('en');"><img src="<c:url value='/static/en.gif' />" /></a> | 
		<a href="javascript:addLangParam('et');"><img	src="<c:url value='/static/et.gif' />" /></a>)
		<br>
</div>
