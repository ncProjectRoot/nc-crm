<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    .modal.modal-fixed-footer {
        max-height: 85%;
        height: 98%;
    }

    .modal .modal-footer .btn {
        float: none;
    }

    .modal-content h4 {
        margin-top: 20px;
    }

    td.discount {
        position: relative;
    }

    td.discount .old-price {
        text-decoration: line-through;
        opacity: 0.6;
        position: relative;
        top: 17px;
        left: -23px;
    }

    td.discount .new-price {
        position: relative;
        margin: 10px;
        font-size: 18px;
        right: 0;
    }

    td.discount .percentage {
        transition: all 1s cubic-bezier(0,1.4,1,1.4);
        display: inline-block;
        width: 50px;
        height: 50px;
        border: 2px solid #D32F2F;
        line-height: 47px;
        text-align: center;
        font-size: 18px;
        position: absolute;
        border-radius: 30px;
        top: -3px;
        right: 9px;
        opacity: 0.7;
        transform: scale(0);
    }

    #order-product-wrapper td.discount .percentage {
        right: 58px;
    }

    td.discount .percentage.show{
        transform: scale(0.7);
    }
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body z-depth-1" data-page-name="Products">
    <div class="row">

        <div class="col s12">
            <ul id="tabs" class="tabs">
                <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                    <li class="tab col s3"><a class="active" href="#order-product-wrapper">Order Product</a></li>
                    <li class="tab col s3"><a class="active" href="#my-product-wrapper">My Active Products</a></li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
                    <li class="tab col s3"><a class="active" href="#all-product-wrapper">All Products</a></li>
                    <li class="tab col s3"><a href="#create-wrapper">Create</a></li>
                </sec:authorize>
            </ul>
        </div>
        <sec:authorize access="hasRole('ROLE_CUSTOMER')">
            <div id="order-product-wrapper" class="col s12">
                <div id="table-create-orders" class="table-container row">
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
                                <th data-field="5">
                                    <a href="#!" class="sorted-element a-dummy">Group</a>
                                </th>
                                <th>
                                    <a href="#!" class="a-dummy">Action</a>
                                </th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
            </div>
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
                                    <a href="#!" class="sorted-element a-dummy">Product Discount</a>
                                </th>
                                <th data-field="5">
                                    <a href="#!" class="sorted-element a-dummy">Group</a>
                                </th>
                                <th data-field="6">
                                    <a href="#!" class="sorted-element a-dummy">Group Discount</a>
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
                        <table class="striped responsive-table centered bulk-table">
                            <thead>
                            <tr>
                                <th class="hide-on-med-and-down"></th>
                                <th>
                                    <p>
                                        <input type='checkbox' class='filled-in bulk-select-all'
                                               id="select-all-checkbox"/>
                                        <label for='select-all-checkbox'></label>
                                    </p>
                                </th>
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
                                    <a href="#!" class="sorted-element a-dummy">P Discount</a>
                                </th>
                                <th class="th-dropdown" data-field="discountActive">
                                    <a class='dropdown-button a-dummy' href='#'
                                       data-activates='dropdown-all-discount-status'
                                       data-default-name="Active">
                                        Active
                                    </a>
                                    <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                    <ul id="dropdown-all-discount-status" class='dropdown-content'>
                                        <li><a href="#" class="a-dummy" data-value="true">True</a></li>
                                        <li><a href="#" class="a-dummy" data-value="false">False</a></li>
                                    </ul>
                                </th>
                                <th data-field="5">
                                    <a href="#!" class="sorted-element a-dummy">Group</a>
                                </th>
                                <th data-field="6">
                                    <a href="#!" class="sorted-element a-dummy">G Discount</a>
                                </th>
                                <th class="th-dropdown" data-field="groupDiscountActive">
                                    <a class='dropdown-button a-dummy' href='#'
                                       data-activates='dropdown-all-group-discount-status'
                                       data-default-name="Active">
                                        Active
                                    </a>
                                    <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                    <ul id="dropdown-all-group-discount-status" class='dropdown-content'>
                                        <li><a href="#" class="a-dummy" data-value="true">True</a></li>
                                        <li><a href="#" class="a-dummy" data-value="false">False</a></li>
                                    </ul>
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
                            <div class='input-field col s12 m6'>
                                <i class="material-icons prefix">title</i>
                                <label for="title">Title</label>
                                <input class="validate" id="title" type="text" name="title">
                            </div>
                            <div class="input-field col s12 m6">
                                <i class="material-icons prefix">bubble_chart</i>
                                <input type="text" id="group-input" class="autocomplete">
                                <input type="hidden" id="group-hidden-input" name="groupId"/>
                                <label for="group-input">Selected group: <span id="selected-group"></span></label>
                            </div>
                        </div>
                        <div class="row">
                            <div class='input-field col s12 m6'>
                                <i class="material-icons prefix">attach_money</i>
                                <input class='validate' type='number' name='defaultPrice' id='price'/>
                                <label for="price">Price</label>
                            </div>
                            <div class="input-field col s12 m6">
                                <i class="material-icons prefix">loyalty</i>
                                <input type="text" id="discount-input" class="autocomplete">
                                <input type="hidden" id="discount-hidden-input" name="discountId"/>
                                <label for="discount-input">Selected discount: <span
                                        id="selected-discount"></span></label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12">
                                <i class="material-icons prefix">description</i>
                                <textarea id="descProduct" name="description" class="materialize-textarea"
                                          data-length="400"></textarea>
                                <label for="descProduct">Description</label>
                            </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="row">
                            <div class="col s12 m6">
                                <button class="btn waves-effect waves-light" id="submit-product" type="submit"
                                        name="action">Create Product
                                    <i class="material-icons right">send</i>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <div id="bulk-change-modal" class="modal bottom-sheet">
                <div class="modal-content">
                    <div class="row">
                        <div id="bulk-change-modal-title" class="col s3 offset-s2">
                            <h4>Edit Selected Items</h4>
                            <p>Choose field to edit it for each selected item.</p>
                            <div class="chip bulk-chip" checkbox-id="checkbox-status">Status<i class="chip-close material-icons">close</i></div>
                            <div class="chip bulk-chip" checkbox-id="checkbox-price">Price<i class="chip-close material-icons">close</i></div>
                            <div class="chip bulk-chip" checkbox-id="checkbox-discount">Discount<i class="chip-close material-icons">close</i></div>
                            <div class="chip bulk-chip" checkbox-id="checkbox-group">Group<i class="chip-close material-icons">close</i></div>
                            <div class="chip bulk-chip" checkbox-id="checkbox-description">Description<i class="chip-close material-icons">close</i></div>
                        </div>
                        <div class="col s7">
                            <div class="row">
                                <div class="col s12">
                                    <ul class="tabs">
                                        <li class="tab col s2 bulk-modal-tab"><a class="active" href="#test1">Status</a></li>
                                        <li class="tab col s2 bulk-modal-tab"><a href="#test2">Price</a></li>
                                        <li class="tab col s3 bulk-modal-tab"><a href="#test3">Discount</a></li>
                                        <li class="tab col s2 bulk-modal-tab"><a href="#test4">Group</a></li>
                                        <li class="tab col s3 bulk-modal-tab"><a href="#test5">Description</a></li>
                                    </ul>
                                </div>
                                <form id="bulk-change-form">
                                    <div class="row col s12">
                                        <div class="col s8">
                                            <div id="test1" class="col s12">
                                                <div class="row edit-selected-items">
                                                    <div class="input-field col s12">
                                                        <i class="material-icons prefix">cached</i>
                                                        <input id="checkbox-status" type="hidden" class="is-changed-checkbox" name="isStatusNameChanged">
                                                        <select class="bulk-field-change" name="statusName" id="bulk-select-product-status">
                                                            <option value="PLANNED" data-value="10"
                                                                    data-after-disabled="11">
                                                                PLANNED
                                                            </option>
                                                            <option value="ACTUAL" data-value="11"
                                                                    data-after-disabled="12">
                                                                ACTUAL
                                                            </option>
                                                            <option value="OUTDATED" data-value="12">OUTDATED</option>
                                                        </select>
                                                        <label for="bulk-select-product-status">Choose product
                                                            status</label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="test2" class="col s12">
                                                <div class="row edit-selected-items">
                                                    <div class="input-field col s12">
                                                        <i class="material-icons prefix">attach_money</i>
                                                        <input id="checkbox-price" type="hidden" class="is-changed-checkbox" name="isDefaultPriceChanged">
                                                        <input class='bulk-field-change validate' type='number' name='defaultPrice'
                                                               id='bulk-price'/>
                                                        <label for="bulk-price">Price</label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="test3" class="col s12">
                                                <div class="row edit-selected-items">
                                                    <div class="input-field col s12">
                                                        <i class="material-icons prefix">loyalty</i>
                                                        <input id="checkbox-discount" type="hidden" class="is-changed-checkbox" name="isDiscountIdChanged">
                                                        <input type="text" id="bulk-discount-input"
                                                               class="bulk-field-change autocomplete">
                                                        <input type="hidden" id="bulk-discount-hidden-input"
                                                               name="discountId"/>
                                                        <label for="discount-input">Selected discount: <span
                                                                id="bulk-selected-discount"></span></label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="test4" class="col s12">
                                                <div class="row edit-selected-items">
                                                    <div class="input-field col s12">
                                                        <i class="material-icons prefix">bubble_chart</i>
                                                        <input id="checkbox-group" type="hidden" class="is-changed-checkbox" name="isGroupIdChanged">
                                                        <input type="text" id="bulk-group-input" class="bulk-field-change autocomplete">
                                                        <input type="hidden" id="bulk-group-hidden-input"
                                                               name="groupId"/>
                                                        <label for="group-input">Selected group: <span
                                                                id="bulk-selected-group"></span></label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="test5" class="col s12">
                                                <div class="row edit-selected-items">
                                                    <div class="input-field col s12">
                                                        <input id="checkbox-description" type="hidden" class="is-changed-checkbox" name="isDescriptionChanged">
                                                        <i class="material-icons prefix">description</i>
                                                        <textarea id="bulk-desc-product" name="description"
                                                                  class="bulk-field-change materialize-textarea"></textarea>
                                                        <label for="bulk-desc-product">Description</label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col s4">
                                            <input type="hidden" name="itemIds" id="bulk-item-ids">
                                            <button id="bulk-submit" type="submit" name="action"
                                                    class="btn waves-effect waves-light">Edit
                                                <i class="material-icons right">replay</i>
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div id="bulk-card" class="row">
                <div class="col s12 m6">
                    <div class="card">
                        <div class="card-content center-align">
                            <span class="card-title">Items Selected</span>
                            <h5 class="selected-items">0</h5>
                        </div>
                        <div class="card-action center-align">
                            <a id="bulk-change-btn" class="a-dummy" href="#!">Edit</a>
                            <a id="bulk-cancel-btn" class="a-dummy" href="#!">Cancel</a>
                        </div>
                    </div>
                </div>
            </div>
        </sec:authorize>


    </div>
