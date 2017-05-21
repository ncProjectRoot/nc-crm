<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <spring:url value="/css/login.css" var="springCss" />
    <spring:url value="/img/ico.png" var="springIcon" />

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css">

    <link href="${springCss}" rel="stylesheet" />
    <link href="${springIcon}" rel="icon" />

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Login</title>
</head>

<body>
<h1 class="teal-text darken-3-text">NC-CRM</h1>
<h5 class="teal-text lighten-5">Please, login into your account</h5>
<div class="form-wrapper z-depth-1 grey lighten-4">
    <form action="/login" class="row" method="post" id="auth-form">
        <div class='input-field col s12'>
            <input class='validate' type='email' name='email' id='email' />
            <label for='email'>Enter your email</label>
        </div>
        <div class='input-field col s12'>
            <input class='validate' type='password' name='password' id='password' />
            <label for='password'>Enter your password</label>
        </div>
        <div class='col s12'>
            <input type='checkbox' name='remember-me' id='remember' />
            <label for='remember'>Remember Me</label>
        </div>
        <div class='col s12'>
            <button type='submit' name='btn_login' class='col s12 btn btn-large waves-effect teal darken-3'>Log in</button>
        </div>
        <div class="col s12">
            <label class="label-forgot center-align">
                <a class='modal-trigger waves-effect waves-light blue-grey-text lighten-2' href='#modal1'><b>Forgot Password?</b></a>
            </label>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>

<div id="modal1" class="modal">
    <form action="/forgot" method="post">
        <div class="modal-content">
            <h4>Recovery password</h4>

            <div class='input-field'>
                <input class='validate' type='email' name='email' id="email1"/>
                <label for='email1'>Enter your email</label>
            </div>
            <div class='input-field'>
                <input class='validate' type='tel' name='phone' id="phone"/>
                <label for='phone'>Enter your phone</label>
            </div>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        </div>
        <div class="modal-footer">
            <div class='col s12'>
                <button type='submit' name='btn_login' class='col s12 btn btn-large waves-effect teal'>Recovery</button>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>
<script>
    $(document).ready(function(){
        <c:if test="${not empty error}">
        Materialize.toast("${error}", 10000, 'rounded');
        </c:if>
        <c:if test="${not empty msg}">
        Materialize.toast("${msg}", 10000, 'rounded');
        </c:if>

        $('#auth-form').submit(function() {
            var el = $(this);
            var hash = window.location.hash;
            if (hash) el.prop('action', el.prop('action') + '#' + unescape(hash.substring(1)));
            return true;
        });

        $('.modal-trigger').leanModal();
        Materialize.updateTextFields();
    });
</script>
</body>
</html>