<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .content-body {
        height: 400px;
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        justify-content: center;
        align-items: center;
    }

    ul li span {
        color: darkblue;
    }

    .date {
        text-decoration: underline;
    }

    .title {
        color: green;
    }

    .name {
        color: darkblue;
    }

    .history {
        width: calc(100% - 20px * 2);
        margin: 0 auto;
        font-size: 17px;
    }

    #status{
        color: darkblue;
    }
</style>
<div class="content-body z-depth-1" data-page-name="Order #${order.id}">
    <h2  id="status">${order.status.name.replaceAll("_", " ")}</h2>
    <div class="section">
        <h5>Product : <a href="#product/${order.product.id}" class="title">${order.product.title}</a></h5>
    </div>
    <div class="section">
        <h5>Customer : <a href="#user/${order.customer.id}" class="name">${order.customer.firstName} ${order.customer.lastName}</a></h5>
    </div>
    <div class="section">
        <h5>Order date : <span class="date">${order.date.toString().replace("T", " ")}</span></h5>
        <c:if test="${order.status == 'PROCESSING'}">
            <h5>Prefered date : <span class="date">${order.preferedDate.toString().replace("T", " ")}</span></h5>
        </c:if>
    </div>


    <div class="row">
        <div class="section">
            <sec:authentication var="user" property="principal"/>
            <sec:authorize access="hasAnyRole('ROLE_CSR', 'ROLE_ADMIN')">
                <input type="hidden" id="csrfToken" value="${_csrf.token}"/>
                <input type="hidden" id="csrfHeader" value="${_csrf.headerName}"/>
                <div class="section">
                    <sec:authorize access="hasAnyRole('ROLE_CSR')">
                        <c:if test="${order.status == 'NEW'}">
                            <a class="waves-effect waves-light btn" type="submit" id="csr_accept">accept
                                <i class="material-icons right">assignment_turned_in</i>
                            </a>
                        </c:if>
                    </sec:authorize>
                    <c:if test="${user.id == order.csr.id or user.userRole == 'ROLE_ADMIN'}">
                        <c:choose>
                            <c:when test="${order.status == 'PROCESSING'}">
                                <a class="waves-effect waves-light btn" id="csr_activate">activate
                                    <i class="material-icons right">done</i>
                                </a>
                            </c:when>
                            <c:when test="${order.status == 'REQUEST_TO_RESUME'}">
                                <a class="waves-effect waves-light btn" id="csr_resume">resume
                                    <i class="material-icons right">refresh</i>
                                </a>
                            </c:when>
                            <c:when test="${order.status == 'REQUEST_TO_PAUSE'}">
                                <a class="waves-effect waves-light btn" id="csr_pause">pause
                                    <i class="material-icons right">pause</i>
                                </a>
                            </c:when>
                            <c:when test="${order.status == 'REQUEST_TO_DISABLE'}">
                                <a class="waves-effect waves-light btn" id="csr_disable">disable
                                    <i class="material-icons right">archive</i>
                                </a>
                            </c:when>
                        </c:choose>
                    </c:if>
                </div>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                <input type="hidden" id="csrfToken" value="${_csrf.token}"/>
                <input type="hidden" id="csrfHeader" value="${_csrf.headerName}"/>
                <div class="section">
                    <c:choose>
                        <c:when test="${order.status == 'ACTIVE'}">
                            <a class="waves-effect waves-light btn" id="customer_pause">pause
                                <i class="material-icons right">pause</i>
                            </a>
                            <a class="waves-effect waves-light btn" id="customer_disable">disable
                                <i class="material-icons right">archive</i>
                            </a>
                        </c:when>
                        <c:when test="${order.status == 'PAUSED'}">
                            <a class="waves-effect waves-light btn" id="customer_resume">activate
                                <i class="material-icons right">done</i>
                            </a>
                            <a class="waves-effect waves-light btn" id="customer_disable">disable
                                <i class="material-icons right">archive</i>
                            </a>
                        </c:when>
                    </c:choose>
                </div>
            </sec:authorize>
        </div>
    </div>

    <a href="/order/${order.id}/report" class="waves-effect waves-light btn">Get PDF Report
        <i class="material-icons right">save</i>
    </a>
</div>
<div class="history z-depth-1">
    <ul class="collection" id="history">
    </ul>
</div>

<script>

    fetchHistory();

    function fetchHistory() {
        var orderId = ${order.id};
        $.get('/orders/' + orderId + '/history').success(function (data) {
            var historyUL = $('#history');
            historyUL.children().remove();
            $.each(data, function (i, item) {
                var li = $("<li>").addClass("collection-item")
                    .append("Status - <span>" + item.oldStatus + "</span>, time change status - <span class='date'>" + item.dateChangeStatus
                        + "</span>, message - " + item.descChangeStatus);
                historyUL.append(li);
            });
        });
    }
    //            CSR
    $('#csr_accept').on('click', function () {
        sendPut("/orders/" + ${order.id} +"/accept");
    });
    $('#csr_activate').on('click', function () {
        sendPut("/orders/" + ${order.id} +"/activate");
    });
    $('#csr_resume').on('click', function () {
        sendPut("/orders/" + ${order.id} +"/resume");
    });
    $('#csr_pause').on('click', function () {
        sendPut("/orders/" + ${order.id} +"/pause");
    });
    $('#csr_disable').on('click', function () {
        sendPut("/orders/" + ${order.id} +"/disable");
    });
    //    CUSTOMER
    $('#customer_pause').on('click', function () {
        sendPut("/orders/" + ${order.id} +"/request-pause");
    });
    $('#customer_disable').on('click', function () {
        sendPut("/orders/" + ${order.id} +"/request-disable");
    });
    $('#customer_resume').on('click', function () {
        sendPut("/orders/" + ${order.id} +"/request-resume");
    });
    //    PUT FUNCTION
    function sendPut(url) {
        var token = $('#csrfToken').val();
        var header = $('#csrfHeader').val();
        var xhr = $.ajax({
            type: 'PUT',
            url: url,
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            statusCode: {
                200: function (data) {
                    Materialize.toast(xhr.getResponseHeader("successMessage"), 10000);
                    fetchHistory();
                    $(window).trigger('hashchange');
                },
                400: function (data) {
                    Materialize.toast(xhr.getResponseHeader("errorMessage"), 10000);
                    $(window).trigger('hashchange');
                }
            }
        });
        return xhr;
    }


</script>