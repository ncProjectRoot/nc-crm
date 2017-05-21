<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body" data-page-name="Discounts">
    <div id="content-body" class="row">

        <div class="col s12">
            <ul id="tabs" class="tabs">
                <li class="tab col s3"><a class="active" href="#all-discounts-wrapper">All Discounts</a></li>
                <li class="tab col s3"><a href="#create-wrapper">Create</a></li>
            </ul>
        </div>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
            <div id="all-discounts-wrapper" class="col s12">
                <div id="table-all-products" class="table-container row">
                    <div class="table-wrapper col s11 center-align">
                        <table class="striped responsive-table centered bulk-table">
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


            <div id="create-wrapper" class="col s12">
                <div class="row">
                    <form class="col s12" id="addDiscount">
                        <div class="row">
                            <div class='input-field col s6'>
                                <i class="material-icons prefix">title</i>
                                <input class='validate' type='text' name='title' id='disc_title'/>
                                <label for="disc_title">Title</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class='input-field col s6'>
                                <i class="material-icons prefix">call_received</i>
                                <input class='validate' type='number' name='percentage' id='disc_percentage'/>
                                <label for="disc_percentage">Percentage</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class='switch col s6'>
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
                            <div class="input-field col s6">
                                <i class="material-icons prefix">description</i>
                                <textarea id="disc_description" name="description"
                                          class="materialize-textarea"></textarea>
                                <label for="disc_description">Description</label>
                            </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="row">
                            <div class="col s6">
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
<%@ include file="/WEB-INF/jsp/component/tableScript.jsp" %>
<script>

    $('ul#tabs').tabs();

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
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

    //////// all ////////

    $("#table-all-products").karpo_table({
        urlSearch: "/discounts/autocomplete",
        urlTable: "/discounts",
        bulkUrl: "/discounts/bulk",
        mapper: function (object) {
            var disActive = null;
            var tr = $("<tr>");
            tr.append($("<td><p><input type='checkbox' class='bulk-checkbox filled-in' id='bulk-table-" + object.id + "' /><label for='bulk-table-" + object.id + "'></label></p></td>"), {});
            tr.append($("<td>").append($("<a>", {
                text: object.id,
                href: "#discount/" + object.id
            })));
            tr.append($("<td>", {text: object.title}));
            tr.append($("<td>", {text: object.percentage ? object.percentage + "%" : ""}));
            tr.append($("<td>", {text: object.description}));
            if (object.discountActive != null)
                disActive = (object.discountActive == true) ? "<i class='material-icons prefix'>check</i>" : "<i class='material-icons prefix'>clear</i>";
            tr.append($("<td>", {html: disActive}));
            return tr;
        }
    });
    </sec:authorize>

</script>
