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
            }, 1000);
        }).error(function (e) {
        window.setTimeout(function () {
            $contentBody.html(e.status);
            $(".progress").removeClass("progress-active");
            $contentBody.addClass("content-body-visible");
        }, 1000);
    });
}