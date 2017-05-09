<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body" data-page-name="Groups">
    <div id="content-body" class="row">

        <div class="col s12">
            <ul id="tabs" class="tabs">
                <li class="tab col s3"><a class="active" href="#all-groups-wrapper">All Groups</a></li>
                <li class="tab col s3"><a href="#create-wrapper">Create</a></li>
            </ul>
        </div>
        <div id="all-groups-wrapper" class="col s12">

        </div>
        <div id="create-wrapper" class="col s12">
            <div class="row">
                <form class="col s12" id="addGroup">
                    <div class="row">
                        <div class='input-field col s6'>
                            <i class="material-icons prefix">short_text</i>
                            <input class='validate' type='text' name='name' id='group_name'/>
                            <label for="group_name">Name</label>
                        </div>
                        <div class="input-field col s3">
                            <i class="material-icons prefix">add_shopping_cart</i>
                            <select name="discountId" id="select_group_disc">
                                <option value="0">Default</option>
                            </select>
                            <label for="select_group_disc">Choose discount</label>
                        </div>
                        <div class="input-field col s3">
                            <i class="material-icons prefix">search</i>
                            <input class='validate' type='text' onkeyup="fetchGroupDiscounts()"
                                   id='search_for_group_title'/>
                            <label for="search_for_group_title">Search discount <span
                                    id="group_discount_numbers"></span></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class='input-field col s6'>
                            <i class="material-icons prefix">done_all</i>
                            <select multiple id="products_without_group" name="products">
                                <option value="" disabled selected>Choose products</option>
                            </select>
                            <label>Products without group</label>
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="row">
                        <div class="col s6">
                            <button class="btn waves-effect waves-light" type="submit" id="submit-group" name="action">Create Group
                                <i class="material-icons right">send</i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>


    </div>
</div>
<%@ include file="/WEB-INF/jsp/component/tableScript.jsp" %>
<script>

    $('ul#tabs').tabs({
        onShow: function (tab) {
        }
    });

    //////// create ////////

    function fetchGroupDiscounts() {
        var title = $('#search_for_group_title').val();
        if (title.length > 1) {
            $('#select_group_disc').children().remove();
            $.get("/discounts/" + title).success(function (data) {
                $('#select_group_disc').append($('<option value="0">Default</option>'));
                $.each(data, function (i, item) {
                    $('#select_group_disc').append($('<option/>', {
                        value: item.id,
                        text: item.title + ' - ' + item.percentage + '%'
                    }));
                });
                $('#select_group_disc').material_select('updating');
                $('#group_discount_numbers').html(", results " + data.length);
            });
        }
    }

    loadProductsWithoutGroup();
    function loadProductsWithoutGroup() {
        $.get("/csr/load/productWithoutGroup").success(function (data) {
            $('#products_without_group').children().remove();
            $('#products_without_group').append('<option value="" disabled selected>Choose products</option>')
            $.each(data, function (i, item) {
                $('#products_without_group').append($('<option/>', {
                    value: item.id,
                    text: item.title + ' - ' + item.statusName
                }));
            });
            $('#products_without_group').material_select('updating');
        });
    }

    //        create group
    $("#addGroup").on("submit", function (e) {
        e.preventDefault();
        var grpName = $('#group_name').val();

        if (grpName.length < 5) {
            Materialize.toast("Please enter group name at least 5 characters", 10000, 'rounded');
        } else {
            $.post("/csr/addGroup", $("#addGroup").serialize(), function (data) {
                $("#addGroup")[0].reset();
                Materialize.toast(data, 10000, 'rounded');
                loadProductsWithoutGroup();
            });
        }
    });

    loadProductsWithoutGroup();
    function loadProductsWithoutGroup() {
        $.get("/csr/load/productWithoutGroup").success(function (data) {
            var prods = $('#products_without_group');
            prods.children().remove();
            prods.append('<option value="" disabled selected>Choose products</option>')
            $.each(data, function (i, item) {
                prods.append($('<option/>', {
                    value: item.id,
                    text: item.title + ' - ' + item.statusName
                }));
            });
            prods.material_select('updating');
        });
    }

    $(document).on("click","#submit-group", function (e) {
        event.preventDefault();
        var url = "/csr/addGroup";
        var form = "#addGroup";
        sendPost(form, url);
        $(form)[0].reset();
    });

    //////// all ////////


</script>