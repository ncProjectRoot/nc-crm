<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <spring:url value="/css/main.css" var="springCss"/>
    <spring:url value="/img/ico.png" var="springIcon"/>
    <spring:url value="/js/main.js" var="springScript"/>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" type="text/css"
    href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.1/css/materialize.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/chartist/0.11.0/chartist.min.css">

    <link href="${springCss}" rel="stylesheet"/>
    <link href="${springIcon}" rel="icon"/>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>NC-CRM</title>
</head>

<body>
<header>
    <nav>
        <div class="nav-wrapper grey lighten-5">
            <div class="menu-left">
                <div class="menu-block hide-on-med-and-down menu-left-item"></div>
                <a href="." data-activates="slide-out" class="button-collapse menu-left-item a-dummy"><i
                        class="black-text material-icons">menu</i></a>
                <a href="." class="hide-on-med-and-down menu-left-item burger a-dummy"><i
                        class="black-text material-icons">menu</i></a>
                <a href="#messages" class="menu-left-item message-menu-item" data-new-message="${newMessage}"><i
                        class="black-text material-icons">email</i></a>
            </div>
            <ul class="menu-right">
                <li class="menu-item-language">
                    <div>
                        <a class='dropdown-button black-text a-dummy' href='.'
                           data-activates='dropdown-language'>${currentLanguage}</a>
                        <ul id='dropdown-language' class='dropdown-content'>
                            <li><a href=".">English</a></li>
                            <li><a href=".">Russian</a></li>
                        </ul>
                    </div>
                </li>
                <li class="hide-on-med-and-up">
                    <a href="." class="a-logout a-dummy menu-left-item">
                        <i class="black-text material-icons">settings_power</i>
                        <form action="/logout" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                    </a>
                </li>
                <li class="menu-item-user hide-on-small-only" data-user-role="${user.userRole}">
                    <div>
                        <a class='dropdown-button a-dummy' href='.' data-activates='dropdown-user'>
                            <img src="https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png"
                                 alt="" class="circle responsive-img z-depth-1">
                            <div>
                                <span class="black-text title" id="user-name">${user.lastName} ${user.firstName}</span>
                            </div>
                        </a>
                        <ul id='dropdown-user' class='dropdown-content'>
                            <li><a href="#profile"><i class="small material-icons">person_pin</i>My Profile</a></li>
                            <li><a href="#settings"><i class="small material-icons">settings</i>Setting</a></li>
                            <li>
                                <a href="." class="a-logout a-dummy">
                                    <i class="small material-icons">settings_power</i>
                                    Log Out
                                    <form action="/logout" method="post">
                                          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </form>
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>

            </ul>
        </div>
    </nav>
</header>

<div class=side-nav-wrapper>
    <ul id="slide-out" class="side-nav fixed z-depth-0 blue-grey lighten-2">
        <li class="grey-text lighten-5">
            <a href="." class="logo valign-wrapper a-dummy">
                <div class="icon"></div>
                <h1>NC-CRM</h1>
            </a>
        </li>
        <li class="li-divider">
            <div class="divider"></div>
        </li>
        <%@ include file="/WEB-INF/jsp/menu.jsp" %>
    </ul>
</div>


<div class="content">
    <%--<div class="content-header z-depth-1 valign-wrapper">--%>
        <%--<i class="black-text material-icons"></i>--%>
        <%--<span></span>--%>
    <%--</div>--%>
    <div class="progress">
        <div class="indeterminate"></div>
    </div>
    <div class="content-body">
    </div>

    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.1/js/materialize.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/chartist/0.11.0/chartist.min.js"></script>
    <script src="${springScript}"></script>
</div>
</body>
</html>