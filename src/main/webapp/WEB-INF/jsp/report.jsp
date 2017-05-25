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
                <li class="tab col s3"><a href="#xml-wrapper">Excel</a></li>
            </ul>
        </div>
        <div id="xml-wrapper" class="col s12">
            <div class="row">
                <form class="col s12" id="downloadXml" method="get" action="/*/report/users">
                    <div class="row date-fields-wrapper">
                        <div class="input-field col s11 m4">
                            <input name="dateFrom" id="from-date" type="date" class="datepicker">
                            <label for="from-date">From</label>
                        </div>
                        <div class="input-field col s11 m4">
                            <input name="dateTo" id="to-date" type="date" class="datepicker">
                            <label for="to-date">To</label>
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
                                    <option value="6">CSR id</option>
                                </select>
                                <label>Order By</label>
                            </div>
                            <div class="col s12">
                                <button class="btn waves-effect waves-light" type="submit" id="download-orders" name="action">
                                    Download
                                    <i class="material-icons right">save</i>
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>

    $('ul#tabs').tabs();

    $('select').material_select();

    $('.datepicker').pickadate({
        selectMonths: true,
        format: 'yyyy-mm-dd',
        max: new Date(),
        closeOnSelect: true
    }).pickadate('picker');

    $("#user-input").karpo_multi_select({
        url: "/users/autocomplete",
        collection: "#selected-users",
        hideInput: "#user-hidden-input"
    });

    Materialize.updateTextFields();

    $("#download-orders").on("submit", function (e) {
        if (!$("#orderBySelect").val()) {
            Materialize.toast("Please, select order by", 5000);
            e.preventDefault();
        }
    });

</script>
