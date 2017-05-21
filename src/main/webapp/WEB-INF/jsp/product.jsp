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
        min-height: 320px;
    }

    #order-form {
        width: 360px;
        margin: 50px auto 0;
        text-align: center;
    }

    .change-trigger {
        position: absolute;
        margin: 20px;
    }
    .change-status-trigger {
        position: absolute;
        margin: 20px;
        top: 70px;
    }

    .modal.modal-fixed-footer {
        max-height: 85%;
        height: 98%;
    }

    .modal.modal-fixed-footer.status-modal {
        height: 50%;
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
<div class="content-body z-depth-1" data-page-name="Product #${product.id}">
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
        <a class="modal-trigger brown-text change-trigger" href="#change"><i class='material-icons medium'>settings</i></a>
        <div id="change" class="modal modal-fixed-footer">
            <form id="change-form">
                <div class="modal-content row">
                    <h4>Change Product</h4>
                    <input type="hidden" name="id" value="${product.id}"/>
                    <div class='input-field col s7'>
                        <i class="material-icons prefix">title</i>
                        <label for="title">Title</label>
                        <input class="validate" id="title" type="text" name="title">
                    </div>
                    <div class='input-field col s7'>
                        <i class="material-icons prefix">attach_money</i>
                        <input class='validate' type='number' name='defaultPrice' id='price'/>
                        <label for="price">Price</label>
                    </div>
                    <div class="input-field col s7">
                        <i class="material-icons prefix">loyalty</i>
                        <input type="text" id="discount-input" class="autocomplete">
                        <input type="hidden" id="discount-hidden-input" name="discountId"/>
                        <label for="discount-input">Selected discount: <span id="selected-discount"></span></label>
                    </div>
                    <div class="input-field col s7">
                        <i class="material-icons prefix">bubble_chart</i>
                        <input type="text" id="group-input" class="autocomplete">
                        <input type="hidden" id="group-hidden-input" name="groupId"/>
                        <label for="group-input">Selected group: <span id="selected-group"></span></label>
                    </div>
                    <div class="input-field col s11">
                        <i class="material-icons prefix">description</i>
                        <textarea id="descProduct" name="description"
                                  class="materialize-textarea">${product.description}</textarea>
                        <label for="descProduct">Description</label>
                    </div>
                </div>
                <div class="modal-footer center-align">
                    <button class="btn waves-effect waves-light" id="submit-product" type="submit" name="action">Update
                        <i class="material-icons right">send</i>
                    </button>
                </div>
            </form>
        </div>
        <c:if test="${product.status.name!='OUTDATED'}">
        <a class="modal-trigger brown-text change-status-trigger" href="#change-status"><i class='material-icons medium'>cached</i></a>
        <div id="change-status" class="modal modal-fixed-footer status-modal">
            <form id="change-status-form">
                <div class="modal-content row">
                    <h4>Change Product status</h4>
                    <input type="hidden" name="id" value="${product.id}"/>
                    <div class="input-field col s7">
                        <i class="material-icons prefix">cached</i>
                        <select name="statusName" id="select_product_status">
                            <option value="12" data-value="12" data-after-disabled="13">PLANNED</option>
                            <option value="13" data-value="13" data-after-disabled="14">ACTUAL</option>
                            <option value="14" data-value="14">OUTDATED</option>
                        </select>
                        <label for="select_product_status">Choose product status</label>
                    </div>
                </div>
                <div class="modal-footer center-align">
                    <button class="btn waves-effect waves-light" id="change-product-status" type="submit" name="action">Change
                        <i class="material-icons right">send</i>
                    </button>
                </div>
            </form>
        </div>
        </c:if>
    </sec:authorize>
    <c:if test="${product.discount.active}">
        <img class="discount-img" src="${discountUrl}"/>
    </c:if>
    <div class="container">
        <h4 class="title field">${product.title}</h4>
        <div class="divider"></div>
        <div class="section">
            <h5 class="status ${product.status.name} field">${product.status.name}</h5>
            <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                <c:if test="${product.status == 'ACTUAL' && !hasProduct}">
                    <a class="btn-floating btn-large waves-effect waves-light green darken-4 order-btn" href="#order"><i
                            class="material-icons">attach_money</i></a>
                    <div id="order" class="modal bottom-sheet">
                        <div class="modal-content">
                            <h4>Order</h4>
                            <div class="input-field col s12">
                                <form id="order-form">
                                    <div class="input-field col s4">
                                        <input name="preferredDate" id="preferred-date" type="date" class="datepicker">
                                        <label for="preferred-date">Preferred date</label>
                                    </div>
                                    <div class="input-field col s12">
                                        <label for="timepicker_ampm_dark">Preferred time</label>
                                        <input name="preferredTime" id="timepicker_ampm_dark" class="timepicker"
                                               type="time">
                                    </div>
                                    <input name="productId" type="hidden" value="${product.id}"/>
                                    <button class="btn waves-effect waves-light" type="submit" name="action">Pay
                                        <i class="material-icons left">attach_money</i>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:if>
            </sec:authorize>
            <h6 class="group field">${product.group.name}</h6>
        </div>
        <div class="section">
            <div class="div-price field">
                <h5 class="price">${product.defaultPrice}$</h5>
                <c:if test="${product.discount.active}">
                    <h5 class="new-price"></h5>
                    <a class="percentage" href="#discount?id=${product.discount.id}">${product.discount.percentage}%</a>
                </c:if>
            </div>
        </div>
        <div class="divider"></div>
        <div class="section">
            <h5 class="description field">${product.description}</h5>
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

    <c:if test="${product.status == 'ACTUAL' && !hasProduct}">
    $('.modal').modal({
            opacity: .5
        }
    );
    $('.datepicker').pickadate({
        selectMonths: true,
        format: 'yyyy-mm-dd',
        min: new Date(),
        autoclose: true
    });

    $('.timepicker').pickatime({
        default: 'now',
        twelvehour: false, // change to 12 hour AM/PM clock from 24 hour
        donetext: 'OK',
        autoclose: true,
        vibrate: true // vibrate the device when dragging clock hand
    });

    $('#order-form').on("submit", function (e) {
        e.preventDefault();
        var url = "/orders";
        var form = "#order-form";
        send(form, url, "POST").done(function (orderId) {
            if (orderId) {
                location.hash = '#order/' + orderId;
            }
            $('.modal').modal('close');
        })
    })
    ;
    </c:if>
    </sec:authorize>

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">

    $('.modal').modal({
            opacity: .5, // Opacity of modal background
            endingTop: '8%' // Starting top style attribute
        }
    );


    $(".modal-content input[name='title']").val("${product.title}");
    $(".modal-content input[name='defaultPrice']").val("${product.defaultPrice}");
    $('#select_product_status').karpo_status(${product.status.id});
    $('#discount-input').karpo_autocomplete({
        url: "/discounts/autocomplete",
        label: "#selected-discount",
        defaultValue: "${product.discount.id} ${product.discount.title}",
        hideInput: "#discount-hidden-input"
    });
    $('#group-input').karpo_autocomplete({
        url: "/groups/autocomplete/",
        label: "#selected-group",
        defaultValue: "${product.group.id} ${product.group.name}",
        hideInput: "#group-hidden-input"
    });

    $('.materialize-textarea').trigger('autoresize');
    Materialize.updateTextFields();

    $("#change-form").on("submit", function (e) {
        e.preventDefault();
        send("#change-form", "/products", "PUT").done(function () {
            $('.modal').modal('close');
            $(window).trigger('hashchange')
        })
    });

    $.ajaxSetup({
        complete: $(function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
        })
    });

    $("#change-status-form").on("submit", function (e) {
        e.preventDefault();
        $.ajax({
            url: "/products/status",
            type: 'PUT',
            data: {
                productId: ${product.id},
                statusId: $('#select_product_status').val()
            },
            success: function (data) {
                if (data === true) {
                    Materialize.toast('You have changed status of product!'  , 5000, 'rounded');
                } else if (data === false) {
                    Materialize.toast("Something wrong!", 3000, 'rounded');
                    setTimeout(function () {
                        window.location.reload();
                    }, 3000);
                }
                $('.modal').modal('close');
                $(window).trigger('hashchange')
            }
        });
    });

    </sec:authorize>

</script>