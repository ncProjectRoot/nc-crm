<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>

    .date-fields-wrapper {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        align-items: center;
    }

    div.date-fields-wrapper .col.input-field {
        margin-left: 0;
    }

</style>
<div class="content-body z-depth-1" data-page-name="Report">
    <div id="content-body" class="row">
        <div class="col s12">
            <ul id="tabs" class="tabs">
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
                    <li class="tab col s3"><a href="#order-wrapper">User orders</a></li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_PMG')">
                    <li class="tab col s3"><a href="#complaint-wrapper">Product complaints</a></li>
                </sec:authorize>
            </ul>
        </div>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
            <div id="order-wrapper" class="col s12">
                <div class="row">
                    <form class="col s12" id="downloadXlsx" method="get" action="/*/report/users">
                        <div class="row date-fields-wrapper">
                            <div class="input-field col s11 m4">
                                <input name="dateFrom" id="from-date-order" type="date" class="order-datepicker">
                                <label for="from-date-order">From</label>
                            </div>
                            <div class="input-field col s11 m4">
                                <input name="dateTo" id="to-date-order" type="date" class="order-datepicker">
                                <label for="to-date-order">To</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m6 right">
                                <ul id="selected-users" class="collection"></ul>
                            </div>
                            <div class="col s12 m6 left">
                                <div class="input-field col s12">
                                    <i class="material-icons prefix">view_list</i>
                                    <input type="text" id="user-input" class="autocomplete">
                                    <input type="hidden" id="user-hidden-input" name="idCustomer"/>
                                    <label for="user-input">Select Users</label>
                                </div>
                                <div class="input-field col s12">
                                    <select name="orderByIndex" id="orderBySelect">
                                        <option value="1">User name</option>
                                        <option value="2">Product title</option>
                                        <option value="3">Order date</option>
                                        <option value="4">Prefered date</option>
                                        <option value="5">Order status</option>
                                    </select>
                                    <label>Order By</label>
                                </div>
                                <div class="col s12">
                                    <button class="btn waves-effect waves-light" type="submit" id="download-orders"
                                            name="action">
                                        Download
                                        <i class="material-icons right">save</i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_PMG')">
            <div id="complaint-wrapper" class="col s12">
                <div class="row">
                    <form class="col s12" id="complaints" method="get" action="/*/report/complaints">
                        <div class="row date-fields-wrapper">
                            <div class="input-field col s11 m4">
                                <input name="dateFrom" id="from-date-complaint" type="date"
                                       class="complaint-datepicker">
                                <label for="from-date-complaint">From</label>
                            </div>
                            <div class="input-field col s11 m4">
                                <input name="dateTo" id="to-date-complaint" type="date" class="complaint-datepicker">
                                <label for="to-date-complaint">To</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m6 right">
                                <ul id="selected-products" class="collection"></ul>
                            </div>
                            <div class="col s12 m6 left">
                                <div class="input-field col s12">
                                    <i class="material-icons prefix">view_list</i>
                                    <input type="text" id="product-input" class="autocomplete">
                                    <input type="hidden" id="product-hidden-input" name="idProduct"/>
                                    <label for="user-input">Select products</label>
                                </div>
                                <div class="input-field col s12">
                                    <select name="orderByIndex" id="orderBySelect-complaint">
                                        <option value="1">User name</option>
                                        <option value="2">Complaint title</option>
                                        <option value="3">Product title</option>
                                        <option value="4">Complaint status</option>
                                        <option value="5">Created date</option>
                                    </select>
                                    <label>Order By</label>
                                </div>
                                <div class="col s12">
                                    <button class="btn waves-effect waves-light" type="submit" id="download-complaints"
                                            name="action">
                                        Download
                                        <i class="material-icons right">save</i>
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
    var today = new Date();
    var lastWeek = new Date(today.getTime() - 7 * 24 * 60 * 60 * 1000);

    $('ul#tabs').tabs();

    $('select').material_select();

    $('.complaint-datepicker').pickadate({
        selectMonths: true,
        format: 'yyyy-mm-dd',
        max: new Date(),
        closeOnSelect: true
    }).pickadate('picker');

    $('.order-datepicker').pickadate({
        selectMonths: true,
        format: 'yyyy-mm-dd',
        max: new Date(),
        closeOnSelect: true
    }).pickadate('picker');

    //    Order datepicker
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
        var $ordersFromDate = $('#from-date-order').pickadate({
            selectMonths: true,
            format: 'yyyy-mm-dd',
            max: today,
            closeOnSelect: true,
            container: '#order-wrapper',
            select: lastWeek
        }).pickadate('picker').set('select', lastWeek);

        var $ordersToDate = $('#to-date-order').pickadate({
            selectMonths: true,
            format: 'yyyy-mm-dd',
            max: today,
            min: lastWeek,
            closeOnSelect: true,
            container: '#order-wrapper',
            select: today
        }).pickadate('picker').set('select', today);

        $ordersFromDate.start();
        $ordersFromDate.set('select', lastWeek);
        $ordersToDate.start();
        $ordersToDate.set('select', today);
        $ordersToDate.set('min', lastWeek);


        $('.order-datepicker').on('change', function () {
            if ($(this).attr('id') === 'from-date-order') {
                $ordersToDate.set('min', $(this).val());
            }
            if ($(this).attr('id') === 'to-date-order') {
                $ordersFromDate.set('max', $(this).val());
            }
        });
    </sec:authorize>
    //    complaint datepicker
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_PMG')">
        var $complaintsFromDate = $('#from-date-complaint').pickadate({
            selectMonths: true,
            format: 'yyyy-mm-dd',
            max: today,
            closeOnSelect: true,
            container: '#complaint-wrapper',
            select: lastWeek
        }).pickadate('picker').set('select', lastWeek);

        var $complaintsToDate = $('#to-date-complaint').pickadate({
            selectMonths: true,
            format: 'yyyy-mm-dd',
            max: today,
            min: lastWeek,
            closeOnSelect: true,
            container: '#complaint-wrapper',
            select: today
        }).pickadate('picker').set('select', today);

        $complaintsFromDate.start();
        $complaintsFromDate.set('select', lastWeek);
        $complaintsToDate.start();
        $complaintsToDate.set('select', today);
        $complaintsToDate.set('min', lastWeek);

        $('.complaint-datepicker').on('change', function () {
            if ($(this).attr('id') === 'from-date-complaint') {
                $complaintsToDate.set('min', $(this).val());
            }
            if ($(this).attr('id') === 'to-date-complaint') {
                $complaintsFromDate.set('max', $(this).val());
            }
        });
    </sec:authorize>
    //    autocomplets

    $("#user-input").karpo_multi_select({
        url: "/users/autocomplete",
        collection: "#selected-users",
        hideInput: "#user-hidden-input",
        elementUrl: "#user/"
    });

    $("#product-input").karpo_multi_select({
        url: "/products/autocomplete?type=all",
        collection: "#selected-products",
        hideInput: "#product-hidden-input",
        elementUrl: "#product/"
    });

    Materialize.updateTextFields();

    $("#download-orders").on("submit", function (e) {
        if (!$("#orderBySelect").val()) {
            Materialize.toast("Please, select order by", 5000);
            e.preventDefault();
        }
    });

    $("#download-complaints").on("submit", function (e) {
        if (!$("#orderBySelect-complaint").val()) {
            Materialize.toast("Please, select order by", 5000);
            e.preventDefault();
        }
    });
</script>