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
                    <th><a href="#!" class="sorted-element a-dummy" data-field="1">#<span>&#9660;</span></a></th>
                    <th>

                        <a class='dropdown-button dropdown-status-button a-dummy' href='#!'
                           data-activates='dropdown-status'><span class="value">Status</span></a>
                        <span class="status-deleter"><a href="#!" class="a-dummy">&#215;</a></span>
                        <ul id='dropdown-status' class='dropdown-content'>
                            <li><a href="#!" class="a-dummy" data-status-id="4">New</a></li>
                            <li><a href="#!" class="a-dummy" data-status-id="5">In queue</a></li>
                            <li><a href="#!" class="a-dummy" data-status-id="6">Processing</a></li>
                            <li><a href="#!" class="a-dummy" data-status-id="7">Active</a></li>
                            <li><a href="#!" class="a-dummy" data-status-id="8">Disabled</a></li>
                            <li><a href="#!" class="a-dummy" data-status-id="9">Paused</a></li>
                        </ul>

                    </th>
                    <th><a href="#!" class="sorted-element a-dummy" data-field="2">Product Id</a></th>
                    <th><a href="#!" class="sorted-element a-dummy" data-field="3">Product Title</a></th>
                    <th>

                        <a class='dropdown-button dropdown-product-status-button a-dummy' href='#!'
                           data-activates='dropdown-product-status'><span class="value">Product Status</span>
                        </a>
                        <span class="product-status-deleter"><a href="#!" class="a-dummy">&#215;</a></span>
                        <ul id='dropdown-product-status' class='dropdown-content'>
                            <li><a href="#!" class="a-dummy" data-status-id="10">Planned</a></li>
                            <li><a href="#!" class="a-dummy" data-status-id="11">Actual</a></li>
                            <li><a href="#!" class="a-dummy" data-status-id="12">Outdated</a></li>
                        </ul>

                    </th>
                    <th><a href="#!" class="sorted-element a-dummy" data-field="4">Customer</a></th>
                    <th><a href="#!" class="sorted-element a-dummy" data-field="5">CSR</a></th>
                    <th><a href="#!" class="sorted-element a-dummy" data-field="6">Date Finish</a></th>
                    <th><a href="#!" class="sorted-element a-dummy" data-field="7">Preferred Date</a></th>

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

    var countTr = 7;
    var countPagesVisibleNearCurrent = 2;
    var currentPage = 1;
    var countTablePages;
    var dataAutocomplete = {'': null};
    var typedKeywords = [];
    var ajaxParametersForTable = {
        orderBy: 1,
        desc: false,
        keywords: function () {
            return typedKeywords.join("\n");
        },
        statusId: 0,
        productStatusId: 0,
        rowLimit: countTr,
        rowOffset: 0
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
        typedKeywords.push(chip.tag);
        currentPage = 1;
        downloadTable();
    });

    $('#chips-search').on('chip.delete', function (e, chip) {
        var idx = typedKeywords.indexOf(chip.tag)
        typedKeywords.splice(idx, 1);
        currentPage = 1;
        downloadTable();
    });

    $("#chips-search input").on("input", function (event) {
        var typedText = $("#chips-search input").val();
        if (typedText.length > 1) {
            $.get("csr/load/productNames", {likeTitle: typedText}, function (productNames) {
                for (key in dataAutocomplete) {
                    delete dataAutocomplete[key];
                }
                productNames.forEach(function (element) {
                    dataAutocomplete[element] = null;
                });
            });
        }
    })

    downloadTable();
    function downloadTable() {
        ajaxParametersForTable.rowOffset = (currentPage - 1) * countTr;
        console.log(ajaxParametersForTable)
        $(".content-body tbody").empty();
        $(".preloader-wrapper").addClass("active");
        $.get("csr/load/orders", ajaxParametersForTable, function (data) {
            console.log(data)
            $(".preloader-wrapper").removeClass("active");
            $(".content-body tbody").empty();
            fillTable(data);
        });

    }


    function fillTable(data) {
        if (data.length == 0) {
            $(".content-body .message").show();
            countTablePages = 0;
        } else {
            $(".content-body .message").hide();

            countTablePages = Math.ceil(data.length / countTr);

            data.orders.forEach(function (element) {
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
        if (countTablePages != 1) {
            addTablePage(countTablePages);
        }

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
    });

    $(document).on("click", ".pagination li.page-right:not(.disabled)", function (e) {
        currentPage++;
        downloadTable();
    });

    $("#dropdown-status li").on("click", function () {
        var status = $(this).find("a");
        $(".dropdown-status-button .value").text(status.text());
        $(".status-deleter").show();
        ajaxParametersForTable.statusId = status.data("status-id");
        currentPage = 1;
        downloadTable();
    })

    $(".status-deleter").on("click", function () {
        $(".dropdown-status-button .value").text("Status");
        ajaxParametersForTable.statusId = 0;
        $(".status-deleter").hide();
        currentPage = 1;
        downloadTable();
    });

    $("#dropdown-product-status li").on("click", function () {
        var productStatus = $(this).find("a");
        $(".dropdown-product-status-button .value").text(productStatus.text());
        $(".product-status-deleter").show();
        ajaxParametersForTable.productStatusId = productStatus.data("status-id");
        currentPage = 1;
        downloadTable();
    })

    $(".product-status-deleter").on("click", function () {
        $(".dropdown-product-status-button .value").text("Product Status");
        ajaxParametersForTable.productStatusId = 0;
        $(".product-status-deleter").hide();
        currentPage = 1;
        downloadTable();
    });

    var oldSortedElement = $(".sorted-element").first();
    $(".sorted-element").on("click", function () {
        var newSortedElement = $(this);
        if (newSortedElement.is(oldSortedElement)) {

            if (ajaxParametersForTable.desc == true) {
                newSortedElement.find("span").html("&#9650;");
                ajaxParametersForTable.desc = false;
            } else {
                newSortedElement.find("span").html("&#9660;");
                ajaxParametersForTable.desc = true;
            }
        } else {
            oldSortedElement.find("span").remove();
            newSortedElement.append($("<span>", {html: "&#9660;"}));
            ajaxParametersForTable.desc = true;
            ajaxParametersForTable.orderBy = parseInt(newSortedElement.data("field"));
            oldSortedElement = newSortedElement;
        }
        currentPage = 1;
        downloadTable();
    });
</script>