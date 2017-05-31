<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>

    .content-body {
        background-color: rgba(0, 0, 0, 0);
    }

    .card-wrapper {
        width: 100%;
        min-height: 350px;
        margin-bottom: 20px;
        background-color: #fff;
    }

    .labels-wrapper, .date-title-wrapper {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        align-items: center;
    }

    .labels-wrapper span, .date-title-wrapper span {
        margin: 10px;
    }

    .date-title-wrapper .arrow i {
        margin-top: 5px;
    }

    .content-body .selected-products {
        margin: 0 auto;
        float: none;
    }

    .content-body .col-submit {
        margin-top: 25px;
    }

    .graph .ct-horizontal {
        margin-left: -7px;
    }

    .ct-label {
        fill: #fff;
        color: #fff;
        font-size: 0.75rem;
        line-height: 1;
    }

    .graph span {
        color: #000;
    }

</style>
<div class="content-body" data-page-name="Dashboard">
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
        <div id="root-picker-outlet-for-orders"></div>
        <div class="card-wrapper z-depth-1 card">
            <div class="card-content">
                <span class="card-title activator">Product Orders<i class="material-icons right">more_vert</i></span>
                <div class="date-title-wrapper">
                    <span id="orders-date-from-title"></span>
                    <span class="arrow"><i class="material-icons">trending_flat</i></span>
                    <span id="orders-date-to-title"></span>
                </div>
                <div id="orders-labels" class="labels-wrapper"></div>
                <div id="orders-graph-wrapper">
                    <div id="orders-graph" class="graph"></div>
                </div>
            </div>
            <div class="card-reveal">
            <span class="card-title grey-text text-darken-4">Product Orders<i
                    class="material-icons right">close</i></span>
                <form id="show-orders-graph">
                    <div class="row">
                        <div class="input-field col s10 m4">
                            <i class="material-icons prefix">view_list</i>
                            <input type="text" id="product-input-for-orders" class="autocomplete">
                            <input type="hidden" id="product-hidden-input-for-orders" name="elementIds"/>
                            <label for="product-input-for-orders">Select products</label>
                        </div>
                        <div class="input-field col s10 m2">
                            <input name="fromDate" id="orders-from-date" type="date" class="orders-datepicker">
                            <label for="orders-from-date">From</label>
                        </div>
                        <div class="input-field col s10 m2">
                            <input name="toDate" id="orders-to-date" type="date" class="orders-datepicker">
                            <label for="orders-to-date">To</label>
                        </div>
                        <div class="input-field col s10 m2">
                            <select name="dateType" id="orders-date-type">
                                <option value="DAYS" selected>Day</option>
                                <option value="MONTHS">Month</option>
                                <option value="YEARS">Year</option>
                            </select>
                            <label>Type</label>
                        </div>
                        <div class="col s10 m2 col-submit">
                            <button class="waves-effect waves-light btn" type="submit" name="action">Update</button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s6 selected-products">
                            <ul id="selected-products-for-orders" class="collection selected-products"></ul>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_PMG')">
        <div id="root-picker-outlet-for-complaints"></div>
        <div class="card-wrapper z-depth-1 card">
            <div class="card-content">
                <span class="card-title activator">Product Complaints<i
                        class="material-icons right">more_vert</i></span>
                <div class="date-title-wrapper">
                    <span id="complaints-date-from-title"></span>
                    <span class="arrow"><i class="material-icons">trending_flat</i></span>
                    <span id="complaints-date-to-title"></span>
                </div>
                <div id="complaints-labels" class="labels-wrapper"></div>
                <div id="complaints-graph-wrapper">
                    <div id="complaints-graph" class="graph"></div>
                </div>
            </div>
            <div class="card-reveal">
            <span class="card-title grey-text text-darken-4">Product Complaints<i
                    class="material-icons right" id="close-">close</i></span>
                <form id="show-complaints-graph">
                    <div class="row">
                        <div class="input-field col s10 m4">
                            <i class="material-icons prefix">view_list</i>
                            <input type="text" id="product-input-for-complaints" class="autocomplete">
                            <input type="hidden" id="product-hidden-input-for-complaints" name="elementIds"/>
                            <label for="product-input-for-complaints">Select products</label>
                        </div>
                        <div class="input-field col s10 m2">
                            <input name="fromDate" id="complaints-from-date" type="date" class="complaints-datepicker">
                            <label for="complaints-from-date">From</label>
                        </div>
                        <div class="input-field col s10 m2">
                            <input name="toDate" id="complaints-to-date" type="date" class="complaints-datepicker">
                            <label for="complaints-to-date">To</label>
                        </div>
                        <div class="input-field col s10 m2">
                            <select name="dateType" id="complaints-date-type">
                                <option value="DAYS" selected>Day</option>
                                <option value="MONTHS">Month</option>
                                <option value="YEARS">Year</option>
                            </select>
                            <label>Type</label>
                        </div>
                        <div class="col s10 m2 col-submit">
                            <button class="waves-effect waves-light btn" type="submit" name="action">Update</button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s6 selected-products">
                            <ul id="selected-products-for-complaints" class="collection selected-products"></ul>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </sec:authorize>
