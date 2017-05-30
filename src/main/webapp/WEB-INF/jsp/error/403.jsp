<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url value="/img/cat.png" var="errorUrl"/>
<style>
    .content-body {
        height: 400px;
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        justify-content: center;
        align-items: center;
        position: relative;
    }

    .error-img {
        position: absolute;
        left: 10px;
        top: 0;
        width: 25%;
    }

    .message-h1 {
        font-size: 5rem;
        text-align: center;
    }

    .message-h2 {
        font-size: 2.8rem;
        text-align: center;
    }

    @media (max-width: 700px)  {

        .error-img {
            width: 40%;
        }

        .message-h1 {
            font-size: 40px;
        }

        .message-h2 {
            font-size: 18px;
        }
    }
</style>
<div class="content-body z-depth-1" data-page-name="403">
    <img class="error-img" src="${errorUrl}"/>
    <h1 class="message-h1">403</h1>
    <h2 class="message-h2">Forbidden Error</h2>
</div>