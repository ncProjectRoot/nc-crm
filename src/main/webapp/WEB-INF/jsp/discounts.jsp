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
                $.post("/discounts", $("#addDiscount").serialize(), function (data) {
                    $("#addDiscount")[0].reset();
                    Materialize.toast(data, 10000, 'rounded');
                });
            }
        }
    );

    $(document).on("click", "#submit-discount", function (e) {
        event.preventDefault();
        var url = "/discounts";
        var form = "#addDiscount";
        sendPost(form, url);
        $(form)[0].reset();
    });

    //////// all ////////

    $("#table-all-products").karpo_table({
        urlSearch: "/discounts/titles",
        urlTable: "/discounts",
        mapper: function (object) {
            var disActive = null;
            var tr = $("<tr>");
            tr.append($("<td>").append($("<a>", {
                text: object.id,
                href: "#discount?id=" + object.id
            })));
            tr.append($("<td>", {text: object.title}));
            tr.append($("<td>", {text: object.percentage ? object.percentage + "%" : ""}));
            tr.append($("<td>", {text: object.description}));
            if(object.discountActive != null)
                disActive = (object.discountActive == true) ? "<i class='material-icons prefix'>check</i>" : "<i class='material-icons prefix'>clear</i>";            
            tr.append($("<td>", {html: disActive}));
            return tr;
        }
    });
    </sec:authorize>

</script>