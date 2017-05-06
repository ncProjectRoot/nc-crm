<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body" data-page-name="Products">
    <div class="row">

        <div class="col s12">
            <ul id="tabs" class="tabs">
                <li class="tab col s3"><a class="active" href="#all-product-wrapper">All Products</a></li>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
                    <li class="tab col s3"><a  href="#create-wrapper">Create</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                    <li class="tab col s3"><a class="active" href="#my-product-wrapper">My Products</a></li>
                </sec:authorize>
            </ul>
        </div>
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
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
            <div id="create-wrapper" class="col s12">
                <div class="row">
                    <form class="col s12" id="addProduct">
                        <div class="row">
                            <div class='input-field col s6'>
                                <i class="material-icons prefix">title</i>
                                <label for="title">Title</label>
                                <input class="validate" id="title" type="text" name="title">
                            </div>
                            <div class="input-field col s3">
                                <i class="material-icons prefix">add_shopping_cart</i>
                                <select name="discountId" id="select_disc">
                                    <option value="0">Default</option>
                                </select>
                                <label for="select_disc">Choose discount</label>
                            </div>
                            <div class="input-field col s3">
                                <i class="material-icons prefix">search</i>
                                <input class='validate' type='text' onkeyup="fetchDiscounts()" id='search_title'/>
                                <label for="search_title">Search discount <span id="discount_numbers"></span></label>
                            </div>
                        </div>
                        <div class="row">
                            <div class='input-field col s6'>
                                <i class="material-icons prefix">euro_symbol</i>
                                <input class='validate' type='number' name='defaultPrice' id='price'/>
                                <label for="price">Price</label>
                            </div>
                            <div class="input-field col s3">
                                <i class="material-icons prefix">add</i>
                                <select name="groupId" id="select_group">
                                    <option value="0">Default</option>
                                </select>
                                <label for="select_group">Choose group</label>
                            </div>
                            <div class="input-field col s3">
                                <i class="material-icons prefix">search</i>
                                <input class='validate' type='text' onkeyup="fetchGroups()" id='search_group'/>
                                <label for="search_group">Search group <span id="group_numbers"></span></label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s6">
                                <i class="material-icons prefix">cached</i>
                                <select name="statusName" id="select_product_status">
                                </select>
                                <label for="select_disc">Choose product status</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12">
                                <i class="material-icons prefix">description</i>
                                <textarea id="descProduct" name="description" class="materialize-textarea"></textarea>
                                <label for="descProduct">Description</label>
                            </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="row">
                            <div class="col s6">
                                <button class="btn waves-effect waves-light" type="submit" name="action">Create Product
                                    <i class="material-icons right">send</i>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_CUSTOMER')">
            <div id="my-product-wrapper" class="col s12">

            </div>
        </sec:authorize>


    </div>
</div>
<%@ include file="/WEB-INF/jsp/component/tableScript.jsp" %>
<script>

    $('ul#tabs').tabs({
        onShow: function (tab) {
        }
    });

    //////// create ////////

    $('select').material_select();

    function fetchDiscounts() {
        var title = $('#search_title').val();
        if (title.length > 1) {
            $('#select_disc').children().remove();
            $.get("/csr/discountByTitle/" + title).success(function (data) {
                $('#select_disc').append($('<option value="0">Default</option>'));
                $.each(data, function (i, item) {
                    $('#select_disc').append($('<option/>', {
                        value: item.id,
                        text: item.title + ' - ' + item.percentage + '%'
                    }));
                });
                $('#select_disc').material_select('updating');
                $('#discount_numbers').html(", results " + data.length);
            });
        }
    }

    function fetchGroups() {
        var title = $('#search_group').val();
        if (title.length > 1) {
            $('#select_group').children().remove();
            $.get("/csr/groupByName/" + title).success(function (data) {
                $('#select_group').append($('<option value="0">Default</option>'));
                $.each(data, function (i, item) {
                    console.log(item);
                    $('#select_group').append($('<option/>', {
                        value: item.id,
                        text: item.name
                    }));
                });
                $('#select_group').material_select('updating');
                $('#group_numbers').html(", results " + data.length);
            });
        }
    }

    //      load product status
    $.get("/csr/load/productStatus/").success(function (data) {
        $.each(data, function (i, item) {
            $('#select_product_status').append($('<option>', {
                value: item.name,
                text: item.name
            }));
        });
        $('#select_product_status').material_select('updating');
    });

    //        create product
    $("#addProduct").on("submit", function (e) {
        e.preventDefault();
        var title = $('#title').val();
        var price = $('#price').val();
        if (title.length < 5) {
            Materialize.toast("Please enter title at least 5 characters", 10000, 'rounded');
        } else if (price < 1) {
            Materialize.toast("Please enter price more 0", 10000, 'rounded');
        } else {
            $.post("/csr/addProduct", $("#addProduct").serialize(), function (data) {
                $("#addProduct")[0].reset();
                Materialize.toast(data, 10000, 'rounded');
            });
            loadProductsWithoutGroup();
        }
    });




    //////// all ////////



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