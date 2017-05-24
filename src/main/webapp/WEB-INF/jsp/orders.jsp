<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style></style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body z-depth-1" data-page-name="Orders">

    <div class="row">
        <div class="col s12">
            <ul id="tabs" class="tabs">
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR', 'ROLE_PMG')">
                    <li class="tab col s3"><a class="active" href="#all-orders-wrapper">All Orders</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                    <li class="tab col s3"><a class="active" href="#my-orders-wrapper">History</a></li>
                </sec:authorize>
            </ul>
        </div>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR', 'ROLE_PMG')">
            <div id="all-orders-wrapper" class="col s12">
                <div id="table-all-orders" class="table-container row">
                    <div class="table-wrapper col s11 center-align">
                        <table class="striped responsive-table centered ">
                            <thead>
                            <tr>
                                <th data-field="1">
                                    <a href="#!" class="sorted-element a-dummy">#</a>
                                </th>
                                <th class="th-dropdown" data-field="statusId">
                                    <a class='dropdown-button dropdown-status-button a-dummy' href='#'
                                       data-activates='dropdown-all-status' data-default-name="Status">
                                        Status
                                    </a>
                                    <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                    <ul id="dropdown-all-status" class='dropdown-content'>
                                        <li><a href="#" class="a-dummy" data-value="4">New</a></li>
                                        <li><a href="#" class="a-dummy" data-value="5">Processing</a></li>
                                        <li><a href="#" class="a-dummy" data-value="6">Active</a></li>
                                        <li><a href="#" class="a-dummy" data-value="7">Disabled</a></li>
                                        <li><a href="#" class="a-dummy" data-value="8">Paused</a></li>
                                        <li><a href="#" class="a-dummy" data-value="9">Request resume</a></li>
                                        <li><a href="#" class="a-dummy" data-value="10">Request pause</a></li>
                                        <li><a href="#" class="a-dummy" data-value="11">Request disable</a></li>
                                    </ul>
                                </th>
                                <th data-field="2">
                                    <a href="#!" class="sorted-element a-dummy">Product Id</a>
                                </th>
                                <th data-field="3">
                                    <a href="#!" class="sorted-element a-dummy">Product Title</a>
                                </th>
                                <th class="th-dropdown" data-field="productStatusId">
                                    <a class='dropdown-button a-dummy' href='#'
                                       data-activates='dropdown-all-product-status' data-default-name="Product Status">
                                        Product Status
                                    </a>
                                    <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                    <ul id="dropdown-all-product-status" class='dropdown-content'>
                                        <li><a href="#" class="a-dummy" data-value="12">Planned</a></li>
                                        <li><a href="#" class="a-dummy" data-value="13">Actual</a></li>
                                        <li><a href="#" class="a-dummy" data-value="14">Outdated</a></li>
                                    </ul>
                                </th>
                                <th data-field="4">
                                    <a href="#!" class="sorted-element a-dummy">Customer</a>
                                </th>
                                <th data-field="5">
                                    <a href="#!" class="sorted-element a-dummy">CSR</a>
                                </th>
                                <th data-field="6">
                                    <a href="#!" class="sorted-element a-dummy">Date Finish</a>
                                </th>
                                <th data-field="7">
                                    <a href="#!" class="sorted-element a-dummy">Preferred Date</a>
                                </th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
            </div>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_CUSTOMER')">
            <div id="my-orders-wrapper" class="col s12">
                <div id="table-my-orders" class="table-container row">
                    <div class="table-wrapper col s11 center-align">
                        <table class="striped responsive-table centered ">
                            <thead>
                            <tr>
                                <th data-field="1">
                                    <a href="#!" class="sorted-element a-dummy">#</a>
                                </th>
                                <th class="th-dropdown" data-field="statusId">
                                    <a class='dropdown-button dropdown-status-button a-dummy' href='#'
                                       data-activates='dropdown-my-status' data-default-name="Status">
                                        Status
                                    </a>
                                    <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                    <ul id="dropdown-my-status" class='dropdown-content'>
                                        <li><a href="#" class="a-dummy" data-value="4">New</a></li>
                                        <li><a href="#" class="a-dummy" data-value="5">Processing</a></li>
                                        <li><a href="#" class="a-dummy" data-value="6">Active</a></li>
                                        <li><a href="#" class="a-dummy" data-value="7">Disabled</a></li>
                                        <li><a href="#" class="a-dummy" data-value="8">Paused</a></li>
                                        <li><a href="#" class="a-dummy" data-value="9">Request resume</a></li>
                                        <li><a href="#" class="a-dummy" data-value="10">Request pause</a></li>
                                        <li><a href="#" class="a-dummy" data-value="11">Request disable</a></li>
                                    </ul>
                                </th>
                                <th data-field="2">
                                    <a href="#!" class="sorted-element a-dummy">Product Id</a>
                                </th>
                                <th data-field="3">
                                    <a href="#!" class="sorted-element a-dummy">Product Title</a>
                                </th>
                                <th class="th-dropdown" data-field="productStatusId">
                                    <a class='dropdown-button a-dummy' href='#'
                                       data-activates='dropdown-my-product-status' data-default-name="Product Status">
                                        Product Status
                                    </a>
                                    <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                    <ul id="dropdown-my-product-status" class='dropdown-content'>
                                        <li><a href="#" class="a-dummy" data-value="12">Planned</a></li>
                                        <li><a href="#" class="a-dummy" data-value="13">Actual</a></li>
                                        <li><a href="#" class="a-dummy" data-value="14">Outdated</a></li>
                                    </ul>
                                </th>
                                <th data-field="5">
                                    <a href="#!" class="sorted-element a-dummy">CSR</a>
                                </th>
                                <th data-field="6">
                                    <a href="#!" class="sorted-element a-dummy">Date Finish</a>
                                </th>
                                <th data-field="7">
                                    <a href="#!" class="sorted-element a-dummy">Preferred Date</a>
                                </th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>

                </div>


            </div>
        </sec:authorize>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/component/tableScript.jsp" %>
