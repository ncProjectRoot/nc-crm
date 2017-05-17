<style>
    .table-container {
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        align-items: center;
    }

    .table-container.row .col {
        margin-left: 0;
    }

    .table-container.row .field-search {
        margin-top: 40px;
    }

    .field-search .autocomplete-content {
        position: absolute;
    }

    .table-container .table-wrapper {
        min-height: 420px;
    }

    .table-container .pagination li.active {
        background-color: rgba(3, 155, 229, 0.7);
    }

    /*.table-container thead a {
        color: #007bb7;
    }*/

    .table-container .sorted-element {
        color: #38474e;
    }

    .table-container .deleter {
        display: none;
    }

    .table-container .dropdown-content li > a {
        color: #38474e;
    }

    .table-container .message {
        margin-top: 100px;
        display: none;
    }

    .table-container .preloader-wrapper {
        margin-top: 100px;
    }

    .table-container .preloader-wrapper:not(.active) {
        display: none;
    }

    .table-container .table-pages.col ul li {
        display: inline;
        margin-left: 4px;
        margin-right: 4px;
    }

    .table-container .table-pages .table-skip-a {
        margin-top: 3px;
    }

    .table-container .footer {
        height: 20px;
        width: 100%
    }

    .table-container th {
        position: relative;
    }

    .table-container .search-element {
        cursor: pointer;
        position: absolute;
        top: 3px;
        left: 0;
        right: 0;
        opacity: 0.3;
        transition: opacity 0.5s;
    }

    .table-container .search-element:hover {
        opacity: 1;
    }

    .input-field .prefix ~ .autocomplete-content {
        max-height: 250px;
    }

    .highlighted-row {
        background-color: #f2f2f2;
    }

    #bulk-card {
        position: fixed;
        display: none;
        top: 15%;
        right: -20%;
        width: 50%;
    }

    .edit-selected-items {
        margin-bottom: 0;
        margin-top: 15%;
    }

    #bulk-submit {
        margin-top: 40%;
    }

</style>