</div>
<%@ include file="/WEB-INF/jsp/component/tableScript.jsp" %>
<script>
    //TODO: help me
    $(document).ready(function () {
        $('.scrollspy').scrollSpy();
    });
    $('ul#tabs').tabs({
        onShow: function (tab) {
        }
    });

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">

    //////// create product ////////

    $('#select_product_status').karpo_status(12).disabled(14);
    $('#discount-input').karpo_autocomplete({
        url: "/discounts/autocomplete",
        label: "#selected-discount",
        defaultValue: "",
        hideInput: "#discount-hidden-input"
    });
    $('#group-input').karpo_autocomplete({
        url: "/groups/autocomplete",
        label: "#selected-group",
        defaultValue: "",
        hideInput: "#group-hidden-input"
    });
    $('select').material_select();


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
            var url = "/products";
            var form = "#addProduct";
            send(form, url, "POST").done(function (id) {
                if (id) {
                    location.hash = '#product/' + id;
                }
            })
        }
    });

    //////// all ////////


    $("#table-all-products").karpo_table({
        urlSearch: "/products/autocomplete?type=all",
        urlTable: "/products",
        bulkUrl: "/products/bulk",
        mapper: function (object) {
            var tr = $("<tr>");
            tr.append($("<td  class='hide-on-med-and-down'><p class='bulk-checkbox-wrapper'><input type='checkbox' class='bulk-checkbox filled-in' id='bulk-table-" + object.id + "' /><label for='bulk-table-" + object.id + "'></label></p></td>"), {});
            tr.append($("<td>").append($("<a>", {
                text: object.id,
                href: "#product/" + object.id
            })));
            tr.append($("<td>", {text: object.title}));
            tr.append($("<td>", {text: object.status}));
            var priceTd =  $("<td>");
            var price = $("<span>", {text: object.price});
            if (object.discountActive || object.groupDiscountActive) {
                var allPercentage = 0;
                var tooltipHtml = "";
                if (object.discountActive) {
                    allPercentage += object.discountPercentage;
                    tooltipHtml +=  "<p>" + object.discountTitle + " - " + object.discountPercentage + "%</p>";
                }
                if (object.groupDiscountActive) {
                    allPercentage += object.groupDiscountPercentage;
                    tooltipHtml +=  "<p>" + object.groupDiscountTitle + " - " + object.groupDiscountPercentage + "%</p>";
                }
                allPercentage = allPercentage > 99 ? 99: allPercentage;
                priceTd =  $("<td>", {
                    "data-tooltip": tooltipHtml
                });
                price.addClass("old-price");
                priceTd.addClass("tooltipped discount");
                var newPrice = $("<span>", {
                    text: Math.round((object.price - object.price * allPercentage / 100) * 100) / 100,
                    class: "red-text text-darken-2 new-price"
                });
                priceTd.append(newPrice);
                var percentage = $("<span>", {
                    text: allPercentage + "%",
                    class: "red-text text-darken-2 percentage hide-on-med-and-down"
                });
                priceTd.append(percentage);
            }
            priceTd.append(price);
            tr.append(priceTd);
            tr.append($("<td>").append($("<a>", {
                text: object.discountTitle,
                href: "#discount/" + object.discount
            })));
            var disActive = null;
            if(object.discountActive != null)
                disActive = (object.discountActive == true) ? "<i class='material-icons prefix'>check</i>" : "<i class='material-icons prefix'>clear</i>";
            tr.append($("<td>", {html: disActive}));
            tr.append($("<td>").append($("<a>", {
                text: object.groupName,
                href: "#group/" + object.group
            })));
            tr.append($("<td>").append($("<a>", {
                text: object.groupDiscountTitle,
                href: "#discount/" + object.groupDiscount
            })));
            var groupDisActive = null;
            if(object.groupDiscountActive != null)
                groupDisActive = (object.groupDiscountActive == true) ? "<i class='material-icons prefix'>check</i>" : "<i class='material-icons prefix'>clear</i>";
            tr.append($("<td>", {html: groupDisActive}));
            return tr;
        },
        complete: function () {
            $(".percentage").addClass("show");
            $(".tooltipped").tooltip({
                delay: 50,
                html: true
            });
        }
    });

