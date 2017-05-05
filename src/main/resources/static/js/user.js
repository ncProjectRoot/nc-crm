$(document).ready(function () {

    $(document).on("change", "#user_role", function () {

        if ($('#user_role option:selected').val() == 'ROLE_CUSTOMER') {
            $('.customer-field').css("display", "block");
        } else {
            $('.customer-field').css("display", "none");
        }
        $('#customer_contact_person').prop('checked', false);
        $('#submit-user-create').css("display", "block");
        $('#submit-user-create').html('Create ' + $('#user_role option:selected').text());
    });

    $(document).on("click", "#submit-user-create", function () {
        event.preventDefault();
        console.log("submit-user-create");
        var customerForm = "#form-user-create";
        registerUser(customerForm);
    });

    function registerUser(form) {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: "/user/registration",
            type: "POST",
            data: $(form).serialize(),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            statusCode: {
                201: function (data) {
                    console.log(data);
                    Materialize.toast("success", 10000);
                },
                500: function (data) {
                    console.log(data);
                    Materialize.toast("error", 10000);
                }
            }
        })
    }
});