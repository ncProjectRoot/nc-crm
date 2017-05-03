<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .content-body-wrapper {
        width: calc(100% - 20px * 2);
        margin: 20px;
        background-color: #fff;
    }

    table {
        width: calc(100% - 20px * 2);
        margin: 20px;
    }

</style>
<div class="content-header z-depth-1 valign-wrapper">
    <i class="black-text material-icons">note_add</i>
    <span>Complaint</span>
</div>
<div class="content-body-wrapper z-depth-1">
    <div class="col s12">
        <ul class="tabs" id="tabs">
            <li class="tab col s3"><a href="#swipe-create-complaint-form">Create</a></li>
            <li class="tab col s3"><a href="#swipe-history">History</a></li>
        </ul>
    </div>
    <div id="swipe-history" class="col s12">
        <table class="striped" id="historyTable">
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Order</th>
                <th>Date</th>
                <th>Status</th>
                <th>Message</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="i" items="${complaints}">
                <tr>
                    <td>${i.id}</td>
                    <td>${i.title}</td>
                    <td>${i.order.id} ${i.order.product.title} </td>
                    <td>${i.date.toLocalDate()} ${i.date.toLocalTime().getHour()}:${i.date.toLocalTime().getMinute()} </td>
                    <td>${i.status}</td>
                    <td>
                            <%--<a class="waves-effect waves-light btn" href="#modal${i.id}">Show</a>--%>
                        <button data-target="modal${i.id}" class="btn">show</button>
                        <div id="modal${i.id}" class="modal">
                            <div class="modal-content">
                                <h4>${i.title}</h4>
                                <p>${i.message.replaceAll("\\n", "<br>")}</p>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div id="swipe-create-complaint-form" class="col s12">
        <div class="row">
            <form id="createComplaintForm" class="col s12">
                <div class="row">
                    <div class="input-field col s6">
                        <i class="material-icons prefix">title</i>
                        <input id="title" type="text" class="validate" name="title" maxlength="50">
                        <label for="title">Title</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s6">
                        <i class="material-icons prefix">view_carousel</i>
                        <select id="orderSelect" name="orderId" size="5">
                            <option value="" disabled selected>Choose order</option>
                            <c:forEach items="${orders}" var="i">
                                <option value="${i.id}">${i.id} ${i.product.title} </option>
                            </c:forEach>
                        </select>
                        <label>Order Select</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s6">
                        <i class="material-icons prefix">mode_edit</i>
                        <textarea name="message" id="message" class="materialize-textarea"
                                  maxlength="400"></textarea>
                        <label for="message">Description</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col s6">
                        <button class="btn waves-effect waves-light" type="submit" name="action" id="createButton">
                            Create Copmplaint
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('ul.tabs').tabs();
        $('select').material_select();
        $('.modal').modal();

        $("#createComplaintForm").on("submit", function (e) {
                e.preventDefault();
                var titleLenth = $("#title").val().length;
                var messageLenth = $("#message").val().length;
                var selectVal = $("#orderSelect").val();
                if (titleLenth < 5 || titleLenth > 50) {
                    Materialize.toast("Title length must be 5-50", 5000, 'rounded');
                } else if (messageLenth < 5 || messageLenth > 400) {
                    Materialize.toast("Description length must be 5-400", 5000, 'rounded');
                } else if (!selectVal > 0) {
                    Materialize.toast("Order can't be empty", 5000, 'rounded');
                } else {
                    $('ul.tabs').tabs('select_tab', 'swipe-history');
                    $(".progress").addClass("progress-active");
                    $.post("/customer/createComplaint", $("#createComplaintForm").serialize(), function (data) {
                        $("#createComplaintForm")[0].reset();
                        var monthValue = checkLength(data.date.monthValue);
                        var dayOfMonth = checkLength(data.date.dayOfMonth);
                        var hour = data.date.hour;
                        var minute = data.date.minute;

                        var row = $("<tr>");
                        row.append($("<td>", {text: data.id}));
                        row.append($("<td>", {text: data.title}));
                        row.append($("<td>", {text: data.order.id + " " + data.order.product.title}));
                        row.append($("<td>", {
                            text: data.date.year + "-" +
                            monthValue + "-" +
                            dayOfMonth + " " +
                            hour + ":" +
                            minute
                        }))
                        row.append($("<td>", {text: data.status}));
                        var td = $("<td>");
                        td.html(' <button data-target="modal' + data.id + '" class="btn">show</button>' +
                            '<div id="modal' + data.id + '" class="modal"> ' +
                            '<div class="modal-content"> ' +
                            '<h4>' + data.title + '</h4> ' +
                            '<p>' + data.message.replace(/\n/g, '<br>') + '</p> ' +
                            ' </div> ' +
                            '  </div> ');
                        row.append(td);

                        $(".content-body tbody").prepend(row);

                        $('.modal').modal();

                        $(".progress").removeClass("progress-active");
                        Materialize.toast("Complaint with id " + data.id + " successfuly created", 5000, 'rounded');
                    });
                }
            }
        );
    });

    function checkLength(string) {
        if (string.toString().length < 2) {
            string = "0" + string;
        }
        return string;
    }

</script>

