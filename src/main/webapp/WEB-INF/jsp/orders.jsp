<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    #content-body-wrapper {
        width: calc(100% - 20px * 2);
        margin: 0 auto;
        margin-top: 15px;
        background-color: #fff;
    }

    #content-body {
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        align-items: center;
    }

    #content-body.row .col {
        margin-left: 0;
    }

    #content-body.row .field-search {
        margin-top: 40px;
    }

    .field-search .autocomplete-content {
        position: absolute;
    }

    #content-body .table-wrapper {
        min-height: 420px;
    }

    .pagination li.active {
        background-color: rgba(3, 155, 229, 0.7);
    }

    /*.content-body thead a {
        color: #007bb7;
    }*/

    #content-body .sorted-element {
        color: #38474e;
    }

    .product-status-deleter, .status-deleter {
        display: none;
    }

    #content-body .dropdown-content li > a {
        color: #38474e;
    }

    #content-body .message {
        margin-top: 100px;
        display: none;
    }

    #content-body .preloader-wrapper {
        margin-top: 100px;
    }

    #content-body .preloader-wrapper:not(.active) {
        display: none;
    }

    #content-body .table-pages.col ul li {
        display: inline;
        margin-left: 4px;
        margin-right: 4px;
    }

    #content-body .table-pages .table-skip-a {
        margin-top: 3px;
    }

    #content-body .footer {
        height: 100px;
        width: 100%
    }
</style>
<div class="content-header z-depth-1 valign-wrapper">
    <i class="black-text material-icons">receipt</i>
    <span>Orders</span>
</div>
<div id="content-body-wrapper">
    <div id="content-body" class="row">
        <div class="input-field col s12 m8 l6 field-search">
            <i class="material-icons prefix">search</i>
            <div class="chips" id="chips-search"></div>
            <!-- <label for="autocomplete-input">Search</label> -->
        </div>
        <div class="table-wrapper col s11 center-align">
            <table class="striped responsive-table centered ">
                <thead>
                <tr>
                    <th><a href="#!" class="sorted-element a-dummy" data-field="id">#<span>&#9660;</span></a></th>
                    <th>

                        <a class='dropdown-button dropdown-status-button a-dummy' href='#!'
                           data-activates='dropdown-status'><span class="value">Status</span></a>
                        <span class="status-deleter"><a href="#!" class="a-dummy">&#215;</a></span>
                        <ul id='dropdown-status' class='dropdown-content'>
                            <li><a href="#!" class="a-dummy">New</a></li>
                            <li><a href="#!" class="a-dummy">In queue</a></li>
                            <li><a href="#!" class="a-dummy">Processing</a></li>
                            <li><a href="#!" class="a-dummy">Active</a></li>
                            <li><a href="#!" class="a-dummy">Disabled</a></li>
                            <li><a href="#!" class="a-dummy">Disabled</a></li>
                            <li><a href="#!" class="a-dummy">Paused</a></li>
                        </ul>

                    </th>
                    <th><a href="#!" class="sorted-element a-dummy" data-field="product-id">Product Id</a></th>
                    <th><a href="#!" class="sorted-element a-dummy" data-field="product-title">Product Title</a></th>
                    <th>

                        <a class='dropdown-button dropdown-product-status-button a-dummy' href='#!'
                           data-activates='dropdown-product-status'><span class="value">Product Status</span>
                        </a>
                        <span class="product-status-deleter"><a href="#!" class="a-dummy">&#215;</a></span>
                        <ul id='dropdown-product-status' class='dropdown-content'>
                            <li><a href="#!" class="a-dummy">Planned</a></li>
                            <li><a href="#!" class="a-dummy">Actual</a></li>
                            <li><a href="#!" class="a-dummy">Outdated</a></li>
                        </ul>

                    </th>
                    <th><a href="#!" class="sorted-element a-dummy" data-field="customer">Customer</a></th>
                    <th><a href="#!" class="sorted-element a-dummy" data-field="csr">CSR</a></th>
                    <th><a href="#!" class="sorted-element a-dummy" data-field="date-finish">Date Finish</a></th>
                    <th><a href="#!" class="sorted-element a-dummy" data-field="preferred-date">Preferred Date</a></th>

                </tr>
                </thead>
                <tbody></tbody>
            </table>

            <div class="message">Unfortunately, your request not found</div>

            <div class="preloader-wrapper big active ">
                <div class="spinner-layer spinner-blue-only">
                    <div class="circle-clipper left">
                        <div class="circle"></div>
                    </div>
                    <div class="gap-patch">
                        <div class="circle"></div>
                    </div>
                    <div class="circle-clipper right">
                        <div class="circle"></div>
                    </div>
                </div>
            </div>

        </div>

        <ul class="pagination col">
            <li class="page-left"><a href="#!" class="a-dummy"><i class="material-icons">chevron_left</i></a></li>
            <li class="page-right"><a href="#!" class="a-dummy"><i class="material-icons">chevron_right</i></a></li>
        </ul>
        <div class="footer col"></div>
    </div>
