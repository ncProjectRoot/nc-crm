$(document).ready(function () {

    checkNewMessage();

    $(".button-collapse").sideNav({
        menuWidth: 220,
        edge: 'left',
        // closeOnClick: true,
        draggable: true
    });

    $('.dropdown-button').dropdown({
        belowOrigin: true,
        alignment: 'left'
        // stopPropagation: false
    });

    $(".burger").on('click', function () {
        $("#slide-out").toggleClass("mini-nav");
        $(".menu-block").toggleClass("hide-menu-block");
        $(".content").toggleClass("full-content");
    });

    $("a.a-dummy").on('click', function (e) {
        e.preventDefault();
    });

    $("a.a-logout").on("click", function (e) {
        $(this).find("form").submit();
    })

    downloadContent();
    $(window).on('hashchange', function () {
        downloadContent();
    });
});

function checkNewMessage() {
    var messageItem = $(".message-menu-item");
    if (messageItem.data("new-message") != 0) {
        messageItem.addClass("new-message");
        messageItem.children().removeClass("black-text");
        messageItem.children().addClass("red-text");
    }
}

function downloadContent() {
    var $contentBody = $(".content-body");
    $contentBody.removeClass("content-body-visible");
    $(".progress").addClass("progress-active");
    $.get("/" + $(".menu-item-user").data("user-role") + "/" + location.hash.substr(1))
        .success(function (data) {
            window.setTimeout(function () {
                $contentBody.html(data);
                $(".progress").removeClass("progress-active");
                $contentBody.addClass("content-body-visible");
                document.title = $contentBody.find(".content-header span").text();
            }, 1000);
        }).error(function (e) {
        window.setTimeout(function () {
            $contentBody.html(e.status);
            $(".progress").removeClass("progress-active");
            $contentBody.addClass("content-body-visible");
        }, 1000);
    });
}