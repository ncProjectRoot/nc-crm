<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>

    .container {
        padding-top: 50px;
        padding-bottom: 100px;
    }

    h4, h5, h6 {
        text-align: center;
        word-wrap: break-word;
    }

    .region-groups {
        margin: 40px auto;
    }

</style>
<div class="content-body z-depth-1" data-page-name="Region #${region.id}">
    <div class="container">
        <h4 class="title field">${region.name}</h4>
        <div class="divider"></div>
        <div class="row region-groups">
            <div class="col s10 m6">
                <form id="form-update-region">
                    <input type="hidden" name="id" value="${region.id}">
                    <div class="row">
                        <div class="input-field col s12">
                            <i class="material-icons prefix">bubble_chart</i>
                            <input type="text" id="group-input" class="autocomplete">
                            <input type="hidden" id="group-hidden-input" name="groupIds"/>
                            <label for="group-input">Select group</label>
                        </div>
                        <div class="input-field col s12">
                            <button class="btn waves-effect waves-light" id="submit-product" type="submit" name="action">Update
                                <i class="material-icons right">send</i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col s10 m6">
                <ul id="selected-groups" class="collection"></ul>
            </div>
        </div>
    </div>
</div>
<script>

    var $groupInput = $("#group-input").karpo_multi_select({
        url: "/groups/autocomplete",
        collection: "#selected-groups",
        hideInput: "#group-hidden-input"
    });

    <c:forEach items="${groups}" var="group">
    $groupInput.addSelected("${group.name}", ${group.id});
    </c:forEach>

    $("#form-update-region").on("submit", function (e) {
        e.preventDefault();
        send("#form-update-region", "/regions", "PUT").done(function () {
//            $('.modal').modal('close');
//            $(window).trigger('hashchange')
        })
    });

</script>
