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
                                    <th data-field="4" class="hide-on-med-and-down">
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

    function changeBoolValues(id) {
        var simpleId = id;
        var id = "#" + id;

        if ($(id).html() == "check") {
            document.getElementById(simpleId).style.display = "none";
            $(id).html("clear");
            $(id).fadeIn(3000);
        }
        else if ($(id).html() == "clear") {
            document.getElementById(simpleId).style.display = "none";
            $(id).html("check");
            $(id).fadeIn(3000);
        }
        
        $("#disc_id2").val($("#"+"id"+simpleId).html());
        $("#disc_title2").val($("#"+"title"+simpleId).html());
        var length = $("#"+"percentage"+simpleId).html().length;
        var perc = $("#"+"percentage"+simpleId).html().substring(0, length-1);
        $("#disc_percentage2").val(perc);
        if ($(id).html() == "check")
            document.getElementById("disc_active2").checked = true;
        if ($(id).html() == "clear")
            document.getElementById("disc_active2").checked = false;
        //$("#disc_active2").val($("#"+"discountActive"+simpleId).val());
        $("#disc_description2").val($("#"+"description"+simpleId).html());
        
        
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
        mapper: function (object) {
            var disActive = null;
            var temp = null;
            var tr = $("<tr>");
            temp = "<span id='id" + object.id + "'>"+object.id+"</span>";
            tr.append($("<td>").append($("<a>", {
                html: temp,
                href: "#discount/" + object.id
            })));
            // id='title" + object.id + "'
            temp = "<span id='title" + object.id + "' >"+object.title+"</span>";
            tr.append($("<td>", {html: temp}));
            var perc = object.percentage ? object.percentage + "%" : "";
            temp = "<span id='percentage" + object.id + "' >"+perc+"</span>"
            tr.append($("<td>", {html: temp}));
            temp = "<span id='description" + object.id + "' class='description hide-on-med-and-down' >"+object.description+"</span>";
            tr.append($("<td>", {html: temp}));
            
            if (object.discountActive != null)
                disActive = (object.discountActive == true) ? "<i id='" + object.id + "' onclick='changeBoolValues(" + object.id +")' class='material-icons prefix'>check</i>" : "<i id='" + object.id + "' onclick='changeBoolValues(" + object.id + ")' class='material-icons prefix'>clear</i>";
            temp = "<span id='discountActive" + object.id + "' style='cursor: pointer;'>"+disActive+"</span>";
            tr.append($("<td>", {html: temp}));
            return tr;
        }
    });
    </sec:authorize>

</script>
