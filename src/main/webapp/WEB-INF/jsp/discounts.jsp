<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    .description {
        display: block;
        width: 600px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body z-depth-1" data-page-name="Discounts">
    <div id="content-body" class="row">

        <div class="col s12">
            <ul id="tabs" class="tabs">
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR', 'ROLE_PMG')">
                    <li class="tab col s3"><a class="active" href="#all-discounts-wrapper">All Discounts</a></li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
                    <li class="tab col s3"><a href="#create-wrapper">Create</a></li>
                </sec:authorize>
            </ul>
        </div>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR', 'ROLE_PMG')">
            <div id="all-discounts-wrapper" class="col s12">
                <div id="table-all-products" class="table-container row">
                    <div class="table-wrapper col s11 center-align">
                        <table class="striped responsive-table centered bulk-table">
                            <thead>
                            <tr>
                                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
                                    <th class="hide-on-med-and-down">
                                        <p class='bulk-checkbox-wrapper'>
                                            <input type='checkbox' class='filled-in bulk-select-all'
                                                   id="select-all-checkbox"/>
                                            <label for='select-all-checkbox'></label>
                                        </p>
                                    </th>
                                </sec:authorize>
                                <th data-field="1">
                                    <a href="#!" class="sorted-element a-dummy">#</a>
                                </th>
                                <th data-field="2">
                                    <a href="#!" class="sorted-element a-dummy">Title</a>
                                </th>
                                <th data-field="3">
                                    <a href="#!" class="sorted-element a-dummy">Percentage</a>
                                </th>
                                <th data-field="4">
                                    <a href="#!" class="sorted-element a-dummy">Description</a>
                                </th>
                                <th class="th-dropdown" data-field="active">
                                    <a class='dropdown-button a-dummy' href='#'
                                       data-activates='dropdown-all-discount-status'
                                       data-default-name="Discount Active">
                                        Discount Active
                                    </a>
                                    <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                    <ul id="dropdown-all-discount-status" class='dropdown-content'>
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
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
            <div id="create-wrapper" class="col s12">
                <div class="row">
                    <form class="col s12" id="addDiscount">
                        <div class="row">
                            <div class='input-field col s12 m6'>
                                <i class="material-icons prefix">title</i>
                                <input class='validate' type='text' name='title' id='disc_title'/>
                                <label for="disc_title">Title</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class='input-field col s12 m6'>
                                <i class="material-icons prefix">call_received</i>
                                <input class='validate' type='number' name='percentage' id='disc_percentage'/>
                                <label for="disc_percentage">Percentage</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class='switch col s12 m6'>
                                <i class="material-icons prefix">touch_app</i>
                                <label>
                                    Inactive
                                    <input name="active" type="checkbox" checked="checked" id="disc_active">
                                    <span class="lever"></span>
                                    Active
                                </label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12 m6">
                                <i class="material-icons prefix">description</i>
                                <textarea id="disc_description" name="description"
                                          class="materialize-textarea"></textarea>
                                <label for="disc_description">Description</label>
                            </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="row">
                            <div class="col s12 m6">
                                <button class="btn waves-effect waves-light" type="submit" id="submit-discount"
                                        name="action">Create Discount
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
                            <div class="chip bulk-chip" checkbox-id="checkbox-percentage">Percentage<i
                                    class="chip-close material-icons">close</i></div>
                            <div class="chip bulk-chip" checkbox-id="checkbox-description">Description<i
                                    class="chip-close material-icons">close</i></div>
                            <div class="chip bulk-chip" checkbox-id="checkbox-is-active">Discount Active<i
                                    class="chip-close material-icons">close</i></div>
                        </div>
                        <div class="col s7">
                            <div class="row">
                                <div class="col s12">
                                    <ul class="tabs">
                                        <li class="tab col s4 bulk-modal-tab"><a class="active"
                                                                                 href="#test1">Percentage</a></li>
                                        <li class="tab col s3 bulk-modal-tab"><a href="#test2">Description</a></li>
                                        <li class="tab col s4 bulk-modal-tab"><a href="#test3">Discount Active</a></li>
                                    </ul>
                                </div>
                                <form id="bulk-change-form">
                                    <div class="row col s12">
                                        <div class="col s8">
                                            <div id="test1" class="col s12">
                                                <div class="row edit-selected-items">
                                                    <div class='input-field col s12'>
                                                        <i class="material-icons prefix">call_received</i>
                                                        <input id="checkbox-percentage" type="hidden"
                                                               class="is-changed-checkbox" name="isPercentageChanged">
                                                        <input class='validate bulk-field-change' type='number'
                                                               name='percentage' id='bulk-disc-percentage'/>
                                                        <label for="bulk-disc-percentage">Percentage</label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="test2" class="col s12">
                                                <div class="row edit-selected-items">
                                                    <div class="input-field col s12">
                                                        <input id="checkbox-description" type="hidden"
                                                               class="is-changed-checkbox" name="isDescriptionChanged">
                                                        <i class="material-icons prefix">description</i>
                                                        <textarea id="bulk-desc-product" name="description"
                                                                  class="bulk-field-change materialize-textarea"></textarea>
                                                        <label for="bulk-desc-product">Description</label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="test3" class="col s12">
                                                <div class="row edit-selected-items">
                                                    <div class='input-field col s12 switch'>
                                                        <label>
                                                            <input id="checkbox-is-active" type="hidden"
                                                                   class="is-changed-checkbox" name="isActiveChanged">
                                                            Inactive
                                                            <input type="checkbox" class="bulk-field-change"
                                                                   name="active"/>
                                                            <span class="lever"></span>
                                                            Active
                                                        </label>
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
<form class="col s12" id="updateDiscountActive" style="display: none">
    <input class='validate' type='text' name='id' id='disc_id2'/>
    <div class="row">
        <div class='input-field col s6'>
            <i class="material-icons prefix">title</i>
            <input class='validate' type='text' name='title' id='disc_title2'/>
            <label for="disc_title">Title</label>
        </div>
    </div>
    <div class="row">
        <div class='input-field col s6'>
            <i class="material-icons prefix">call_received</i>
            <input class='validate' type='number' name='percentage' id='disc_percentage2'/>
            <label for="disc_percentage">Percentage</label>
        </div>
    </div>
    <div class="row">
        <div class='switch col s6'>
            <i class="material-icons prefix">touch_app</i>
            <label>
                Inactive
                <input name="active" type="checkbox" checked="checked" id="disc_active2">
                <span class="lever"></span>
                Active
            </label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s6">
            <i class="material-icons prefix">description</i>
            <textarea id="disc_description2" name="description"
                      class="materialize-textarea"></textarea>
            <label for="disc_description">Description</label>
        </div>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="row">
        <div class="col s6">
            <button class="btn waves-effect waves-light" type="submit" id="submit-discount2"
                    name="action">Create Discount
                <i class="material-icons right">send</i>
            </button>
        </div>
    </div>
</form>
<%@ include file="/WEB-INF/jsp/component/tableScript.jsp" %>
<script>
    $('ul#tabs').tabs();

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
    function changeBoolValues(id) {
        var simpleId = id;
        var id = "#" + id;

        if ($(id).html() == "check") {
            document.getElementById(simpleId).style.display = "none";
            $(id).html("clear");
            $(id).fadeIn(2000);
        }
        else if ($(id).html() == "clear") {
            document.getElementById(simpleId).style.display = "none";
            $(id).html("check");
            $(id).fadeIn(2000);
        }

        $("#disc_id2").val($("#" + "id" + simpleId).html());
        $("#disc_title2").val($("#" + "title" + simpleId).html());
        var length = $("#" + "percentage" + simpleId).html().length;
        var perc = $("#" + "percentage" + simpleId).html().substring(0, length - 1);
        $("#disc_percentage2").val(perc);
        if ($(id).html() == "check")
            document.getElementById("disc_active2").checked = true;
        if ($(id).html() == "clear")
            document.getElementById("disc_active2").checked = false;
        //$("#disc_active2").val($("#"+"discountActive"+simpleId).val());
        $("#disc_description2").val($("#" + "description" + simpleId).html());


        /*$("#disc_title").val($("#"+"title"+simpleId).html());
         var length = $("#"+"percentage"+simpleId).html().length;
         var perc = $("#"+"percentage"+simpleId).html().substring(0, length-1);
         $("#disc_percentage").val(perc);

         if ($(id).html() == "check")
         document.getElementById("disc_active").checked = true;
         if ($(id).html() == "clear")
         document.getElementById("disc_active").checked = false;

         //$("#disc_active").val($("#"+"discountActive"+simpleId).html());
         $("#disc_description").val($("#"+"description"+simpleId).html());
         */

        var url = "/discounts";
        var form = "#updateDiscountActive";
        send(form, url, "PUT");
    }

    //////// create ////////

    $('select').material_select();

    $("#addDiscount").on("submit", function (e) {
            e.preventDefault();
            var title = $('#disc_title').val();
            var percentage = $('#disc_percentage').val();
            if (title.length < 5) {
                Materialize.toast("Please enter title at least 5 characters", 10000, 'rounded');
            } else if (percentage < 0 || percentage > 100) {
                Materialize.toast("Please enter percentage more 0 and less 100", 10000, 'rounded');
            } else {
                var url = "/discounts";
                var form = "#addDiscount";
                send(form, url, "POST").done(function (id) {
                    if (id) {
                        location.hash = '#discount/' + id;
                    }
                })
            }
        }
    );
    </sec:authorize>

    //////// all ////////
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR', 'ROLE_PMG')">
    $("#table-all-products").karpo_table({
        urlSearch: "/discounts/autocomplete",
        urlTable: "/discounts",
        bulkUrl: "/discounts/bulk",
        mapper: function (object) {
            var disActive = null;
            var temp = null;
            var tr = $("<tr>");
            temp = "<span id='id" + object.id + "'>" + object.id + "</span>";
            <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
            tr.append($("<td class='hide-on-med-and-down'><p class='bulk-checkbox-wrapper'><input type='checkbox' class='bulk-checkbox filled-in' id='bulk-table-" + object.id + "' /><label for='bulk-table-" + object.id + "'></label></p></td>"), {});
            </sec:authorize>
            tr.append($("<td>").append($("<a>", {
                html: temp,
                href: "#discount/" + object.id
            })));
            // id='title" + object.id + "'
            temp = "<span id='title" + object.id + "' >" + object.title + "</span>";
            tr.append($("<td>", {html: temp}));
            var perc = object.percentage ? object.percentage + "%" : "";
            temp = "<span id='percentage" + object.id + "' >" + perc + "</span>"
            tr.append($("<td>", {html: temp}));
            temp = "<span id='description" + object.id + "' class='description hide-on-med-and-down' >" + object.description + "</span>";
            tr.append($("<td>", {html: temp}));
            if (object.discountActive != null) {
                <sec:authorize access="hasAnyRole('ROLE_PMG')">
                disActive = (object.discountActive == true) ? "<i class='material-icons prefix'>check</i>" : "<i class='material-icons prefix'>clear</i>";
                temp = "<span id='discountActive" + object.id + "'>" + disActive + "</span>";
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
                disActive = (object.discountActive == true) ? "<i id='" + object.id + "' onclick='changeBoolValues(" + object.id + ")' class='material-icons prefix'>check</i>" : "<i id='" + object.id + "' onclick='changeBoolValues(" + object.id + ")' class='material-icons prefix'>clear</i>";
                temp = "<span id='discountActive" + object.id + "' style='cursor: pointer;'>" + disActive + "</span>";
                </sec:authorize>
            }
            tr.append($("<td>", {html: temp}));
            return tr;
        }
    });
    </sec:authorize>

</script>
