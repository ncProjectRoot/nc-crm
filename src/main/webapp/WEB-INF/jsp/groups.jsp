<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body z-depth-1" data-page-name="Groups">
    <div id="content-body" class="row">

        <div class="col s12">
            <ul id="tabs" class="tabs">
                <li class="tab col s3"><a class="active" href="#all-groups-wrapper">All Groups</a></li>
                <li class="tab col s3"><a href="#create-wrapper">Create</a></li>
            </ul>
        </div>
        <div id="all-groups-wrapper" class="col s12">
            <div id="table-all-groups" class="table-container row">
                <div class="table-wrapper col s11 center-align">
                    <table class="striped responsive-table centered ">
                        <thead>
                        <tr>
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
                                <a href="#!" class="sorted-element a-dummy">Name</a>
                            </th>
                            <th data-field="3">
                                <a href="#!" class="passive-single-sort sorted-element a-dummy">Number products</a>
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
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </div>
        <div id="create-wrapper" class="col s12">
            <div class="row">
                <form class="col s12" id="addGroup">
                    <div class="row">
                        <div class='input-field col s12 m6'>
                            <i class="material-icons prefix">short_text</i>
                            <input class='validate' type='text' name='name' id='group_name'/>
                            <label for="group_name">Name</label>
                        </div>
                        <div class="input-field col s12 m6">
                            <i class="material-icons prefix">loyalty</i>
                            <input type="text" id="discount-input" class="autocomplete">
                            <input type="hidden" id="discount-hidden-input" name="discountId"/>
                            <label for="discount-input">Selected discount: <span id="selected-discount"></span></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 m6">
                            <i class="material-icons prefix">view_list</i>
                            <input type="text" id="product-input" class="autocomplete">
                            <input type="hidden" id="product-hidden-input" name="products"/>
                            <label for="product-input">Select products</label>
                        </div>
                        <div class="col s12 m6">
                            <ul id="selected-products" class="collection"></ul>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12 m6">
                            <button class="btn waves-effect waves-light" type="submit" id="submit-group" name="action">
                                Create Group
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
                        <div class="chip bulk-chip" checkbox-id="checkbox-discount">Discount<i
                                class="chip-close material-icons">close</i></div>
                    </div>
                    <div class="col s7">
                        <div class="row">
                            <div class="col s12">
                                <ul class="tabs">
                                    <li class="tab col s6 bulk-modal-tab"><a href="#test1">Discount</a></li>
                                </ul>
                            </div>
                            <form id="bulk-change-form">
                                <div class="row col s12">
                                    <div class="col s8">
                                        <div id="test1" class="col s12">
                                            <div class="row edit-selected-items">
                                                <div class="input-field col s12">
                                                    <i class="material-icons prefix">loyalty</i>
                                                    <input id="checkbox-discount" type="hidden"
                                                           class="is-changed-checkbox" name="isDiscountIdChanged">
                                                    <input type="text" id="bulk-discount-input"
                                                           class="bulk-field-change autocomplete">
                                                    <input type="hidden" id="bulk-discount-hidden-input"
                                                           name="discountId"/>
                                                    <label for="bulk-discount-input">Selected discount: <span
                                                            id="bulk-selected-discount"></span></label>
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
    </div>
</div>
<%@ include file="/WEB-INF/jsp/component/tableScript.jsp" %>
<script>

    $('ul#tabs').tabs();

    $("#table-all-groups").karpo_table({
        urlSearch: "/groups/autocomplete",
        urlTable: "/groups",
        bulkUrl: "/groups/bulk",
        mapper: function (object) {
            var disActive = null;
            var tr = $("<tr>");
            tr.append($("<td><p><input type='checkbox' class='bulk-checkbox filled-in' id='bulk-table-" + object.id + "' /><label for='bulk-table-" + object.id + "'></label></p></td>"), {});
            tr.append($("<td>", {html: '<a href="#group/' + object.id + '">' + object.id + '</a>'}));
            tr.append($("<td>", {text: object.name}));
            tr.append($("<td>", {text: object.numberProducts}));
            tr.append($("<td>", {text: object.discountName}));
            tr.append($("<td>", {text: object.discountValue}));
            if (object.discountActive != null)
                disActive = (object.discountActive == true) ? "<i class='material-icons prefix'>check</i>" : "<i class='material-icons prefix'>clear</i>";
            tr.append($("<td>", {html: disActive}));
            return tr;
        }
    });

    //////// create ////////

    $('#discount-input').karpo_autocomplete({
        url: "/discounts/autocomplete",
        label: "#selected-discount",
        defaultValue: "${product.discount.id} ${product.discount.title}",
        hideInput: "#discount-hidden-input"
    });

    $('#bulk-discount-input').karpo_autocomplete({
        url: "/discounts/autocomplete",
        label: "#bulk-selected-discount",
        defaultValue: "${product.discount.id} ${product.discount.title}",
        hideInput: "#bulk-discount-hidden-input"
    });

    $("#product-input").karpo_multi_select({
        url: "/products/autocomplete?type=withoutGroup",
        collection: "#selected-products",
        hideInput: "#product-hidden-input"
    });

    $("#bulk-product-input").karpo_multi_select({
        url: "/products/autocomplete?type=withoutGroup",
        collection: "#bulk-selected-products",
        hideInput: "#bulk-product-hidden-input"
    });

    $("#addGroup").on("submit", function (e) {
        e.preventDefault();
        var grpName = $('#group_name').val();
        if (grpName.length < 5) {
            Materialize.toast("Please enter group name at least 5 characters", 10000, 'rounded');
        } else {
            var url = "/groups";
            var form = "#addGroup";
            send(form, url, "POST").done(function (id) {
                if (id) {
                    location.hash = '#group/' + id;
                }
            });
        }
    });

</script>
