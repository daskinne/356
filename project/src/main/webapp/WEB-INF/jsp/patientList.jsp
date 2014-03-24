<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>

<html lang="en">


<jsp:include page="fragments/headTag.jsp" />

<body>
	<div class="container">
		<jsp:include page="fragments/bodyHeader.jsp" />

		<h2>Patients</h2>
		<datatables:table id="vets" data="${patients.patientList}" cdn="true"
			row="patient" theme="bootstrap2" cssClass="table table-striped"
			paginate="false" info="false">
			<datatables:column title="Name">
				<c:out value="${patient.userId}"></c:out>
			</datatables:column>
			<datatables:column title="Actions">
				<a href="patient/${patient.userId}/visits/">View Visits</a>
				<a href="patient/${patient.userId}/profile/">View Profile</a>
				<a href="patient/${patient.userId}/visits/new">Add Visit</a>
			</datatables:column>
			<datatables:column title="Assigned Doctors">
				<c:forEach var="doctor" items="${patient.assignedDoctors}">
					<a href="/doctor/${doctor.userId}/profile/"><c:out
							value="${doctor.userId}" /></a>
				</c:forEach>
				<c:if test="${patient.numDoctors == 0}">unassigned!</c:if>
				<a href="/patient/${patient.userId}/assign-doctor/">Assign
					Doctor</a>
			</datatables:column>
		</datatables:table>
		<jsp:include page="fragments/footer.jsp" />
	</div>
</body>

</html>
