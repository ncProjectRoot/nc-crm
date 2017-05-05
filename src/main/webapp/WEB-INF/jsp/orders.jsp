<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    #content-body-wrapper {
        width: calc(100% - 20px * 2);
        margin: 0 auto;
        margin-top: 15px;
        background-color: #fff;
    }
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-header z-depth-1 valign-wrapper">
    <i class="black-text material-icons">receipt</i>
    <span>Orders</span>
</div>
<div id="content-body-wrapper">
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
                           data-activates='dropdown-status' data-default-name="Status">
                            Status
                        </a>
                        <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                        <ul id="dropdown-status" class='dropdown-content'>
                            <li><a href="#" class="a-dummy" data-value="4">New</a></li>
                            <li><a href="#" class="a-dummy" data-value="5">In queue</a></li>
                            <li><a href="#" class="a-dummy" data-value="6">Processing</a></li>
                            <li><a href="#" class="a-dummy" data-value="7">Active</a></li>
                            <li><a href="#" class="a-dummy" data-value="8">Disabled</a></li>
                            <li><a href="#" class="a-dummy" data-value="9">Paused</a></li>
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
<%@ include file="/WEB-INF/jsp/component/tableScript.jsp" %>
<script>

    $("#table-all-orders").karpo_table({
        urlSearch: "/csr/load/productNames",
        urlTable: "/csr/load/orders",
        mapper: function (object) {
            var tr = $("<tr>");
            tr.append($("<td>", {text: object.id}));
            tr.append($("<td>", {text: object.status}));
            tr.append($("<td>", {text: object.productId}));
            tr.append($("<td>", {text: object.productTitle}));
            tr.append($("<td>", {text: object.productStatus}));
            tr.append($("<td>", {text: object.customer}));
            tr.append($("<td>", {text: object.csr}));
            tr.append($("<td>", {text: object.dateFinish}));
            tr.append($("<td>", {text: object.preferredDate}));
            return tr;
        }
    });

</script>