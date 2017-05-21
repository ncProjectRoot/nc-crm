<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>

    #region-page-wrapper {
        transition: margin-top 0.5s, opacity 0.5s linear;
        margin-top: 50px;
        opacity: 0;
    }

    #region-page-wrapper.content-body-visible {
        margin-top: 0;
        opacity: 1;
    }

    #search-regions-wrapper {
        min-height: 150px;
    }

    #search-regions-wrapper .input-field.regions-input-field {
        float: none;
        margin: 35px auto;
    }

</style>
<div class="content-body z-depth-1 row" data-page-name="Regions">
    <div class="col s12">
        <ul id="tabs" class="tabs">
            <li class="tab col s3"><a class="active" href="#search-regions-wrapper">Search regions</a></li>
        </ul>
    </div>
    <div id="search-regions-wrapper" class="col s12">
        <div class="input-field col s6 regions-input-field">
            <i class="material-icons prefix">search</i>
            <input type="text" id="regions-input" class="autocomplete">
            <input type="hidden" id="regions-hidden-input" name="discountId"/>
            <label for="regions-input">Select regions: <span id="selected-regions"></span></label>
        </div>
    </div>
</div>
<div id="region-page-wrapper"></div>
<script>
    $('ul#tabs').tabs();

    $('#regions-input').karpo_autocomplete({
        url: "/regions/autocomplete",
        label: "#selected-regions",
        defaultValue: "",
        hideInput: "#regions-hidden-input"
    }).on("onAutocompleteItem", function (event, id) {
        var $contentRegion = $("#region-page-wrapper");
        $contentRegion.removeClass("content-body-visible");
        $(".progress").addClass("progress-active");
        $.get("/" + $(".menu-item-user").data("user-role") + "/region/" + id).success(function (data) {
            window.setTimeout(function () {
                $contentRegion.html(data);
                $(".progress").removeClass("progress-active");
                $contentRegion.addClass("content-body-visible");
            }, 500);
        });
    })
</script>