</div>

<script>
    var data = {
        length: 7 * 11,
        array: []
    }
    var jsonAjaxElement = {
        id: 123,
        status: "Processing",
        productId: 2312,
        productTitle: "Telecom Life",
        productStatus: "Outdated",
        customer: 213,
        csr: 23,
        dateFinish: "21.05.2017",
        preferredDate: "21.05.2017"
    }
    for (var i = 0; i < 7; i++) {
        data.array.push(jsonAjaxElement);
    }

    //////////


    var countTr = 7;
    var countPagesVisibleNearCurrent = 2;
    var currentPage = 1;
    var countTablePages;
    var dataAutocomplete = {
        'Apple': null,
        'Microsoft': null,
        'Google': null
    }
    var ajaxParametersForTable = {
        orderBy: "id",
        orderByOption: "DESC",
        searchArray: [],
        status: "",
        productStatus: "",
        firstRowNum: 1,
        lastRowNum: countTr
    };

    $('#chips-search').material_chip({
        placeholder: 'Type and enter',
        secondaryPlaceholder: 'Search',
        autocompleteOptions: {
            data: dataAutocomplete,
            limit: Infinity,
            minLength: 1
        }
    });

    $('.dropdown-status-button, .dropdown-product-status-button').dropdown({
        belowOrigin: true,
        alignment: 'left'
        // stopPropagation: false
    });

    $('#chips-search').on('chip.add', function (e, chip) {
        ajaxParametersForTable.searchArray.push(chip.tag)
        downloadTable();
    });

    $('#chips-search').on('chip.delete', function (e, chip) {
        var idx = ajaxParametersForTable.searchArray.indexOf(chip.tag)
        ajaxParametersForTable.searchArray.splice(idx, 1)
        downloadTable();
    });

    var needAjaxSearchChips = true;
    $("#chips-search input").on("keyup", function (event) {
        var typedText = $("#chips-search input").val();
        // console.log(typedText + " " + typedText.length)
        if (needAjaxSearchChips && typedText.length >= 1) {
            //ajax
            setTimeout(function () {
                var newData = ["qwer", "asdf", "zxcv", "poiu"];
                for (key in dataAutocomplete) {
                    // console.log("--- delete " + key)
                    delete dataAutocomplete[key];
                }
                newData.forEach(function (element) {
                    dataAutocomplete[element] = null;
                })
                needAjaxSearchChips = false;
                $("#chips-search input").trigger("keypress");
                needAjaxSearchChips = true;
            }, 100);
        }
    })

    downloadTable();
    function downloadTable() {
        ajaxParametersForTable.lastRowNum = currentPage * countTr;
        ajaxParametersForTable.firstRowNum = ajaxParametersForTable.lastRowNum - countTr + 1;

        $(".content-body tbody").empty();
        $(".preloader-wrapper").addClass("active");
        //ajax
        console.log(ajaxParametersForTable)
        setTimeout(function () {
            $(".preloader-wrapper").removeClass("active");
            $(".content-body tbody").empty();
            fillTable(data);
        }, 500);

    }


    function fillTable(data) {
        if (data.length == 0) {
            $(".content-body .message").show();
            countTablePages = 0;
        } else {
            $(".content-body .message").hide();

            countTablePages = Math.ceil(data.length / countTr);

            data.array.forEach(function (element) {
                var tr = $("<tr>");
                tr.append($("<td>", {text: element.id}));
                tr.append($("<td>", {text: element.status}));
                tr.append($("<td>", {text: element.productId}));
                tr.append($("<td>", {text: element.productTitle}));
                tr.append($("<td>", {text: element.productStatus}));
                tr.append($("<td>", {text: element.customer}));
                tr.append($("<td>", {text: element.csr}));
                tr.append($("<td>", {text: element.dateFinish}));
                tr.append($("<td>", {text: element.preferredDate}));
                $(".content-body tbody").append(tr);
            });
        }

        fillPagination();
    }

    function fillPagination() {
        $(".pagination li:not(:first-child, :last-child)").remove();
        if (countTablePages == 0) {
            return;
        }

        addTablePage(1);
        var firstPageNearCurrent = currentPage - countPagesVisibleNearCurrent;
        var lastPageNearCurrent = currentPage + countPagesVisibleNearCurrent;
        if (firstPageNearCurrent > 2) {
            addTablePageEllipsis();
        }
        if (firstPageNearCurrent <= 1) {
            firstPageNearCurrent = 2;
            // lastPageNearCurrent++;
        }
        if (lastPageNearCurrent >= countTablePages) {
            lastPageNearCurrent = countTablePages - 1;
        }
        for (var i = firstPageNearCurrent; i <= lastPageNearCurrent; i++) {
            addTablePage(i);
        }
        if (lastPageNearCurrent < countTablePages - 1) {
            addTablePageEllipsis();
        }
        addTablePage(countTablePages);

        checkVisibleChevronsAndActivePage();
    }

    function addTablePage(number) {
        var aAttributes = {
            href: "#",
            class: "black-text a-dummy",
            text: number
        };
        var liAttributes = {
            class: "waves-effect number-page"
        }
        $("<li>", liAttributes).append($("<a>", aAttributes)).insertBefore(".pagination li:last-child");
    }

    function addTablePageEllipsis() {
        $("<li>", {class: "disabled"}).append($("<a>", {text: "..."})).insertBefore(".pagination li:last-child");
    }

    function checkVisibleChevronsAndActivePage() {
        $(".pagination li.number-page.active").removeClass("active");
        $('.pagination li.number-page:contains("' + currentPage + '")').filter(function (index) {
            return $(this).text() == currentPage;
        }).addClass("active");
        ;
        if (currentPage == 1) {
            $(".pagination li:first-child").addClass("disabled");
            $(".pagination li:first-child").removeClass("waves-effect");
        } else {
            $(".pagination li:first-child").removeClass("disabled");
            $(".pagination li:first-child").addClass("waves-effect");
        }
        if (currentPage == countTablePages) {
            $(".pagination li:last-child").addClass("disabled");
            $(".pagination li:last-child").removeClass("waves-effect");
        } else {
            $(".pagination li:last-child").removeClass("disabled");
            $(".pagination li:last-child").addClass("waves-effect");
        }
    }

    $(document).on("click", ".pagination li.number-page", function (e) {
        var pageClick = $(this).text();
        if (currentPage != pageClick) {
            currentPage = parseInt(pageClick);
            downloadTable();
        }
    })

    $(document).on("click", ".pagination li.page-left:not(.disabled)", function (e) {
        currentPage--;
        downloadTable();
    })

    $(document).on("click", ".pagination li.page-right:not(.disabled)", function (e) {
        currentPage++;
        downloadTable();
    })

    $("#dropdown-status li").on("click", function () {
        var status = $(this).find("a").text();
        $(".dropdown-status-button .value").text(status);
        $(".status-deleter").show();
        ajaxParametersForTable.status = status;
        downloadTable();
    })

    $(".status-deleter").on("click", function () {
        $(".dropdown-status-button .value").text("Status");
        ajaxParametersForTable.status = "";
        $(".status-deleter").hide();
        downloadTable();
    });

    $("#dropdown-product-status li").on("click", function () {
        var productStatus = $(this).find("a").text();
        $(".dropdown-product-status-button .value").text(productStatus);
        $(".product-status-deleter").show();
        ajaxParametersForTable.productStatus = productStatus;
        downloadTable();
    })

    $(".product-status-deleter").on("click", function () {
        $(".dropdown-product-status-button .value").text("Product Status");
        ajaxParametersForTable.productStatus = "";
        $(".product-status-deleter").hide();
        downloadTable();
    });

    var oldSortedElement = $(".sorted-element").first();
    $(".sorted-element").on("click", function () {
        var newSortedElement = $(this);
        if (newSortedElement.is(oldSortedElement)) {

            if (ajaxParametersForTable.orderByOption == "DESC") {
                newSortedElement.find("span").html("&#9650;");
                ajaxParametersForTable.orderByOption = "ASC";
            } else {
                newSortedElement.find("span").html("&#9660;");
                ajaxParametersForTable.orderByOption = "DESC";
            }
        } else {
            oldSortedElement.find("span").remove();
            newSortedElement.append($("<span>", {html: "&#9660;"}));
            ajaxParametersForTable.orderByOption = "DESC";
            ajaxParametersForTable.orderBy = newSortedElement.data("field");
            oldSortedElement = newSortedElement;
        }
        downloadTable();
    });
</script>