<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formtags" tagdir="/WEB-INF/tags" %>

<html lang="en">

<jsp:include page="fragments/headTag.jsp"/>
<body>
<div class="container">
    <jsp:include page="fragments/bodyHeader.jsp"/>
    <c:set var="method" value="post"/>
    <h2>
        Login
    </h2>

    <form:form modelAttribute="login" method="${method}"
               class="form-horizontal">
        <formtags:inputField label="Username" name="username"/><div class="control-group ">
        <label class="control-label">Password</label>
        <div class="controls">
            <input id="password" name="password" type="password">
            <span class="help-inline"></span>
        </div>
        </div>
        <div class="form-actions">
			<button type="submit">Login</button>
        </div>
    </form:form>
    <jsp:include page="fragments/footer.jsp"/>
</div>
</body>

</html>
