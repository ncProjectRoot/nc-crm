<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
</style>
<div class="content-body" data-page-name="Messages">
    <div class="row col s12">
        <ul id="messages" class="collection with-header">
        </ul>
    </div>
    <input type="hidden" id="csrfToken" value="${_csrf.token}"/>
    <input type="hidden" id="csrfHeader" value="${_csrf.headerName}"/>
</div>
<script>
    setInterval(fetchMessage(),150000);
    fetchMessage();
    function fetchMessage() {
        $.get("/messages").success(function (data) {
            var ul = $('#messages');
            ul.children().remove();
            ul.append('<li class="collection-header"><h4>Messages</h4></li>');
            $.each(data, function (i, item) {
                var div = $("<div>").addClass("row");
                var button = $('<a class="waves-effect waves-light btn right" id="' + item.id + '" >' + getStatus(item.status) + '</a>');
                button.click(function () {
                    submitOrder(item.id, item.status);
                });
                var li = $("<li>").addClass("collection-item");
                div.append(item.title +  ', status - ' + item.status + ', prefered date for activate ' + item.date);
                div.append(button);
                li.append(div);
                ul.append(li);
            })
        })
    }


    function submitOrder(id, status) {
        var url = '/orders/' + id + '/' + getStatus(status);
        sendPut(id, url);
    }

    function getStatus(status) {
        if (status == 'PROCESSING') {
            return 'activate';
        }else if (status == 'REQUEST_TO_RESUME'){
            return 'resume';
        }else if (status == 'REQUEST_TO_PAUSE'){
            return 'pause';
        }else if (status == 'REQUEST_TO_DISABLE'){
            return 'disable';
        }
    }

    function sendPut(id, url) {
        var token = $('#csrfToken').val();
        var header = $('#csrfHeader').val();

        var xhr = $.ajax({
            type: 'PUT',
            url: url,
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            statusCode: {
                200: function (data) {
                    Materialize.toast(xhr.getResponseHeader("successMessage"), 5000);
                    $('#' + id).remove();
                }
            }
        });
        return xhr;
    }

</script>
