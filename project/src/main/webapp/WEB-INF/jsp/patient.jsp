<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formtags" tagdir="/WEB-INF/tags" %>

<html lang="en">

<jsp:include page="fragments/headTag.jsp"/>
<body>
<div class="container">
    MOther fuckinger
    
    <c:out value="${owner.firstName} ${owner.lastName}"/>
   	
</div>
</body>

</html>
