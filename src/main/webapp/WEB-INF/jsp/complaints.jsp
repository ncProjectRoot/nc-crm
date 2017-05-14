<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<sec:authentication var="user" property="principal"/>
<style>
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body" data-page-name="Complaints">
    <div class="col s12">
        <ul class="tabs" id="tabs">
            <sec:authorize access="hasAnyRole('ROLE_PMG, ROLE_ADMIN')">
                <li class="tab col s3"><a href="#swipe-all-complaints">All complaints</a></li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_PMG')">
                <li class="tab col s3"><a href="#swipe-pmg-complaints">Your complaints</a></li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_CUSTOMER')">
                <li class="tab col s3"><a href="#swipe-create-complaint-form">Create</a></li>
                <li class="tab col s3"><a href="#swipe-all-customer-complaints">My complaints</a></li>
            </sec:authorize>
        </ul>
    </div>
    <sec:authorize access="hasAnyRole('ROLE_PMG, ROLE_ADMIN')">
        <div id="swipe-all-complaints" class="col s12">
            <div id="table-all-complaints" class="table-container row">
                <div class="table-wrapper col s11 center-align">
                    <table class="striped responsive-table centered ">
                        <thead>
                        <tr>
                            <th data-field="1">
                                <a href="#!" class="sorted-element a-dummy">#</a>
                            </th>
                            <th data-field="2">
                                <a href="#!" class="sorted-element a-dummy">Title</a>
                            </th>
                            <th class="th-dropdown" data-field="statusId">
                                <a class='dropdown-button dropdown-status-button a-dummy' href='#'
                                   data-activates='dropdown-status' data-default-name="Status">
                                    Status
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-status" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="1">OPEN</a></li>
                                    <li><a href="#" class="a-dummy" data-value="2">SOLVING</a></li>
                                    <li><a href="#" class="a-dummy" data-value="3">CLOSED</a></li>
                                </ul>
                            </th>
                            <th data-field="3">
                                <a href="#!" class="sorted-element a-dummy">Customer</a>
                            </th>
                            <th data-field="4">
                                <a href="#!" class="sorted-element a-dummy">Order</a>
                            </th>
                            <th class="th-dropdown" data-field="orderStatusId">
                                <a class='dropdown-button a-dummy' href='#'
                                   data-activates='dropdown-order-status' data-default-name="Order Status">
                                    Order Status
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-order-status" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="4">New</a></li>
                                    <li><a href="#" class="a-dummy" data-value="6">Processing</a></li>
                                    <li><a href="#" class="a-dummy" data-value="7">Active</a></li>
                                    <li><a href="#" class="a-dummy" data-value="8">Disabled</a></li>
                                    <li><a href="#" class="a-dummy" data-value="9">Paused</a></li>
                                </ul>
                            </th>
                            <th data-field="5">
                                <a href="#!" class="sorted-element a-dummy">Product</a>
                            </th>
                            <th class="th-dropdown" data-field="productStatusId">
                                <a class='dropdown-button a-dummy' href='#'
                                   data-activates='dropdown-product-status' data-default-name="Product Status">
                                    Product Status
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-product-status" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="10">Planned</a></li>
                                    <li><a href="#" class="a-dummy" data-value="11">Actual</a></li>
                                    <li><a href="#" class="a-dummy" data-value="12">Outdated</a></li>
                                </ul>
                            </th>
                            <th data-field="6">
                                <a href="#!" class="sorted-element a-dummy">PMG</a>
                            </th>
                            <th data-field="7">
                                <a href="#!" class="sorted-element a-dummy">Date</a>
                            </th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </div>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_PMG')">
        <div id="swipe-pmg-complaints" class="col s12">
            <div id="table-pmg-complaints" class="table-container row">
                <div class="table-wrapper col s11 center-align">
                    <table class="striped responsive-table centered ">
                        <thead>
                        <tr>
                            <th data-field="1">
                                <a href="#!" class="sorted-element a-dummy">#</a>
                            </th>
                            <th data-field="2">
                                <a href="#!" class="sorted-element a-dummy">Title</a>
                            </th>
                            <th class="th-dropdown" data-field="statusId">
                                <a class='dropdown-button dropdown-status-button a-dummy' href='#'
                                   data-activates='dropdown-status-own' data-default-name="Status">
                                    Status
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-status-own" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="1">OPEN</a></li>
                                    <li><a href="#" class="a-dummy" data-value="2">SOLVING</a></li>
                                    <li><a href="#" class="a-dummy" data-value="3">CLOSED</a></li>
                                </ul>
                            </th>
                            <th data-field="3">
                                <a href="#!" class="sorted-element a-dummy">Customer</a>
                            </th>
                            <th data-field="4">
                                <a href="#!" class="sorted-element a-dummy">Order</a>
                            </th>
                            <th class="th-dropdown" data-field="orderStatusId">
                                <a class='dropdown-button a-dummy' href='#'
                                   data-activates='dropdown-order-status-own' data-default-name="Order Status">
                                    Order Status
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-order-status-own" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="4">New</a></li>
                                    <li><a href="#" class="a-dummy" data-value="6">Processing</a></li>
                                    <li><a href="#" class="a-dummy" data-value="7">Active</a></li>
                                    <li><a href="#" class="a-dummy" data-value="8">Disabled</a></li>
                                    <li><a href="#" class="a-dummy" data-value="9">Paused</a></li>
                                </ul>
                            </th>
                            <th data-field="5">
                                <a href="#!" class="sorted-element a-dummy">Product</a>
                            </th>
                            <th class="th-dropdown" data-field="productStatusId">
                                <a class='dropdown-button a-dummy' href='#'
                                   data-activates='dropdown-product-status-own' data-default-name="Product Status">
                                    Product Status
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-product-status-own" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="10">Planned</a></li>
                                    <li><a href="#" class="a-dummy" data-value="11">Actual</a></li>
                                    <li><a href="#" class="a-dummy" data-value="12">Outdated</a></li>
                                </ul>
                            </th>
                            <th data-field="7">
                                <a href="#!" class="sorted-element a-dummy">Date</a>
                            </th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </div>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_CUSTOMER')">
        <div id="swipe-all-customer-complaints" class="col s12">
            <div id="table-customer-complaints" class="table-container row">
                <div class="table-wrapper col s11 center-align">
                    <table class="striped responsive-table centered ">
                        <thead>
                        <tr>
                            <th data-field="1">
                                <a href="#!" class="sorted-element a-dummy">#</a>
                            </th>
                            <th data-field="2">
                                <a href="#!" class="sorted-element a-dummy">Title</a>
                            </th>
                            <th class="th-dropdown" data-field="statusId">
                                <a class='dropdown-button dropdown-status-button a-dummy' href='#'
                                   data-activates='dropdown-status-customer' data-default-name="Status">
                                    Status
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-status-customer" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="1">OPEN</a></li>
                                    <li><a href="#" class="a-dummy" data-value="2">SOLVING</a></li>
                                    <li><a href="#" class="a-dummy" data-value="3">CLOSED</a></li>
                                </ul>
                            </th>
                            <th data-field="4">
                                <a href="#!" class="sorted-element a-dummy">Order</a>
                            </th>
                            <th class="th-dropdown" data-field="orderStatusId">
                                <a class='dropdown-button a-dummy' href='#'
                                   data-activates='dropdown-order-status-customer' data-default-name="Order Status">
                                    Order Status
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-order-status-customer" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="4">New</a></li>
                                    <li><a href="#" class="a-dummy" data-value="6">Processing</a></li>
                                    <li><a href="#" class="a-dummy" data-value="7">Active</a></li>
                                    <li><a href="#" class="a-dummy" data-value="8">Disabled</a></li>
                                    <li><a href="#" class="a-dummy" data-value="9">Paused</a></li>
                                </ul>
                            </th>
                            <th data-field="5">
                                <a href="#!" class="sorted-element a-dummy">Product</a>
                            </th>
                            <th data-field="7">
                                <a href="#!" class="sorted-element a-dummy">Date</a>
                            </th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </div>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_CUSTOMER')">
        <div id="swipe-create-complaint-form" class="col s12">
            <div class="row">
                <form id="createComplaintForm" class="col s12" name="createComplaint">
                    <div class="row">
                        <div class="input-field col s6">
                            <i class="material-icons prefix">title</i>
                            <input id="title" type="text" class="validate" name="title" maxlength="50" data-length="50">
                            <label for="title">Title</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <i class="material-icons prefix">loyalty</i>
                            <input type="text" id="order-input" class="autocomplete">
                            <input type="hidden" id="order-hidden-input" name="orderId"/>
                            <label for="order-input">Order: <span id="selected-order"></span></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <i class="material-icons prefix">mode_edit</i>
                            <textarea name="message" id="message" class="materialize-textarea"
                                      maxlength="375" data-length="375"></textarea>
                            <label for="message">Message</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s6">
                            <button class="btn waves-effect waves-light" type="submit" name="action" id="createButton">
                                Create Copmplaint
                                <i class="material-icons right">send</i>
                            </button>
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </sec:authorize>
</div>
<%@ include file="/WEB-INF/jsp/component/tableScript.jsp" %>
<script>
    $('ul.tabs').tabs();
    $('select').material_select();
    $('input#title, textarea#message').characterCounter();

    $('#order-input').karpo_autocomplete({
        url: "/orders/users/${user.id}",
        label: "#selected-order",
        defaultValue: "",
        hideInput: "#order-hidden-input"
    });


    $("#createComplaintForm").on("submit", function (e) {
            e.preventDefault();
            var titleLenth = $("#title").val().length;
            var messageLenth = $("#message").val().length;
            var selectVal = $("#order-hidden-input").val();
            if (titleLenth < 5 || titleLenth > 50) {
                Materialize.toast("Title length must be 5-50", 5000, 'rounded');
            } else if (messageLenth < 5 || messageLenth > 375) {
                Materialize.toast("Description length must be 5-375", 5000, 'rounded');
            } else if (!(selectVal > 0)) {
                Materialize.toast("Order can't be empty", 5000, 'rounded');
            } else {
                $(".progress").addClass("progress-active");
                send('#createComplaintForm', '/complaints', "POST").done(function (id) {
                    $("#createComplaintForm")[0].reset();
                    $(".progress").removeClass("progress-active");
//                    Materialize.toast("Complaint with id " + data.id + " successfuly created", 2000, 'rounded');
                        window.location.href = "/#/complaints/" + id;
                    }
                );
            }
        }
    );

    <sec:authorize access="hasAnyRole('ROLE_PMG, ROLE_ADMIN')">
    $("#table-all-complaints").karpo_table({
        urlSearch: "/complaints/titles",
        urlTable: "/complaints/",
        mapper: function (object) {
            var tr = $("<tr>");
            tr.append($("<td>", {html: '<a href="#complaints/' + object.id + '">' + object.id + '</a>'}));
            tr.append($("<td>", {text: object.title}));
            tr.append($("<td>", {text: object.status}));
            tr.append($("<td>", {text: object.customer}));
            tr.append($("<td>", {text: object.order}));
            tr.append($("<td>", {text: object.orderStatus}));
            tr.append($("<td>", {text: object.productTitle}));
            tr.append($("<td>", {text: object.productStatus}));
            tr.append($("<td>", {text: object.pmg}));
            tr.append($("<td>", {text: object.date}));
            return tr;
        }
    });
    </sec:authorize>

    <sec:authorize access="hasAnyRole('ROLE_PMG')">
    $("#table-pmg-complaints").karpo_table({
        urlSearch: "/complaints/titles?userId=${user.id}",
        urlTable: "/complaints?userId=${user.id}",
        mapper: function (object) {
            var tr = $("<tr>");
            tr.append($("<td>", {html: '<a href="#complaints/' + object.id + '">' + object.id + '</a>'}));
            tr.append($("<td>", {text: object.title}));
            tr.append($("<td>", {text: object.status}));
            tr.append($("<td>", {text: object.customer}));
            tr.append($("<td>", {text: object.order}));
            tr.append($("<td>", {text: object.orderStatus}));
            tr.append($("<td>", {text: object.productTitle}));
            tr.append($("<td>", {text: object.productStatus}));
            tr.append($("<td>", {text: object.date}));
            return tr;
        }
    });
    </sec:authorize>

    <sec:authorize access="hasAnyRole('ROLE_CUSTOMER')">
    $("#table-customer-complaints").karpo_table({
        urlSearch: "/complaints/titles",
        urlTable: "/complaints?userId=${user.id}",
        mapper: function (object) {
            var tr = $("<tr>");
            tr.append($("<td>", {html: '<a href="#complaints/' + object.id + '">' + object.id + '</a>'}));
            tr.append($("<td>", {text: object.title}));
            tr.append($("<td>", {text: object.status}));
            tr.append($("<td>", {text: object.order}));
            tr.append($("<td>", {text: object.orderStatus}));
            tr.append($("<td>", {text: object.productTitle}));
            tr.append($("<td>", {text: object.date}));
            return tr;
        }
    });
    </sec:authorize>
</script>