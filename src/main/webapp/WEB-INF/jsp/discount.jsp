<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body" data-page-name="Discounts">
    <div id="content-body" class="row">
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
            <div id="update-wrapper" class="col s12">
                <br>
                <div class="row">
                    <form class="col s12" id="updateDiscount">
                        <div class="row">
                            <div class='input-field col s6'>
                                <i class="material-icons prefix">title</i>
                                <input class='validate' type='text' name='title' id='disc_title'
                                       value="${discount.title}"/>
                                <label for="disc_title" class="active">Title</label>
                            </div>
                            <div class="switch">
                                <i class="material-icons prefix">touch_app</i>
                                <label>
                                    Inactive
                                    <input name="active" type="checkbox" id="disc_active" <c:if test="${discount.active==true}">checked="checked"</c:if>>
                                    <span class="lever"></span>
                                    Active
                                </label>
                            </div>
                        </div>
                        <div class="row">
                            <div class='input-field col s6'>
                                <i class="material-icons prefix">call_received</i>
                                <input class='validate' type='number' name='percentage' id='disc_percentage'
                                       value="${discount.percentage}"/>
                                <label for="disc_percentage" class="active">Percentage</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12">
                                <div class="input-field col s12">
                                    <i class="material-icons prefix">description</i>
                                    <textarea id="disc_description" name="description"
                                              class="materialize-textarea">${discount.description}</textarea>
                                    <label for="disc_description" class="active">Description</label>
                                </div>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="id" value="${discount.id}">
                            <div class="row">
                                <div class="col s6">
                                    <button class="btn waves-effect waves-light" type="submit"
                                            id="submit-update-discount"
                                            name="action">Update Discount
                                        <i class="material-icons right">send</i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </sec:authorize>
    </div>
</div>


<script>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">

    $('select').material_select();

    $("#updateDiscount").on("submit", function (e) {
            e.preventDefault();
            var title = $('#disc_title').val().set();
            var percentage = $('#disc_percentage').val().set;
            if (title.length < 5) {
                Materialize.toast("Please enter title at least 5 characters", 10000, 'rounded');
            } else if (percentage < 0 || percentage > 100) {
                Materialize.toast("Please enter percentage more 0 and less 100", 10000, 'rounded');
            } else {
                $.put("/discounts", $("#updateDiscount").serialize(), function (data) {
                    $("#updateDiscount")[0].reset();
                    Materialize.toast(data, 10000, 'rounded');
                });
            }
        }
    );

    $(document).on("click", "#submit-update-discount", function (e) {
        event.preventDefault();
        var url = "/discounts";
        var form = "#updateDiscount";
        sendPut(form, url);
    });
    </sec:authorize>
</script>