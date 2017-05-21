<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
</style>
<div class="content-body z-depth-1" data-page-name="Region #${region.id}">
    ${region.name}
</div>
<script>

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">

    $('.modal').modal({
            opacity: .5, // Opacity of modal background
            endingTop: '8%' // Starting top style attribute
        }
    );


    $("#update-group").on("submit", function (e) {
        e.preventDefault();
        send("#update-group", "/groups", "PUT").done(function () {
            $('.modal').modal('close');
            $(window).trigger('hashchange')
        })
    });

    $('#discount-input').karpo_autocomplete({
        url: "/discounts/autocomplete",
        label: "#selected-discount",
        defaultValue: "${group.discount.id} ${group.discount.title}",
        hideInput: "#discount-hidden-input"
    });



    var $groupProductSelect = $("#product-input").karpo_multi_select({
        url: "/products/autocomplete?type=withoutGroup",
        collection: "#selected-products",
        hideInput: "#product-hidden-input"
    });

    function fillCollection() {
        <c:forEach items="${products}" var="product">
            $groupProductSelect.addSelected("${product.id}" + " " + "${product.title}");
        </c:forEach>
    }
    fillCollection();


    </sec:authorize>

</script>
