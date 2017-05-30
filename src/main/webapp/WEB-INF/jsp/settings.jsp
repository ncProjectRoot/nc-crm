<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>

    #password-wrapper {
        padding-top: 50px;
        padding-bottom: 50px;
    }

    #email-wrapper .input-field.col, #password-wrapper .input-field.col {
        float: none;
        margin: 0 auto;
    }

</style>
<div class="content-body z-depth-1" data-page-name="Settings">
    <div class="row">
        <div class="col s12">
            <ul id="tabs" class="tabs">
                <li class="tab col s3"><a class="active" href="#password-wrapper">Password</a></li>
            </ul>
        </div>
        <div id="password-wrapper" class="col s12">
            <form id="update-password">
                <div class="row">
                    <div class="input-field col s12 m6">
                        <input id="old-password" type="password" class="validate" name="oldPassword">
                        <label for="old-password">Old Password</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12 m6">
                        <input id="new-password" type="password" class="validate" name="newPassword">
                        <label for="new-password">New Password</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12 m6">
                        <input id="confirm-password" type="password" class="validate">
                        <label for="confirm-password">Confirm Password</label>
                    </div>
                </div>
                <div class="row center-align">
                    <button class="btn waves-effect waves-light" type="submit" name="action">Update</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>

    $('ul#tabs').tabs();
    Materialize.updateTextFields();

    $("#update-password").on("submit",  function (e) {
        e.preventDefault();
        if ($("#new-password").val() != $("#confirm-password").val()) {
            Materialize.toast("New password and confirm password don't  match", 5000);
        } else {
            send("#update-password", "/users/password", "PUT").complete(function(xhr, textStatus) {
                if (xhr.status == 400) {
                    Materialize.toast("Wrong password", 5000);
                }
            })
        }
    });

</script>