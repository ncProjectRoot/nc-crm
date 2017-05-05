$(document).ready(function () {

    $(document).on("click", "#submit-admin-create", function () {
        event.preventDefault();
        console.log("submit-admin-create");
        var adminForm = "#form-admin-create";
        registerUser(adminForm);
    });

    $(document).on("click", "#submit-customer-create", function () {
        event.preventDefault();
        console.log("submit-customer-create");
        var customerForm = "#form-customer-create";
        registerUser(customerForm);
    });

    $(document).on("click", "#submit-сsr-create", function () {
        event.preventDefault();
        console.log("#submit-сsr-create");
        var csrForm = "#form-csr-create";
        registerUser(csrForm);
    });

    $(document).on("click", "#submit-pmg-create", function () {
        event.preventDefault();
        console.log("#submit-pmg-create");
        var pmgForm = "#form-pmg-create";
        registerUser(pmgForm);
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