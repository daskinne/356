<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp" />


<body>
	<script>
		$(function() {
			$("#date").datepicker({
				dateFormat : 'yy/mm/dd'
			});
		});
	</script>
	<div class="container">
		<jsp:include page="../fragments/bodyHeader.jsp" />
		<h2>Visit</h2>

		<b>Patient</b>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Name</th>
					<th>Health</th>
					<th>Primary Doctor</th>
				</tr>
			</thead>
			<tr>
				<!-- TODO: this patientName field must be filled out and only shown to doctors/staff and not auditors -->
				<td><c:out value="${visit.patientName}" /></td>
				<td><c:out value="${visit.patient.currentHealth}" /></td>
				<td><c:out value="${visit.patient.doctorAccount}" /></td>
			</tr>
		</table>

		<!-- TODO: JODA time untested must check -->
		<%-- <joda:format value="${visit.startTime}" pattern="yyyy/MM/dd hh:mm"/> --%>

		<form:form modelAttribute="appointment" class="form-horizontal">
			<petclinic:inputField readonly="true" label="Version Number"
				name="versionNumber" />
			<petclinic:inputField readonly="true" label="Start Time"
				name="startTime" />
			<petclinic:inputField readonly="true" label="End Time" name="endTime" />
		</form:form>


		<form:form modelAttribute="visit" class="form-horizontal">
			<petclinic:inputField readonly="false" label="Diagnosis"
				name="diagnosis" />
			<petclinic:inputField readonly="false" label="Diagnostic Procedure"
				name="diagnosticProcedure" />
			<petclinic:inputField readonly="false" label="Comment"
				name="commentValue" />
			<!-- DISABLE UPDATE OF COMMENTS IF STAFF OR NON PRIMARY DOCTOR -->
			<button type="submit">Update Comment</button>
		</form:form>
		<br /> <b>Prescriptions</b>
		<table style="width: 333px;">
			<tr>
				<th>Medication</th>
				<th>Dosage</th>
				<th>End Date</th>
			</tr>
			<c:forEach var="prescription" items="${visit.prescriptions}">
				<tr>
					<!-- TODO: add user friendly medicationName field to legal prescription model  -->
					<td><c:out value="${prescription.medicationName}" /></td>
					<td><c:out value="${prescription.dosage}" /></td>
					<td><joda:format value="${prescription.endDate}"
							pattern="yyyy/MM/dd" /></td>
				</tr>
				Not Implemented:
				<button>Add Prescription</button>
			</c:forEach>
		</table>

		<b>Treatments</b>
		<table style="width: 333px;">
			<tr>
				<th>Procedure</th>
				<th>Start Time</th>
				<th>End Time</th>
			</tr>
			<c:forEach var="treatment" items="${visit.treatments}">
				<tr>
					<!-- TODO: add user friendly medicationName field to legal prescription model  -->
					<td><c:out value="${treatment.procedure}" /></td>
					<td><joda:format value="${treatment.startTime}"
							pattern="yyyy/MM/dd" /></td>
					<td><joda:format value="${treatment.endTime}"
							pattern="yyyy/MM/dd" /></td>
				</tr>
				Not Implemented
				<button>Add Treatment</button>
			</c:forEach>
		</table>

	</div>
	<jsp:include page="../fragments/footer.jsp" />
</body>

</html>
