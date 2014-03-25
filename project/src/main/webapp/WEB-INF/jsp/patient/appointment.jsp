<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp" />
<script>
	$(function() {
		$("#startTime").datetimepicker();
		$("#endTime").datetimepicker();
	});
</script>
<body>
	<div class="container">
		<jsp:include page="../fragments/bodyHeader.jsp" />
		<c:choose>
			<c:when test="${patient == null}">
				<c:set var="method" value="post" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="put" />
			</c:otherwise>
		</c:choose>

		<h2>
			<c:if test="${patient == null}">New </c:if>
			Profile
		</h2>

		<form:form modelAttribute="appointment" method="${method}"
			class="form-horizontal">
			<petclinic:inputField readonly="true" label="User ID"
				name="patientAccount" />
			<petclinic:inputField readonly="true" label="Assigned Doctor"
				name="doctorAccount" />
			<petclinic:inputField readonly="true" label="Version Number"
				name="versionNumber" />
			<petclinic:inputField label="Start Time" name="startTime" />
			<petclinic:inputField label="End Time" name="endTime" />
			<div class="form-actions">
				<c:choose>
					<c:when test="${patient == null}">
						<button type="submit">Add Appointment</button>
					</c:when>
					<c:otherwise>
						<button type="submit">Update</button>
					</c:otherwise>
				</c:choose>
			</div>
		</form:form>

		<h2>
			<c:if test="${updateSuccess==1}">Successfully updated!</c:if>
		</h2>

		<jsp:include page="../fragments/footer.jsp" />
	</div>
</body>

</html>