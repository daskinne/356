<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>
	ECE356:<br /> <small>An example of literally everything you
		shouldn't do with a web application.</small>
</h1>
<div class="navbar">
	<div class="navbar-inner">
		<ul class="nav">
			<c:if test="${empty sessionScope.user }">
				<li style="margin: 10px;"><a href="<spring:url value="/login" htmlEscape="true" />">
						Login</a></li>
			</c:if>
			<c:if test="${!empty sessionScope.user }">
				<li style="margin: 20px;">User: ${sessionScope.user.userId}</li>
				<c:if test="${sessionScope.user.type == 'PATIENT' }">
					<li style="margin: 10px;"><a href="patient">Profile</a></li>
				</c:if>
				<c:if test="${sessionScope.user.type != 'PATIENT' }">
					<li style="margin: 10px;"><a
						href="<spring:url value="/patients" htmlEscape="true" />"><i
							class="icon-search"></i>Patients</a></li>
				</c:if>
				<li style="margin: 10px;"><a
					href="<spring:url value="/logout" htmlEscape="true" />"> Logout</a></li>
			</c:if>
			<li style="margin: 10px;"><a
				href="<spring:url value="/" htmlEscape="true" />"><i
					class="icon-home"></i> Home</a></li>


		</ul>
	</div>
</div>

