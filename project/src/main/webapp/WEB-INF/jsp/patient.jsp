<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<html lang="en">

<jsp:include page="fragments/headTag.jsp" />

<body>
<div class="container">
    <jsp:include page="fragments/bodyHeader.jsp" />
    <c:choose>
        <c:when test="${patient == null}">
            <c:set var="method" value="post"/>
        </c:when>
        <c:otherwise>
            <c:set var="method" value="put"/>
        </c:otherwise>
    </c:choose>

    <h2>
        <c:if test="${patient == null}">New </c:if>
        Profile
    </h2>

    <form:form modelAttribute="patient" method="${method}" class="form-horizontal">
        <p>
            <label class="control-label">Owner: </label>
            <c:out value="${patient.userId}"/>
        </p>
        
        <p>
            <label class="control-label">Assigned Doctor: </label> 
            <c:out value="${patient.doctorAccount}"/>
       	</p>
        
        <p>
            <label class="control-label">Current Health: </label> 
            <c:out value="${patient.currentHealth}"/>
        </p>
        
        <petclinic:inputField label="Phone Number" name="phoneNumber"/>
        <petclinic:inputField label="Health Card" name="healthCard"/>
        <petclinic:inputField label="SIN Number" name="sin"/>
        <petclinic:inputField label="Address" name="address"/>
        <div class="form-actions">
            <c:choose>
                <c:when test="${patient == null}">
                    <button type="submit">Add Patient</button>
                </c:when>
                <c:otherwise>
                    <button type="submit">Update Patient</button>
                </c:otherwise>
            </c:choose>
        </div>
    </form:form>
    
    
	<jsp:include page="fragments/footer.jsp" />
</div>
</body>

</html>