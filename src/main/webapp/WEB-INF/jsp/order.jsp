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

    #date{
        text-decoration: underline;
    }
    .title {
        color: green;
    }

    .name {
        color: darkblue;
    }
</style>
<div class="content-body z-depth-1" data-page-name="Product #${order.id}">
    <a class="waves-effect waves-light btn-large" id="status">${order.status.name.replaceAll("_", " ")}</a>
    <div class="section">
        <h5>Product title : <span class="title">${order.product.title}</span></h5>
    </div>
    <div class="section">
        <h5>Customer : <span class="name">${order.customer.firstName} ${order.customer.lastName}</span></h5>
    </div>
    <div class="section">
        <h5>Order date : <span>${order.date.toString().replace("T", " ")}</span></h5>
    </div>
    <c:if test="${order.status == 'PROCESSING'}">
        <div class="section">
            <h5>Prefered date : <span>${order.preferedDate.toString().replace("T", " ")}</span></h5>
        </div>
    </c:if>

    <div class="row">
        <div class="section">
            <sec:authentication var="user" property="principal"/>
            <sec:authorize access="hasAnyRole('ROLE_CSR', 'ROLE_ADMIN')">
                <input type="hidden" id="csrfToken" value="${_csrf.token}"/>
                <input type="hidden" id="csrfHeader" value="${_csrf.headerName}"/>
                <div class="section">
                    <sec:authorize access="hasAnyRole('ROLE_CSR', 'ROLE_ADMIN')">
                        <c:if test="${order.status == 'NEW'}">
                            <a class="waves-effect waves-light btn" type="submit" id="csr_accept">accept</a>
                        </c:if>
                    </sec:authorize>
                    <c:if test="${user.id == order.csr.id}">
                        <c:choose>
                            <c:when test="${order.status == 'PROCESSING'}">
                                <a class="waves-effect waves-light btn" id="csr_activate">activate</a>
                            </c:when>
                            <c:when test="${order.status == 'REQUEST_TO_RESUME'}">
                                <a class="waves-effect waves-light btn" id="csr_resume">resume</a>
                            </c:when>
                            <c:when test="${order.status == 'REQUEST_TO_PAUSE'}">
                                <a class="waves-effect waves-light btn" id="csr_pause">pause</a>
                            </c:when>
                            <c:when test="${order.status == 'REQUEST_TO_DISABLE'}">
                                <a class="waves-effect waves-light btn" id="csr_disable">disable</a>
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
                            <a class="waves-effect waves-light btn" id="customer_pause">pause</a>
                            <a class="waves-effect waves-light btn" id="customer_disable">disable</a>
                        </c:when>
                        <c:when test="${order.status == 'PAUSED'}">
                            <a class="waves-effect waves-light btn" id="customer_resume">activate</a>
                            <a class="waves-effect waves-light btn" id="customer_disable">disable</a>
                        </c:when>
                    </c:choose>
                </div>
            </sec:authorize>
        </div>
    </div>

</div>
<div class="row">
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
                    .append("Status - <span>" + item.oldStatus + "</span>, time change status - <span id='date'>" + item.dateChangeStatus
                        + "</span>, message - " + item.descChangeStatus);
                historyUL.append(li);
            });
        });
    }
    //            CSR
    $('#csr_accept').on('click', function () {
        $('#csr_accept').remove();
        sendPut("/orders/" + ${order.id} +"/accept");
        $('#status').text('PROCESSING');
    });
    $('#csr_activate').on('click', function () {
        $('#csr_activate').remove();
        sendPut("/orders/" + ${order.id} +"/activate");
        $('#status').text('ACTIVE');
    });
    $('#csr_resume').on('click', function () {
        $('#csr_resume').remove();
        sendPut("/orders/" + ${order.id} +"/resume");
        $('#status').text('ACTIVE');
    });
    $('#csr_pause').on('click', function () {
        $('#csr_pause').remove();
        sendPut("/orders/" + ${order.id} +"/pause");
        $('#status').text('PAUSE');
    });
    $('#csr_disable').on('click', function () {
        $('#csr_disable').remove();
        sendPut("/orders/" + ${order.id} +"/disable");
        $('#status').text('DISABLE');
    });
    //    CUSTOMER
    $('#customer_pause').on('click', function () {
        $('#customer_pause').remove();
        $('#customer_disable').remove();
        sendPut("/orders/" + ${order.id} +"/request-pause");
        $('#status').text('REQUEST TO PAUSE');
    });
    $('#customer_disable').on('click', function () {
        $('#customer_disable').remove();
        $('#customer_resume').remove();
        $('#customer_pause').remove();
        sendPut("/orders/" + ${order.id} +"/request-disable");
        $('#status').text('REQUEST TO DISABLE');
    });
    $('#customer_resume').on('click', function () {
        $('#customer_resume').remove();
        $('#customer_disable').remove();
        sendPut("/orders/" + ${order.id} +"/request-resume");
        $('#status').text('REQUEST TO RESUME');
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
                },
                400: function (data) {
                    Materialize.toast(xhr.getResponseHeader("errorMessage"), 10000);
                }
            }
        });
        return xhr;
    }


</script>