<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>

    #search-regions-wrapper {
        min-height: 450px;
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
<script>
    $('ul#tabs').tabs();

    $('#regions-input').karpo_autocomplete({
        url: "/regions/autocomplete",
        label: "#selected-regions",
        defaultValue: "",
        hideInput: "#regions-hidden-input"
    }).on("onAutocompleteItem", function (event, id) {
        console.log(id)
    })
</script>