<script>

    $('ul#tabs').tabs();

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR', 'ROLE_PMG')">
    $("#table-all-orders").karpo_table({
        urlSearch: "/products/autocomplete?type=all",
        urlTable: "/orders",
        mapper: function (object) {
            var tr = $("<tr>");
            tr.append($("<td>").append($("<a>", {
                text: object.id,
                href: "#order/" + object.id
            })));
            tr.append($("<td>", {text: object.status}));
            tr.append($("<td>").append($("<a>", {
                text: object.productId,
                href: "#product/" + object.productId
            })));
            tr.append($("<td>", {text: object.productTitle}));
            tr.append($("<td>", {text: object.productStatus}));
            tr.append($("<td>").append($("<a>", {
                href: "#user/" + object.customer,
                text: object.customer
            })));
            tr.append($("<td>").append($("<a>", {
                href: "#user/" + object.csr,
                text: object.csr
            })));
            tr.append($("<td>", {text: object.dateFinish}));
            tr.append($("<td>", {text: object.preferredDate}));
            return tr;
        }
    });
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_CUSTOMER')">
    $("#table-my-orders").karpo_table({
        urlSearch: "/products/autocomplete?type=actual",
        urlTable: "/orders",
        mapper: function (object) {
            var tr = $("<tr>");
            tr.append($("<td>").append($("<a>", {
                text: object.id,
                href: "#order/" + object.id
            })));
            tr.append($("<td>", {text: object.status}));
            tr.append($("<td>").append($("<a>", {
                text: object.productId,
                href: "#product/" + object.productId
            })));
            tr.append($("<td>", {text: object.productTitle}));
            tr.append($("<td>", {text: object.productStatus}));
            tr.append($("<td>", {text: object.csr}));
            tr.append($("<td>", {text: object.dateFinish}));
            tr.append($("<td>", {text: object.preferredDate}));
            return tr;
        }
    });
    </sec:authorize>

</script>