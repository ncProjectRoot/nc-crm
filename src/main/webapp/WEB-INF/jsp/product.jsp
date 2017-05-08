<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url value="/img/discount.png" var="discountUrl"/>
<style>

    .content-body {
        position: relative;
    }

    .discount-img {
        position: absolute;
        right: 0;
        width: 15%;
    }

    .product-img {
        position: absolute;
        right: 0;
        width: 15%;
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

    .status {
        text-transform: capitalize
    }

    .status.ACTUAL {
        color: #1b5e20;
    }

    .status.PLANNED {
        color: #4a148c;
    }

    .status.OUTDATED {
        color: #3e2723;
    }

    .order-btn {
        position: absolute;
        left: 165px;
        right: 0;
        top: 125px;
        margin: 0 auto;
    }

    .div-price {
        position: relative;
    }

    .div-price .old-price {
        text-decoration: line-through;
        opacity: 0.6;
        position: relative;
        top: 13px;
        left: 5px;
        margin-top: 50px;
    }

    .div-price .new-price {
        position: absolute;
        right: 65px;
        left: 0;
        top: -33px;
        color: #d32f2f;
    }

    .div-price .percentage {
        width: 70px;
        height: 70px;
        border: 2px solid #D32F2F;
        line-height: 68px;
        text-align: center;
        font-size: 20px;
        position: absolute;
        border-radius: 70px;
        top: -52px;
        right: -151px;
        left: 0px;
        opacity: 0.9;
        transform: rotate(12deg);
        margin: 0 auto;
        color: #000;
    }

    #order {
        min-height: 250px;
    }

    #order-form {
        width: 360px;
        margin: 50px auto 0;
        text-align: center;
    }

</style>
<div class="content-body z-depth-1" data-page-name="Product #${product.id}">
    <c:if test="${product.discount.active}">
        <img class="discount-img" src="${discountUrl}"/>
    </c:if>
    <div class="container">
        <h4 class="title">${product.title}</h4>
        <div class="divider"></div>
        <div class="section">
            <h5 class="status ${product.status.name}">${product.status.name}</h5>
            <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                <c:if test="${product.status == 'ACTUAL'}">
                    <a class="btn-floating btn-large waves-effect waves-light green darken-4 order-btn" href="#order"><i
                            class="material-icons">attach_money</i></a>
                    <div id="order" class="modal bottom-sheet">
                        <div class="modal-content">
                            <h4>Order</h4>
                            <div class="input-field col s12">
                                <form id="order-form">
                                    <div class="input-field col s4">
                                        <input id="preferred-date" type="date" class="datepicker">
                                        <label for="preferred-date">Preferred date</label>
                                    </div>
                                    <button class="btn waves-effect waves-light" type="submit" name="action">Pay
                                        <i class="material-icons left">attach_money</i>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:if>
            </sec:authorize>
            <h6>${product.group.name}</h6>
        </div>
        <div class="section">
            <div class="div-price">
                <h5 class="price">${product.defaultPrice}$</h5>
                <c:if test="${product.discount.active}">
                    <h5 class="new-price"></h5>
                    <a class="percentage" href="#discount?id=${product.discount.id}">${product.discount.percentage}%</a>
                </c:if>
            </div>
        </div>
        <div class="divider"></div>
        <div class="section">
            <h5>${product.description}</h5>
        </div>
    </div>
</div>
<script>

    <c:if test="${product.discount.active}">
    var percentage = parseInt($(".percentage").text());
    var $price = $(".price");
    var oldPrice = parseFloat($price.text());
    $price.addClass("old-price");
    var newPrice = Math.round((oldPrice - oldPrice * percentage / 100) * 100) / 100;
    $(".new-price").text(newPrice);
    </c:if>

    <sec:authorize access="hasRole('ROLE_CUSTOMER')">

    <c:if test="${product.status == 'ACTUAL'}">
    $('.modal').modal({
            opacity: .5
        }
    );
    $('.datepicker').pickadate({
        selectMonths: true,
        format: 'dd-mm-yyyy',
        min: new Date()
    });
    </c:if>
    </sec:authorize>

</script>