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
        <div id="all-discounts-wrapper" class="col s12">
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
                        <div class="switch">
                            <i class="material-icons prefix">touch_app</i>
                            <label>
                                Off
                                <input name="active" type="checkbox" checked="checked" id="disc_active">
                                <span class="lever"></span>
                                On
                            </label>
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
                        <div class="input-field col s12">
                            <i class="material-icons prefix">description</i>
                            <textarea id="disc_description" name="description" class="materialize-textarea"></textarea>
                            <label for="disc_description">Description</label>
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="row">
                        <div class="col s6">
                            <button class="btn waves-effect waves-light" type="submit" name="action">Create Discount
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
                $.post("/csr/addDiscount", $("#addDiscount").serialize(), function (data) {
                    $("#addDiscount")[0].reset();
                    Materialize.toast(data, 10000, 'rounded');
                });
            }
        }
    );

    //////// all ////////


</script>