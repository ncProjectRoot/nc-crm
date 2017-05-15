<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body" data-page-name="Products">
    <div class="row">

        <div class="col s12">
            <ul id="tabs" class="tabs">
                <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                    <li class="tab col s3"><a class="active" href="#my-product-wrapper">My Products</a></li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
                    <li class="tab col s3"><a class="active" href="#all-product-wrapper">All Products</a></li>
                    <li class="tab col s3"><a href="#create-wrapper">Create</a></li>
                </sec:authorize>
            </ul>
        </div>
        <sec:authorize access="hasRole('ROLE_CUSTOMER')">
            <div id="my-product-wrapper" class="col s12">
                <div id="table-my-products" class="table-container row">
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
                                <th data-field="4">
                                    <a href="#!" class="sorted-element a-dummy">Discount</a>
                                </th>
                                <th data-field="5">
                                    <a href="#!" class="sorted-element a-dummy">Percentage</a>
                                </th>
                                <th class="th-dropdown" data-field="discountActive">
                                    <a class='dropdown-button a-dummy' href='#'
                                       data-activates='dropdown-my-discount-status' data-default-name="Discount Active">
                                        Discount Active
                                    </a>
                                    <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                    <ul id="dropdown-my-discount-status" class='dropdown-content'>
                                        <li><a href="#" class="a-dummy" data-value="true">True</a></li>
                                        <li><a href="#" class="a-dummy" data-value="false">False</a></li>
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
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
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
                                <th class="th-dropdown" data-field="statusId">
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
                                <th data-field="3">
                                    <a href="#!" class="sorted-element a-dummy">Price</a>
                                </th>
                                <th data-field="4">
                                    <a href="#!" class="sorted-element a-dummy">Discount</a>
                                </th>
                                <th data-field="5">
                                    <a href="#!" class="sorted-element a-dummy">Percentage</a>
                                </th>
                                <th class="th-dropdown" data-field="discountActive">
                                    <a class='dropdown-button a-dummy' href='#'
                                       data-activates='dropdown-all-discount-status' data-default-name="Discount Active">
                                        Discount Active
                                    </a>
                                    <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                    <ul id="dropdown-all-discount-status" class='dropdown-content'>
                                        <li><a href="#" class="a-dummy" data-value="true">True</a></li>
                                        <li><a href="#" class="a-dummy" data-value="false">False</a></li>
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

            <div id="create-wrapper" class="col s12">
                <div class="row">
                    <form class="col s12" id="addProduct">
                        <div class="row">
                            <div class='input-field col s6'>
                                <i class="material-icons prefix">title</i>
                                <label for="title">Title</label>
                                <input class="validate" id="title" type="text" name="title">
                            </div>
                            <div class="input-field col s6">
                                <i class="material-icons prefix">bubble_chart</i>
                                <input type="text" id="group-input" class="autocomplete">
                                <input type="hidden" id="group-hidden-input" name="groupId"/>
                                <label for="group-input">Selected group: <span id="selected-group"></span></label>
                            </div>
                        </div>
                        <div class="row">
                            <div class='input-field col s6'>
                                <i class="material-icons prefix">attach_money</i>
                                <input class='validate' type='number' name='defaultPrice' id='price'/>
                                <label for="price">Price</label>
                            </div>
                            <div class="input-field col s6">
                                <i class="material-icons prefix">loyalty</i>
                                <input type="text" id="discount-input" class="autocomplete">
                                <input type="hidden" id="discount-hidden-input" name="discountId"/>
                                <label for="discount-input">Selected discount: <span id="selected-discount"></span></label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s6">
                                <i class="material-icons prefix">cached</i>
                                <select name="statusName" id="select_product_status">
                                    <option value="PLANNED" data-value="11" data-after-disabled="12">PLANNED</option>
                                    <option value="ACTUAL" data-value="12" data-after-disabled="13">ACTUAL</option>
                                    <option value="OUTDATED" data-value="14">OUTDATED</option>
                                </select>
                                <label for="select_product_status">Choose product status</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12">
                                <i class="material-icons prefix">description</i>
                                <textarea id="descProduct" name="description" class="materialize-textarea" data-length="400"></textarea>
                                <label for="descProduct">Description</label>
                            </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="row">
                            <div class="col s6">
                                <button class="btn waves-effect waves-light" id="submit-product" type="submit" name="action">Create Product
                                    <i class="material-icons right">send</i>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
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

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">

    //////// create product ////////

    $('#select_product_status').karpo_status(10).disabled(12);
    $('#discount-input').karpo_autocomplete({
        url: "/discounts/csr/discountByTitle/",
        label: "#selected-discount",
        defaultValue: "${product.discount.id} ${product.discount.title}",
        hideInput: "#discount-hidden-input"
    });
    $('#group-input').karpo_autocomplete({
        url: "/csr/groupByName/",
        label: "#selected-group",
        defaultValue: "${product.group.id} ${product.group.name}",
        hideInput: "#group-hidden-input"
    });
    $('select').material_select();

    function fetchDiscounts() {
        var title = $('#search_title').val();
        if (title.length > 1) {
            $('#select_disc').children().remove();
            $.get("/discounts/" + title).success(function (data) {
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

    $('.materialize-textarea').trigger('autoresize');
    Materialize.updateTextFields();


    $("#addProduct").on("submit", function (e) {
        e.preventDefault();
        var title = $('#title').val();
        var price = $('#price').val();
        if (title.length < 5) {
            Materialize.toast("Please enter title at least 5 characters", 10000, 'rounded');
        } else if (price < 1) {
            Materialize.toast("Please enter price more 0", 10000, 'rounded');
        } else {
            
            var url = "/csr/addProduct";
            var form = "#addProduct";
            sendPost(form, url).done(function (productId) {
                if (productId) {
                    location.hash = '#product?id=' + productId;                    
                }     
            })
            loadProductsWithoutGroup(); 
        }
    });

    /*$("#submit-product").on("click", function () {
        event.preventDefault();
        var url = "/csr/addProduct";
        var form = "#addProduct";
        sendPost(form, url);
        $(form)[0].reset();
        loadProductsWithoutGroup();
    });*/


    //////// all ////////


    $("#table-all-products").karpo_table({
        urlSearch: "/csr/load/productNames",
        urlTable: "/csr/load/products",
        mapper: function (object) {
            var disActive = null;
            var tr = $("<tr>");
            tr.append($("<td>").append($("<a>", {
                text: object.id,
                href: "#product?id=" + object.id
            })));
            tr.append($("<td>", {text: object.title}));
            tr.append($("<td>", {text: object.status}));
            tr.append($("<td>", {text: object.price}));
            tr.append($("<td>", {text: object.discountTitle}));
            tr.append($("<td>", {text: object.percentage ? object.percentage + "%": ""}));
            if(object.discountActive != null)
                disActive = (object.discountActive == true) ? "<i class='material-icons prefix'>check</i>" : "<i class='material-icons prefix'>clear</i>";            
            tr.append($("<td>", {html: disActive}));
            tr.append($("<td>", {text: object.groupName}));
            return tr;
        }
    });

    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_CUSTOMER')">
    $("#table-my-products").karpo_table({
        urlSearch: "/customer/load/actualProductNames",
        urlTable: "/customer/load/products",
        mapper: function (object) {
            var disActive = null;
            var tr = $("<tr>");
            tr.append($("<td>").append($("<a>", {
                text: object.id,
                href: "#product?id=" + object.id
            })));
            tr.append($("<td>", {text: object.title}));
            tr.append($("<td>", {text: object.price}));
            tr.append($("<td>", {text: object.discountTitle}));
            tr.append($("<td>", {text: object.percentage ? object.percentage + "%": ""}));
            if(object.discountActive != null)
                disActive = (object.discountActive == true) ? "<i class='material-icons prefix'>check</i>" : "<i class='material-icons prefix'>clear</i>";            
            tr.append($("<td>", {html: disActive}));
            tr.append($("<td>", {text: object.groupName}));
            return tr;
        }
    });
    </sec:authorize>

</script>