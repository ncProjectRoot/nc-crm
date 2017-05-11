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

    .welcome-h1 {
        font-size: 3.2rem;
    }

    .welcome-h2 {
        font-size: 2.8rem;
    }
</style>
<div class="content-body z-depth-1" data-page-name="Product #${order.id}">
    <div class="col s15">
        <div class="row">
            <a class="waves-effect waves-light btn-large disabled" id="new">NEW</a>
            <a class="waves-effect waves-light btn-large disabled" id="processing">PROCESSING</a>
            <a class="waves-effect waves-light btn-large disabled" id="active">ACTIVE</a>
            <a class="waves-effect waves-light btn-large disabled" id="paused">PAUSED</a>
            <a class="waves-effect waves-light btn-large disabled" id="disabled">DISABLED</a>
        </div>
        <div class="row">
            <a class="waves-effect waves-light btn-large disabled" id="request_to_active">REQUEST TO ACTIVE</a>
            <a class="waves-effect waves-light btn-large disabled" id="request_to_pause">REQUEST TO PAUSE</a>
            <a class="waves-effect waves-light btn-large disabled" id="request_to_disable">REQUEST TO DISABLE</a>
        </div>

    </div>
    <h1 class="welcome-h1">${order.product.title}</h1>
    <h2 class="welcome-h2">${order.customer.firstName} ${order.customer.lastName}</h2>

    <div class="row">
        <div class="section">
            <sec:authentication var="user" property="principal" />
            <sec:authorize access="hasRole('ROLE_CSR')">
                <c:if test="${user.id == order.csr.id}">
                    <input type="hidden" id="csrfToken" value="${_csrf.token}"/>
                    <input type="hidden" id="csrfHeader" value="${_csrf.headerName}"/>
                    <c:choose>
                        <c:when test="${order.status == 'NEW'}">
                            <a class="waves-effect waves-light btn" type="submit" id="csr_accept">accept</a>
                        </c:when>
                        <c:when test="${order.status == 'PROCESSING'}">
                            <a class="waves-effect waves-light btn" id="csr_activate">activate</a>
                        </c:when>
                        <%--<c:when test="${order.status == 'ACTIVE'}">--%>

                        <%--</c:when>--%>
                        <%--<c:when test="${order.status == 'PAUSED'}">--%>

                        <%--</c:when>--%>
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
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                <input type="hidden" id="csrfToken" value="${_csrf.token}"/>
                <input type="hidden" id="csrfHeader" value="${_csrf.headerName}"/>
                <c:choose>
                    <%--<c:when test="${order.status == 'NEW'}">--%>

                    <%--</c:when>--%>
                    <%--<c:when test="${order.status == 'PROCESSING'}">--%>

                    <%--</c:when>--%>
                    <c:when test="${order.status == 'ACTIVE'}">
                        <a class="waves-effect waves-light btn" id="customer_pause">pause</a>
                        <a class="waves-effect waves-light btn" id="customer_disable">disable</a>
                    </c:when>
                    <c:when test="${order.status == 'PAUSED'}">
                        <a class="waves-effect waves-light btn" id="customer_resume">activate</a>
                        <a class="waves-effect waves-light btn" id="customer_disable">disable</a>
                    </c:when>
                    <%--<c:when test="${order.status == 'REQUEST_TO_PAUSE'}">--%>

                    <%--</c:when>--%>
                    <%--<c:when test="${order.status == 'REQUEST_TO_RESUME'}">--%>

                    <%--</c:when>--%>
                    <%--<c:when test="${order.status == 'REQUEST_TO_DISABLE'}">--%>

                    <%--</c:when>--%>
                </c:choose>
            </sec:authorize>
        </div>
    </div>

    <c:choose>
        <c:when test="${order.status == 'NEW'}">
            <script>$('#new').removeClass('disabled');</script>
        </c:when>
        <c:when test="${order.status == 'PROCESSING'}">
            <script>$('#processing').removeClass('disabled');</script>
        </c:when>
        <c:when test="${order.status == 'ACTIVE'}">
            <script>$('#active').removeClass('disabled');</script>
        </c:when>
        <c:when test="${order.status == 'PAUSED'}">
            <script>$('#paused').removeClass('disabled');</script>
        </c:when>
        <c:when test="${order.status == 'DISABLED'}">
            <script>$('#disabled').removeClass('disabled');</script>
        </c:when>
        <c:when test="${order.status == 'REQUEST_TO_RESUME'}">
            <script>$('#request_to_active').removeClass('disabled');</script>
        </c:when>
        <c:when test="${order.status == 'REQUEST_TO_PAUSE'}">
            <script>$('#request_to_pause').removeClass('disabled');</script>
        </c:when>
        <c:when test="${order.status == 'REQUEST_TO_DISABLE'}">
            <script>$('#request_to_disable').removeClass('disabled');</script>
        </c:when>
    </c:choose>
</div>
<script>
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
            type : 'PUT',
            url : url,
            dataType : 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            statusCode: {
                200: function (data) {
                    Materialize.toast(xhr.getResponseHeader("successMessage"), 10000);
                }
            }
        });
        return xhr;
    }





</script>