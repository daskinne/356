<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>

<html lang="en">


<jsp:include page="../fragments/headTag.jsp" />

<body>
	<div class="container">
		<jsp:include page="../fragments/bodyHeader.jsp" />

		<h2>Appointments</h2>
		<datatables:table id="vets" data="${appointments.appointmentList}"
			cdn="true" row="patient" theme="bootstrap2"
			cssClass="table table-striped" paginate="false" info="false">
			<datatables:column title="Start Time">
				<c:out value="${appointment.startTime}"></c:out>
			</datatables:column>
			<datatables:column title="End Time">
				<c:out value="${appointment.endTime}"></c:out>
			</datatables:column>
			<datatables:column title="Start Time">
				<c:out value="${appointment.doctorAccount}"></c:out>
			</datatables:column>
			<datatables:column title="Actions">
			<!-- Always modify the latest version of the appointment, identified uniquely by the start and end times -->
				<a href="patient/${appointment.patientAccount}/appointment/?s=${appointment.startTime}&e=${appointment.endTime}">View Appointment</a>
			</datatables:column>
		</datatables:table>
		<jsp:include page="../fragments/footer.jsp" />
	</div>
</body>

</html>
