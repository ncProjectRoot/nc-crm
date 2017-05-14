<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url value="/img/discount.png" var="discountUrl"/>
<style>

    .content-body {
        position: relative;
    }

    .container {
        padding-top: 50px;
        padding-bottom: 100px;
    }

    .container selector {
        position: relative;
    }

    h4, h5, h6 {
        text-align: center;
        word-wrap: break-word;
    }

    .change-trigger {
        position: absolute;
        margin: 20px;
    }

    .modal.modal-fixed-footer {
        max-height: 85%;
        height: 98%;
    }

    .row .col {
        float: none;
        margin: 30px auto;
    }

    .modal .modal-footer .btn {
        float: none;
    }

    .modal-content h4 {
        margin-top: 20px;
    }

</style>
<div class="content-body z-depth-1" data-page-name="Discount #${discount.id}">
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
        <a class="modal-trigger brown-text change-trigger" href="#update"><i class='material-icons medium'>settings</i></a>
        <div id="update" class="modal modal-fixed-footer">
            <form id="update-discount-form">
                <div class="modal-content row">
                    <h4>Update discount</h4>
                    <input type="hidden" name="id" value="${discount.id}"/>
                    <div class='input-field col s7'>
                        <i class="material-icons prefix">title</i>
                        <input class='validate' type='text' name='title' id='disc_title'
                               value="${discount.title}"/>
                        <label for="disc_title" class="active">Title</label>
                    </div>
                    <div class="input-field col s7">
                        <i class="material-icons prefix">call_received</i>
                        <input class='validate' type='number' name='percentage' id='disc_percentage'
                               value="${discount.percentage}"/>
                        <label for="disc_percentage" class="active">Percentage</label>
                    </div>
                    <div class='switch col s7'>
                        <i class="material-icons prefix">touch_app</i>
                        <label>
                            Inactive
                            <input name="active" type="checkbox" id="disc_active"
                                   <c:if test="${discount.active==true}">checked="checked"</c:if>>
                            <span class="lever"></span>
                            Active
                        </label>
                    </div>
                    <div class="input-field col s7">
                        <i class="material-icons prefix">description</i>
                        <textarea id="descProduct" name="description"
                                  class="materialize-textarea">${discount.description}</textarea>
                        <label for="descProduct">Description</label>
                    </div>
                </div>
                <div class="modal-footer center-align">
                    <button class="btn waves-effect waves-light" id="submit-update-discount" type="submit"
                            name="action">Update
                        <i class="material-icons right">send</i>
                    </button>
                </div>
            </form>
        </div>
    </sec:authorize>
    <div class="container">
        <h4 class="title field">${discount.title}</h4>
        <div class="divider"></div>
        <div class="section">
            <div class="div-percentage field">
                <h5 class="price">${discount.percentage}%</h5>
            </div>
        </div>
        <div class="section">
            <div class="div-active field">
                <h5 class="active">
                    <c:if test="${discount.active==true}">Active</c:if>
                    <c:if test="${discount.active==false}">Inactive</c:if></h5>
            </div>
            <div class="divider"></div>
            <div class="section">
                <h5 class="description field">${discount.description}</h5>
            </div>
        </div>
    </div>
</div>
<script>

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">

    $('.modal').modal({
            opacity: .5, // Opacity of modal background
            endingTop: '8%' // Starting top style attribute
        }
    );

    $('.materialize-textarea').trigger('autoresize');
    Materialize.updateTextFields();

    $("#update-discount-form").on("submit", function (e) {
        e.preventDefault();
        sendPut("#update-discount-form", "/discounts").done(function () {
            $('.modal').modal('close');
            $(window).trigger('hashchange')
        })
    });

    </sec:authorize>

</script>