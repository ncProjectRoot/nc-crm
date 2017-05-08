<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .collapsible-body {
        padding-bottom: 10px;
        padding-top: 10px;
        background: #f0f4ff;
    }

    .collapsible {
        margin: 10px;
    }

    .c_title {
        margin: 10px;
        font-size: 32px;
    }
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body" data-page-name="Complaint">
    <ul class="tabs" id="tabs">
        <li class="tab col s3"><a href="#swipe-complaint">Complaint</a></li>
        <li class="tab col s3"><a href="#swipe-history">History</a></li>
    </ul>
    <div id="swipe-complaint" class="col s12">
        <h4 class="c_title" id="title"><span># ${complaint.id} ${complaint.title}</span></h4>
        <div class="col s12">
            <ul class="collapsible" data-collapsible="expandable">
                <li>
                    <div class="collapsible-header"><i class="material-icons">whatshot</i>Status</div>
                    <div class="collapsible-body"><span>${complaint.status}</span></div>
                </li>
                <li>
                    <div class="collapsible-header"><i class="material-icons">place</i>Message</div>
                    <div class="collapsible-body"><p>${complaint.message.replaceAll("\\n", "<br>")}</p>
                    </div>
                </li>
                <li>
                    <div class="collapsible-header"><i class="material-icons">whatshot</i>Date</div>
                    <div class="collapsible-body"><span>${complaint.date}</span></div>
                </li>
                <li>
                    <div class="collapsible-header"><i class="material-icons">whatshot</i>Order</div>
                    <div class="collapsible-body"><span>#${complaint.order.id} ${complaint.order.product.title}</span>
                    </div>
                </li>
                <li>
                    <div class="collapsible-header"><i class="material-icons">whatshot</i>Customer</div>
                    <div class="collapsible-body">
                        <span>
                            #${complaint.customer.id} ${complaint.customer.firstName} ${complaint.customer.lastName}
                        </span>
                        <p>
                            <span>
                                ${complaint.customer.email}
                            </span>
                        </p>
                    </div>
                </li>
                <li>
                    <div class="collapsible-header"><i class="material-icons">whatshot</i>PMG</div>
                    <div class="collapsible-body"><span>
                        <c:if test="${complaint.pmg!=null}">
                            #${complaint.pmg.id} ${complaint.pmg.firstName} ${complaint.pmg.lastName}
                        </c:if>
                    </span></div>
                </li>
            </ul>
            <div class="row">
                <div class="col s6">
                    <button class="btn waves-effect waves-light" name="back" id="back">
                        Back
                        <i class="material-icons left">arrow_back</i>
                    </button>
                </div>
                <div class="col s6">
                    <button class="btn waves-effect waves-light" name="accept" id="accept">
                        Accept
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div id="swipe-history" class="col s12">
        <ul class="collection with-header">
            <li class="collection-header"><h4>First Names</h4></li>
            <li class="collection-item">Alvin</li>
            <li class="collection-item">Alvin</li>
            <li class="collection-item">Alvin</li>
            <li class="collection-item">Alvin</li>
        </ul>

        <div class="divider"></div>
        <div class="section">
            <h5>Section 1</h5>
            <p>Stuff</p>
        </div>
        <div class="divider"></div>
        <div class="section">
            <h5>Section 2</h5>
            <p>Stuff</p>
        </div>
        <div class="divider"></div>
        <div class="section">
            <h5>Section 3</h5>
            <p>Stuff</p>
        </div>

    </div>
</div>

<script>
    $(document).ready(function () {
        $('ul.tabs').tabs();
        $('.collapsible').collapsible();
        for (i = 0; i < 3; i++) {
            $('.collapsible').collapsible('open', i);
        }
    })

    $('#back').bind('click', function () {
        $(location).attr('href', '#complaints/');
    });

    $('#accept').bind('click', function () {
        $.ajaxSetup({
            complete: $(function () {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                $(document).ajaxSend(function (e, xhr, options) {
                    xhr.setRequestHeader(header, token);
                });
            })
        });
        $.post("/pmg/acceptComplaint", {
                complaintId: ${complaint.id},
            },
            function (data) {
                if (data === true) {
                    Materialize.toast("OK", 5000, 'rounded');
                } else if (data === false) {
                    Materialize.toast("WRONG", 5000, 'rounded');
                }
            }
        )
    });

    <%--<sec:authorize access="hasAnyRole('ROLE_CUSTOMER')">--%>
    <%--$("#table-customer-complaints").karpo_table({--%>
    <%--urlSearch: "/customer/load/complaintsNames",--%>
    <%--urlTable: "/customer/load/complaints",--%>
    <%--mapper: function (object) {--%>
    <%--var tr = $("<tr>");--%>
    <%--tr.append($("<td>", {text: object.id}));--%>
    <%--tr.append($("<td>", {text: object.title}));--%>
    <%--tr.append($("<td>", {text: object.status}));--%>
    <%--tr.append($("<td>", {text: object.order}));--%>
    <%--tr.append($("<td>", {text: object.orderStatus}));--%>
    <%--tr.append($("<td>", {text: object.productTitle}));--%>
    <%--tr.append($("<td>", {text: object.date}));--%>
    <%--return tr;--%>
    <%--}--%>
    <%--});--%>
    <%--</sec:authorize>--%>

</script>