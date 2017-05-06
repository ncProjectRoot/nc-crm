$(document).ready(function () {
    $('select').material_select();

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
        var userForm = "#form-user-create";
        var url = "/user/registration";
        sendPost(userForm, url);
    });

    function sendPost(form, url) {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var xhr = $.ajax({
            url: url,
            type: "POST",
            data: $(form).serialize(),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            statusCode: {
                201: function (data) {
                    Materialize.toast(xhr.getResponseHeader("successMessage"), 10000);
                },
                417: function (data) {
                    Materialize.toast(xhr.getResponseHeader("validationMessage"), 10000);
                },
                500: function (data) {
                    Materialize.toast(xhr.getResponseHeader("errorMessage"), 10000, 'red');
                }
            }
        })
    }


//      load products without group
    loadProductsWithoutGroup();
    function loadProductsWithoutGroup() {
        $.get("/csr/load/productWithoutGroup").success(function (data) {
            var prods = $('#products_without_group');
            prods.children().remove();
            prods.append('<option value="" disabled selected>Choose products</option>')
            $.each(data, function (i, item) {
                prods.append($('<option/>', {
                    value: item.id,
                    text: item.title + ' - ' + item.statusName
                }));
            });
            prods.material_select('updating');
        });
    }

//        create product
    $(document).on("click", "#submit-product", function () {
        event.preventDefault();
        var url = "/csr/addProduct";
        var form = "#addProduct";
        sendPost(form, url);
        $(form)[0].reset();
        loadProductsWithoutGroup();
    });

//      create discount
    $(document).on("click", "#submit-discount", function (e) {
        event.preventDefault();
        var url = "/csr/addDiscount";
        var form = "#addDiscount";
        sendPost(form, url);
        $(form)[0].reset();
    });


//        create group
    $(document).on("click","#submit-group", function (e) {
        event.preventDefault();
        var url = "/csr/addGroup";
        var form = "#addGroup";
        sendPost(form, url);
        $(form)[0].reset();
    });
});