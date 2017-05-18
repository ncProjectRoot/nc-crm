$(document).ready(function () {

    // checkNewMessage();

    $(".button-collapse").sideNav({
        menuWidth: 220,
        edge: 'left',
        draggable: false
    });

    $('.dropdown-button').dropdown({
        belowOrigin: true,
        alignment: 'left'
    });

    $(".burger").on('click', function () {
        $("#slide-out").toggleClass("mini-nav");
        $(".menu-block").toggleClass("hide-menu-block");
        $(".content").toggleClass("full-content");
    });

    $(document).on('click', "a.a-dummy", function (e) {
        e.preventDefault();
    });

    $(document).on("click", "a.a-logout", function (e) {
        $(this).find("form").submit();
    });

    $(".menu-element").on("click", function (e) {
        if ($(document).width() < 992) {
            $('.button-collapse').sideNav('hide');
        }
    });

    $('.button-collapse').sideNav('hide');

    downloadContent();
    $(window).on('hashchange', function () {
        downloadContent();
    });
});

// function checkNewMessage() {
//     var messageItem = $(".message-menu-item");
//     if (messageItem.data("new-message") != 0) {
//         messageItem.addClass("new-message");
//         messageItem.children().removeClass("black-text");
//         messageItem.children().addClass("red-text");
//     }
// }

function downloadContent() {
    var $contentBody = $(".content-body-wrapper");
    $contentBody.removeClass("content-body-visible");
    $(".progress").addClass("progress-active");
    $.get("/" + $(".menu-item-user").data("user-role") + "/" + location.hash.substr(1))
        .success(function (data) {
            window.setTimeout(function () {
                $contentBody.html(data);
                $(".progress").removeClass("progress-active");
                $contentBody.addClass("content-body-visible");
                var pageName = $contentBody.find(".content-body").data("page-name");
                $("#current-page").text(pageName);
                document.title = pageName;
            }, 500);
        }).error(function (e) {
        window.setTimeout(function () {
            $contentBody.html(e.status);
            $("#current-page").text(e.status);
            $(".progress").removeClass("progress-active");
            $contentBody.addClass("content-body-visible");
        }, 500);
    });
}

function send(form, url, type) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var xhr = $.ajax({
        url: url,
        type: type,
        data: $(form).serialize(),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        statusCode: {
            200: function (data) {
                Materialize.toast(xhr.getResponseHeader("successMessage"), 10000);
            },
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
    });
    return xhr;
}

jQuery.fn.karpo_status = function (activeStatusId) {
    var statusContainer = $(this[0]);
    if (!activeStatusId) {
        statusContainer.prepend("<option value='' disabled selected>Choose your option</option>")
    } else {
        statusContainer.find("option").each(function () {
            if ($(this).data("value") == activeStatusId) {
                $(this).attr("selected", true)
            }
            if ($(this).data("after-disabled") <= activeStatusId) {
                $(this).attr("disabled", true)
            }
        });
    }
    this.disabled = function (disabledStatusId) {
        statusContainer.find("option").each(function () {
            if ($(this).data("value") == disabledStatusId) {
                $(this).attr("disabled", true)
            }
        });
        statusContainer.material_select();
    }
    statusContainer.material_select();
    return this;
};
jQuery.fn.karpo_autocomplete = function (params) {
    var autocomplete = $(this[0]);
    var dataAutocomplete = {"null":null};
    var deleter;
    if (params.defaultValue.length > 1) {
        var defaultObject = convert(params.defaultValue);
        $(params.label).text("#" + params.defaultValue);
        autocomplete.val(params.defaultValue);
        $(params.hideInput).val(defaultObject.id);
        toggleDeleter();
    } else {
        $(params.hideInput).val(0);
    }
    autocomplete.on("input", function (event) {
        var typedText = autocomplete.val();
        $.get(params.url, {pattern: typedText}, function (array) {
            for (key in dataAutocomplete) {
                delete dataAutocomplete[key];
            }
            array.forEach(function (element) {
                dataAutocomplete[element.id + " " + element.value] = null;
            });
        });
    });
    autocomplete.autocomplete({
        data: dataAutocomplete,
        onAutocomplete: function(val) {
            $(params.label).text("#" + val);
            $(params.hideInput).val(convert(val).id);
            toggleDeleter();
        },
        limit: Infinity,
        minLength: 1
    });
    function toggleDeleter() {
        if (deleter) {
            $(params.label).next().remove();
            deleter.off("click", deleteValue);
            deleter = null;
        } else {
            deleter = $("<i class='material-icons tiny deleter'>delete_forever</i>");
            $(params.label).after(deleter)
            deleter.on("click", deleteValue);
        }
    }
    function deleteValue() {
        $(params.label).text("#");
        autocomplete.val("");
        $(params.hideInput).val(0);
        toggleDeleter();
    }
    function convert(val) {
        return {
            id: parseFloat(val.substring(0, val.indexOf(" "))),
            value: val.substring(val.indexOf(" ") + 1, val.length)
        }
    }
};
jQuery.fn.karpo_multi_select = function (params) {
    var autocomplete = $(this[0]);
    var dataAutocomplete = {"null": null};
    var selected = [];

    autocomplete.on("input", function (event) {
        var typedText = autocomplete.val();
        $.get(params.url, {pattern: typedText}, function (array) {
            for (key in dataAutocomplete) {
                delete dataAutocomplete[key];
            }
            array.forEach(function (element) {
                dataAutocomplete[element.id + " " + element.value] = null;
            });
        });
    });

    this.addSelected = function (val) {
        var id = parseFloat(val.substring(0, val.indexOf(" ")));
        if (selected.indexOf(id) == -1) {
            selected.push(id);
            var $deleter = $('<a href="#!" class="secondary-content a-dummy"><i class="material-icons">delete_forever</i></a>');
            var $div = $('<div>', {text: val}).append($deleter);
            $(params.collection).append($('<li class="collection-item"></li>').append($div));
            $deleter.data("id", id);
            $(params.hideInput).val(selected);
            $deleter.on("click", function () {
                $(this).closest(".collection-item").remove();
                selected.splice(selected.indexOf(parseFloat($(this).data("id"))), 1);
                $(params.hideInput).val(selected);
            })
        }
        autocomplete.val("");
    };
    autocomplete.autocomplete({
        data: dataAutocomplete,
        onAutocomplete: this.addSelected,
        limit: Infinity,
        minLength: 1
    });


    return this;
};

