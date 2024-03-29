<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<jsp:include page="fragments/headTag.jsp" />

<body>
	<div class="container">
		<jsp:include page="fragments/bodyHeader.jsp" />
		<h2>Something happened...</h2>

		<p>${exception.message}</p>
		<!-- TODO: remove stacktrace -->
		Exception: ${exception.message}.
		<c:forEach items="${exception.stackTrace}" var="stackTrace"> 
				${stackTrace} 
			</c:forEach>
		<jsp:include page="fragments/footer.jsp" />

	</div>
</body>

</html>
