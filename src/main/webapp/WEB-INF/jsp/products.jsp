<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body" data-page-name="Products">
    <div id="content-body" class="row">

        <div class="col s12">
            <ul id="tabs" class="tabs">
                <sec:authorize access="hasRole('ROLE_CSR')">
                    <li class="tab col s3"><a  href="#create-wrapper">Create</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                    <li class="tab col s3"><a class="active" href="#my-product-wrapper">My Products</a></li>
                </sec:authorize>
                <li class="tab col s3"><a class="active" href="#all-product-wrapper">All Products</a></li>
            </ul>
        </div>
        <sec:authorize access="hasRole('ROLE_CSR')">
            <div id="create-wrapper" class="col s12">Create</div>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_CUSTOMER')">
            <div id="my-product-wrapper" class="col s12">My Products</div>
        </sec:authorize>
        <div id="all-product-wrapper" class="col s12">

            <div id="table-all-products" class="table-container row">
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
                            <th data-field="3">
                                <a href="#!" class="sorted-element a-dummy">Price</a>
                            </th>
                            <th class="th-dropdown" data-field="statusId">
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
                            <th data-field="4">
                                <a href="#!" class="sorted-element a-dummy">Discount</a>
                            </th>
                            <th data-field="5">
                                <a href="#!" class="sorted-element a-dummy">Percentage</a>
                            </th>
                            <th class="th-dropdown" data-field="discountActive">
                                <a class='dropdown-button a-dummy' href='#'
                                   data-activates='dropdown-discount-status' data-default-name="Discount Active">
                                    Discount Active
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-discount-status" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="true">true</a></li>
                                    <li><a href="#" class="a-dummy" data-value="false">false</a></li>
                                </ul>
                            </th>
                            <th data-field="6">
                                <a href="#!" class="sorted-element a-dummy">Group</a>
                            </th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>


        </div>

    </div>
</div>
<%@ include file="/WEB-INF/jsp/component/tableScript.jsp" %>
<script>

    $('ul#tabs').tabs({
        onShow: function (tab) {
            console.log(tab);
        }
    });

    $("#table-all-products").karpo_table({
        urlSearch: "/csr/load/productNames",
        urlTable: "/csr/load/products",
        mapper: function (object) {
            var tr = $("<tr>");
            tr.append($("<td>", {text: object.id}));
            tr.append($("<td>", {text: object.title}));
            tr.append($("<td>", {text: object.price}));
            tr.append($("<td>", {text: object.status}));
            tr.append($("<td>", {text: object.discount}));
            tr.append($("<td>", {text: object.percentage}));
            tr.append($("<td>", {text: object.discountActive}));
            tr.append($("<td>", {text: object.group}));
            return tr;
        }
    });

</script>