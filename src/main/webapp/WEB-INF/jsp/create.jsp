<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:url value="/css/content.css" var="springCss"/>
<link href="${springCss}" rel="stylesheet"/>
<div class="content-header z-depth-1 valign-wrapper">
    <i class="black-text material-icons">note_add</i>
    <span>Create</span>
</div>
<div class="content-body-wrapper z-depth-1">
    Create User
</div>
<script>
    console.log("createUser");
</script>