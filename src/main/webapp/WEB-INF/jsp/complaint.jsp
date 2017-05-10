<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<sec:authentication var="user" property="principal"/>
<style>

    .content-body {
        position: relative;
    }

    .container {
        padding-top: 50px;
        padding-bottom: 100px;
    }

    .container selector {
        position: relative;
    }

    h4, h5, h6 {
        text-align: center;
        word-wrap: break-word;
    }

    .collapsible-header {
        padding-top: 1px;
        padding-bottom: 1px;
    }

    #message_popup {
        width: 50%;
        margin: auto;
    }

    .message_block {
        font-size: 17px;
        text-align: justify;
    }

    .status {
        text-transform: capitalize
    }

    .status.SOLVING {
        color: #1b5e20;
    }

    .status.OPEN {
        color: #4a148c;
    }

    .status.CLOSED {
        color: #3e2723;
    }

    .button-pmg {
        position: fixed;
        right: 5%;
        top: 90%;
        z-index: 99999;
    }

</style>
<div class="content-body z-depth-1" data-page-name="Complaint #${complaint.id}">
    <div class="container">
        <h4 class="title">${complaint.title}</h4>
        <div class="divider"></div>
        <div class="section">
            <h5>Status: <span class="status ${complaint.status.name}" id="status"> ${complaint.status.name} </span></h5>
        </div>
        <div class="divider"></div>
        <div class="section">
            <h5>Creation date: ${complaint.date.toLocalDate()}</h5>
        </div>
        <div class="divider"></div>
        <div class="section">
            <h5>Order: <a href="/#order?id=${complaint.order.id}"> #${complaint.order.id}</a></h5>
            <h5>Product: <a href="/#product?id=${complaint.order.product.id}"> ${complaint.order.product.title}</a>
            </h5>
        </div>
        <div class="divider"></div>
        <div class="section">
            <h5>Customer info:</h5>
            <sec:authorize access="hasAnyRole('ROLE_CUSTOMER')">
                <h5> #${complaint.customer.id} ${complaint.customer.firstName} ${complaint.customer.lastName}</h5>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_PMG', 'ROLE_ADMIN')">
                <h5><a href="/#user?id=${complaint.customer.id}">
                    #${complaint.customer.id} </a> ${complaint.customer.firstName} ${complaint.customer.lastName}</h5>
            </sec:authorize>
            <h5>email: <a href="mailto:${complaint.customer.email}">${complaint.customer.email}</a></h5>
        </div>
        <sec:authorize access="hasRole('ROLE_PMG')">
            <c:if test="${complaint.pmg!=null}">
                <div class="divider"></div>
                <div class="section">
                    <h5>Pmg info:</h5>
                    <h5> #${complaint.pmg.id} ${complaint.pmg.firstName} ${complaint.pmg.lastName}</h5>
                </div>
            </c:if>
        </sec:authorize>
        <div class="divider"></div>
        <div class="section">
            <ul class="collapsible" data-collapsible="expandable" id="message_popup">
                <li>
                    <div class="collapsible-header"><h5>Message</h5></div>
                    <div class="collapsible-body">
                        <span> <h5 class="message_block"> ${complaint.message.replaceAll("\\n", "<br>")} </h5> </span>
                    </div>
                </li>
            </ul>
        </div>
        <div class="divider"></div>
        <div class="section">
            <sec:authorize access="hasRole('ROLE_PMG')">
                <c:if test="${complaint.status=='OPEN'}">
                    <div class="button-pmg">
                        <a class="waves-effect waves-light btn" id="acceptBtn">accept</a>
                    </div>
                </c:if>
                <c:if test="${complaint.status=='SOLVING'}">
                    <c:if test="${user.id==complaint.pmg.id}">
                        <div class="button-pmg">
                            <a class="waves-effect waves-light btn" id="closeBtn">close</a>
                        </div>
                    </c:if>
                </c:if>
            </sec:authorize>
        </div>
    </div>
</div>
<script>
    $('.collapsible').collapsible();
    <sec:authorize access="hasRole('ROLE_PMG')">

    $.ajaxSetup({
        complete: $(function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
        })
    });

    $('#acceptBtn').bind('click', function () {
        $(".progress").addClass("progress-active");
        $.ajax({
            url: "/complaints/${complaint.id}",
            type: 'PUT',
            data: {
                type: 'ACCEPT'
            },
            success: function (data) {
                if (data === true) {
                    $('#acceptBtn').remove();
                    $('#status').replaceWith('SOLVING');
                    $(".progress").removeClass("progress-active");
                    Materialize.toast("You have accepted the complaint!", 5000, 'rounded');
                } else if (data === false) {
                    $(".progress").removeClass("progress-active");
                    Materialize.toast("Something wrong!", 3000, 'rounded');
                    setTimeout(function () {
                        window.location.reload();
                    }, 3000);
                }
            }
        });
    });

    $('#closeBtn').bind('click', function () {
        $(".progress").addClass("progress-active");
        $.ajax({
            url: "/complaints/${complaint.id}",
            type: 'PUT',
            data: {
                type: 'CLOSE'
            },
            success: function (data) {
                if (data === true) {
                    $('#closeBtn').remove();
                    $('#status').replaceWith('CLOSED');
                    $(".progress").removeClass("progress-active");
                    Materialize.toast("You have closed the complaint!", 5000, 'rounded');
                } else if (data === false) {
                    $(".progress").removeClass("progress-active");
                    Materialize.toast("Something wrong!", 3000, 'rounded');
                    setTimeout(function () {
                        window.location.reload();
                    }, 3000);
                }
            }
        });
    });
    </sec:authorize>

</script>