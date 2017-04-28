$(document).ready(function () {

    // $.get("/page-information", {
    //     "href": location.hash.substr(1)
    // }).success(function (data) {
    //     console.log(data);
    //     var $contentHeader = $(".content-header");
    //     $contentHeader.find("i").text(data.icon);
    //     $contentHeader.find("span").text(data.title);
    //     document.title = data.title;
    // });

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

    // $(".menu-element").on('click', function (e) {
    //     var $contentHeader = $(".content-header");
    //     $contentHeader.find("i").text($(this).find('i').text());
    //     var title = $(this).find('h2').text();
    //     $contentHeader.find("span").text(title);
    //     document.title = title;
    // });

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
                document.title = $contentBody.find(".content-header i.material-icons").text();
            }, 1000);
        }).error(function (e) {
        window.setTimeout(function () {
            $contentBody.html(e.status);
            $(".progress").removeClass("progress-active");
            $contentBody.addClass("content-body-visible");
        }, 1000);
    });
}