//    $('#bulk-select-product-status').karpo_status(12).disabled(14);
    $('#bulk-discount-input').karpo_autocomplete({
        url: "/discounts/autocomplete",
        label: "#bulk-selected-discount",
        defaultValue: "",
        hideInput: "#bulk-discount-hidden-input"
    });
    $('#bulk-group-input').karpo_autocomplete({
        url: "/groups/autocomplete",
        label: "#bulk-selected-group",
        defaultValue: "",
        hideInput: "#bulk-group-hidden-input"
    });

    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_CUSTOMER')">
    $("#table-create-orders").karpo_table({
        urlSearch: "/products/autocomplete?type=possible",
        urlTable: "/products?type=possible",
        countTr: 5,
        mapper: function (object) {
            var tr = $("<tr>");
            var aId = $("<a>", {
                href: "#product/" + object.id,
                text: object.id
            })
            tr.append($("<td>").append(aId));
            tr.append($("<td>", {text: object.title}));
            var priceTd =  $("<td>");
            var price = $("<span>", {text: object.price});
            if (object.discountActive || object.groupDiscountActive) {
                var allPercentage = 0;
                var tooltipHtml = "";
                if (object.discountActive) {
                    allPercentage += object.discountPercentage;
                    tooltipHtml +=  "<p>" + object.discountTitle + " - " + object.discountPercentage + "%</p>";
                }
                if (object.groupDiscountActive) {
                    allPercentage += object.groupDiscountPercentage;
                    tooltipHtml +=  "<p>" + object.groupDiscountTitle + " - " + object.groupDiscountPercentage + "%</p>";
                }
                allPercentage = allPercentage > 99 ? 99: allPercentage;
                priceTd =  $("<td>", {
                    "data-tooltip": tooltipHtml
                });
                price.addClass("old-price");
                priceTd.addClass("tooltipped discount");
                var newPrice = $("<span>", {
                    text: Math.round((object.price - object.price * allPercentage / 100) * 100) / 100,
                    class: "red-text text-darken-2 new-price"
                });
                priceTd.append(newPrice);
                var percentage = $("<span>", {
                    text: allPercentage + "%",
                    class: "red-text text-darken-2 percentage hide-on-med-and-down"
                });
                priceTd.append(percentage);
            }
            priceTd.append(price);
            tr.append(priceTd);
            tr.append($("<td>").append($("<a>", {
                href: "#group/" + object.group,
                text: object.groupName
            })));
            tr.append($("<td>").append($("<a>", {
                href: "#product/" + object.id,
                class: "waves-effect waves-light btn",
                text: "Details"
            })));
            return tr;
        },
        complete: function () {
            $(".percentage").addClass("show");
            $(".tooltipped").tooltip({
                delay: 50,
                html: true
            });
        }
    });

    $("#table-my-products").karpo_table({
        urlSearch: "/products/autocomplete?type=actual",
        urlTable: "/products?type=actual",
        mapper: function (object) {
            var disActive = null;
            var tr = $("<tr>");
            tr.append($("<td>").append($("<a>", {
                text: object.id,
                href: "#product/" + object.id
            })));
            tr.append($("<td>", {text: object.title}));
            tr.append($("<td>", {text: object.price}));
            tr.append($("<td>").append($("<a>", {
                href: "#discount/" + object.discount,
                text: object.discountTitle
            })));
            tr.append($("<td>").append($("<a>", {
                href: "#group/" + object.group,
                text: object.groupName
            })));
            tr.append($("<td>").append($("<a>", {
                href: "#discount/" + object.groupDiscount,
                text: object.groupDiscountTitle
            })));
            return tr;
        }
    });
    </sec:authorize>

</script>