</div>
<script>
    var today = new Date();
    var lastWeek = new Date(today.getTime() - 7 * 24 * 60 * 60 * 1000);

    var seq = 0,
        delays = 80,
        durations = 200;

    var lineOption = {
        low: 0,
        height: 300,
        showPoint: true,
        fullWidth: true,
        chartPadding: 20,
        showLabel: true
    };

    var lineLabelsForOrders = [];
    var lineLabelsForComplaints = [];
    var animateLine = function (data) {
        if (data.type === 'line' || data.type === 'area') {
            seq++;
            if (data.type === 'line') {
                var obj = $(data.element._node);
                var index = "a";
                var lineLabels;
                if (obj.closest("div.graph").attr('id') === 'orders-graph') {
                    lineLabels = lineLabelsForOrders;
                } else if (obj.closest("div.graph").attr('id') === 'complaints-graph') {
                    lineLabels = lineLabelsForComplaints;
                }
                lineLabels.forEach(function (element, i, array) {
                    if (obj.is(".ct-series-" + index + " .ct-line")) {
                        obj.attr("data-tooltip", element);
                        obj.tooltip({
                            delay: 0
                        });
                    }
                    index = String.fromCharCode(index.charCodeAt(0) + 1);
                });
            }
            data.element.animate({
                opacity: {
                    begin: seq * delays + 1000,
                    dur: durations,
                    from: 0,
                    to: 1
                }
            });
        } else if (data.type === 'point') {
            seq++;
            data.element.animate({
                x1: {
                    begin: seq * delays,
                    dur: durations,
                    from: data.x - 10,
                    to: data.x,
                    easing: 'easeOutQuart'
                },
                x2: {
                    begin: seq * delays,
                    dur: durations,
                    from: data.x - 10,
                    to: data.x,
                    easing: 'easeOutQuart'
                },
                opacity: {
                    begin: seq * delays,
                    dur: durations,
                    from: 0,
                    to: 1,
                    easing: 'easeOutQuart'
                }
            })
        }
    };

    function updateGraph(url, form, graphId, chartistLine, lineLabels, labelsId) {
        $(".progress").addClass("progress-active");
        $.get(url, $(form).serialize(), function (dashboardData) {
            if (!dashboardData) {
                Materialize.toast('Invalid date', 10000);
            }
            if (chartistLine) {
                chartistLine.update(dashboardData);
            } else {
                chartistLine = new Chartist.Line(graphId, dashboardData, lineOption).on('created', function () {
                    seq = 0;
                    var $labels = $(labelsId);
                    $labels.empty();
                    lineLabels.forEach(function (element, index) {
                        var i = String.fromCharCode('a'.charCodeAt(0) + index);
                        var color = $(".ct-series-" + i + " .ct-line").css("stroke");
                        $labels.append($("<span>", {text: element}).css("color", color));
                        console.log();
                    });
                }).on('draw', animateLine);
            }
            $(".progress").removeClass("progress-active");
        });
    }

    $('select').material_select();

    var patternYears = /^2[0-9]{3}$/;
    var patternMonths = /^2[0-9]{3}-(0[1-9]|1[0-2])$/;
    var patternDays = /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/;
    function checkDateValidate(fromDate, toDate, dateType) {
        switch (dateType) {
            case "YEARS":
                if (patternYears.test(fromDate) && patternYears.test(toDate)) {
                    return true;
                } else {
                    Materialize.toast('Invalid date, you have to format as example 2017', 10000);
                    return false;
                }
            case "MONTHS":
                if (patternMonths.test(fromDate) && patternMonths.test(toDate)) {
                    return true;
                } else {
                    Materialize.toast('Invalid date, you have to format as example 2017-01', 10000);
                    return false;
                }
            case "DAYS":
                if (patternDays.test(fromDate) && patternDays.test(toDate)) {
                    return true;
                } else {
                    Materialize.toast('Invalid date, you have to format as example 2017-01-21', 10000);
                    return false;
                }
        }
    }

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">

    var $ordersFromDate = $('#orders-from-date').pickadate({
        selectMonths: true,
        format: 'yyyy-mm-dd',
        max: today,
        closeOnSelect: true,
        container: '#root-picker-outlet-for-orders',
        select: lastWeek
    }).pickadate('picker').set('select', lastWeek);

    var $ordersToDate = $('#orders-to-date').pickadate({
        selectMonths: true,
        format: 'yyyy-mm-dd',
        max: today,
        min: lastWeek,
        closeOnSelect: true,
        container: '#root-picker-outlet-for-orders',
        select: today
    }).pickadate('picker').set('select', today);


    $('#orders-date-type').on('change', function(e) {
        switch ($(this).val()) {
            case "YEARS":
                $ordersFromDate.stop();
                $('#orders-from-date').attr("type", "text");
                $('#orders-from-date').val(lastWeek.getFullYear());
                $ordersToDate.stop();
                $('#orders-to-date').attr("type", "text");
                $('#orders-to-date').val(today.getFullYear());
                break;
            case "MONTHS":
                $ordersFromDate.stop();
                $('#orders-from-date').attr("type", "text");
                $('#orders-from-date').val(lastWeek.getFullYear() + "-" + (lastWeek.getMonth() < 10 ? '0' : '') + lastWeek.getMonth());
                $ordersToDate.stop();
                $('#orders-to-date').attr("type", "text");
                $('#orders-to-date').val(today.getFullYear() + "-" + (today.getMonth() < 10 ? '0' : '') + today.getMonth());
                break;
            case "DAYS":
                $ordersFromDate.start();
                $ordersFromDate.set('select', lastWeek);
                $ordersToDate.start();
                $ordersToDate.set('select', today);
                break;
        }
    });

    $('.orders-datepicker').on('change', function () {
        if ($('#orders-date-type').val() == "DAYS") {
            if ($(this).attr('id') === 'orders-from-date') {
                $ordersToDate.set('min', $(this).val());
            }
            if ($(this).attr('id') === 'orders-to-date') {
                $ordersFromDate.set('max', $(this).val());
            }
        }
    });

    var $productsForOrdersMultiSelect = $("#product-input-for-orders").karpo_multi_select({
        url: "/products/autocomplete?type=all",
        collection: "#selected-products-for-orders",
        hideInput: "#product-hidden-input-for-orders",
        elementUrl: "#product/"
    });

    var chartistLineOrders;
    $("#show-orders-graph").on("submit", function (e) {
        e.preventDefault();
        var fromDate = $('#orders-from-date').val();
        var toDate = $('#orders-to-date').val();
        if (checkDateValidate(fromDate, toDate, $('#orders-date-type').val())) {
            lineLabelsForOrders = $productsForOrdersMultiSelect.getSelectedVal().slice(0);
            if($productsForOrdersMultiSelect.getSelectedVal().length == 0) {
                lineLabelsForOrders.push("All products")
            }
            $(this).siblings(".card-title").trigger("click");
            updateGraph("/orders/graph", this, '#orders-graph', chartistLineOrders, lineLabelsForOrders ,"#orders-labels");
            $('#orders-date-from-title').text(fromDate);
            $('#orders-date-to-title').text(toDate);
        }
    });
    $("#show-orders-graph").trigger("submit");



    </sec:authorize>

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_PMG')">

    var $complaintsFromDate = $('#complaints-from-date').pickadate({
        selectMonths: true,
        format: 'yyyy-mm-dd',
        max: today,
        closeOnSelect: true,
        container: '#root-picker-outlet-for-complaints',
        select: lastWeek
    }).pickadate('picker').set('select', lastWeek);

    var $complaintsToDate = $('#complaints-to-date').pickadate({
        selectMonths: true,
        format: 'yyyy-mm-dd',
        max: today,
        min: lastWeek,
        closeOnSelect: true,
        container: '#root-picker-outlet-for-complaints',
        select: today
    }).pickadate('picker').set('select', today);

    $('#complaints-date-type').on('change', function(e) {
        switch ($(this).val()) {
            case "YEARS":
                $complaintsFromDate.stop();
                $('#complaints-from-date').attr("type", "text");
                $('#complaints-from-date').val(lastWeek.getFullYear());
                $complaintsToDate.stop();
                $('#complaints-to-date').attr("type", "text");
                $('#complaints-to-date').val(today.getFullYear());
                break;
            case "MONTHS":
                $complaintsFromDate.stop();
                $('#complaints-from-date').attr("type", "text");
                $('#complaints-from-date').val(lastWeek.getFullYear() + "-" + (lastWeek.getMonth() < 10 ? '0' : '') + lastWeek.getMonth());
                $complaintsToDate.stop();
                $('#complaints-to-date').attr("type", "text");
                $('#complaints-to-date').val(today.getFullYear() + "-" + (today.getMonth() < 10 ? '0' : '') + today.getMonth());
                break;
            case "DAYS":
                $complaintsFromDate.start();
                $complaintsFromDate.set('select', lastWeek);
                $complaintsToDate.start();
                $complaintsToDate.set('select', today);
                break;
        }
    });

    $('.complaints-datepicker').on('change', function () {
        if ($('#complaints-date-type').val() == "DAYS") {
            if ($(this).attr('id') === 'complaints-from-date') {
                $complaintsToDate.set('min', $(this).val());
            }
            if ($(this).attr('id') === 'complaints-to-date') {
                $complaintsFromDate.set('max', $(this).val());
            }
        }
    });

    var $productsForComplaintsMultiSelect = $("#product-input-for-complaints").karpo_multi_select({
        url: "/products/autocomplete?type=all",
        collection: "#selected-products-for-complaints",
        hideInput: "#product-hidden-input-for-complaints",
        elementUrl: "#product/"
    });

    var chartistLineComplaints;
    $("#show-complaints-graph").on("submit", function (e) {
        e.preventDefault();
        var fromDate = $('#complaints-from-date').val();
        var toDate = $('#complaints-to-date').val();
        if (checkDateValidate(fromDate, toDate, $('#complaints-date-type').val())) {
            lineLabelsForComplaints = $productsForComplaintsMultiSelect.getSelectedVal().slice(0);
            if($productsForComplaintsMultiSelect.getSelectedVal().length == 0) {
                lineLabelsForComplaints.push("All products")
            }
            $(this).siblings(".card-title").trigger("click");
            updateGraph("/complaints/graph", this, '#complaints-graph', chartistLineComplaints, lineLabelsForComplaints ,"#complaints-labels");
            $('#complaints-date-from-title').text(fromDate);
            $('#complaints-date-to-title').text(toDate);
        }
    });
    $("#show-complaints-graph").trigger("submit");

    </sec:authorize>
</script>