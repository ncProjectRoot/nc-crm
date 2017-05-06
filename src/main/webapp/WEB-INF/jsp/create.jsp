<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .content-body-wrapper {
        width: calc(100% - 20px * 2);
        margin: 20px;
        background-color: #fff;
    }

    /* Always set the map height explicitly to define the size of the div * element that contains the map. */
    #map {
        height: 270px;
    }
</style>
<div class="content-header z-depth-1 valign-wrapper">
    <i class="black-text material-icons">note_add</i>
    <span>Create</span>
</div>
<div class="content-body-wrapper z-depth-1">
    <ul id="tabs-swipe-demo" class="tabs">
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
            <li class="tab col s3"><a href="#swipe-user-form">User</a></li>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
            <li class="tab col s3"><a href="#swipe-product-form">Product</a></li>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
            <li class="tab col s3"><a href="#swipe-discount-form">Discount</a></li>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
            <li class="tab col s3"><a href="#swipe-group-form">Group</a></li>
        </sec:authorize>
    </ul>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
        <div id="swipe-user-form" class="col s12">
            <div class="row">
                <form id="form-user-create" class="col s12">
                    <div class="row">
                        <div class="col s6">
                            <div class="input-field">
                                <i class="material-icons prefix">account_circle</i>
                                <input id="user_first_name" name="firstName" type="text" class="validate">
                                <label for="user_first_name">First Name</label>
                            </div>
                            <div class="input-field">
                                <i class="material-icons prefix">account_circle</i>
                                <input id="user_middle_name" name="middleName" type="text" class="validate">
                                <label for="user_middle_name">Middle Name</label>
                            </div>
                            <div class="input-field">
                                <i class="material-icons prefix">account_circle</i>
                                <input id="user_last_name" name="lastName" type="text" class="validate">
                                <label for="user_last_name">Last Name</label>
                            </div>
                            <div class="input-field customer-field">
                                <i class="material-icons prefix">location_on</i>
                                <input type="text" id="customer_address"/>
                                <label for="customer_address">Address</label>
                            </div>
                            <div class="input-field customer-field">
                                <i class="material-icons prefix">open_with</i>
                                <input id="customer_address_details" name="addressDetails" type="text" class="validate">
                                <label for="customer_address_details">Address Details</label>
                            </div>
                            <div>
                                <div class="customer-field" id="map" style="width: auto; height: 270px;"></div>
                            </div>
                        </div>
                        <div class="col s6">
                            <div class="input-field">
                                <i class="material-icons prefix">work</i>
                                <select name="userRole" id="user_role">
                                    <option value="ROLE_CUSTOMER">Customer</option>
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                        <option value="ROLE_ADMIN">Administrator</option>
                                        <option value="ROLE_CSR">CSR</option>
                                        <option value="ROLE_PMG">PMG</option>
                                    </sec:authorize>
                                </select>
                                <label for="user_role">User Role</label>
                            </div>
                            <div class="input-field" style="margin-top: 34px;">
                                <i class="material-icons prefix">email</i>
                                <input id="user_email" name="email" type="email" class="validate">
                                <label for="user_email">E-mail</label>
                            </div>
                            <div class="input-field">
                                <i class="material-icons prefix">phone</i>
                                <input id="user_phone" name="phone" type="tel" class="validate">
                                <label for="user_phone">Phone</label>
                            </div>
                            <p class="customer-field" style="margin-top: 15px;">
                                <input type="checkbox" class="filled-in" id="customer_contact_person"
                                       name="contactPerson"
                                       checked="unchecked"/>
                                <label for="customer_contact_person">Contact Person</label>
                            </p>
                            <div class="input-field customer-field" style="margin-top: 52px;">
                                <i class="material-icons prefix">business</i>
                                <input type="text" id="customer_organization_name" name="organizationName"
                                       class="autocomplete">
                                <label for="customer_organization_name">Organization</label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s6">
                            <input type="hidden" name="addressRegionName" id="customer_region_name">
                            <input type="hidden" name="addressLatitude" id="customer_address_lat">
                            <input type="hidden" name="addressLongitude" id="customer_address_long">
                            <button id="submit-user-create" class="btn waves-effect waves-light" type="submit"
                                    name="action">Create Customer
                                <i class="material-icons right">send</i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
        <div id="swipe-product-form" class="col s12">
            <div class="row">
                <form class="col s12" id="addProduct">
                    <div class="row">
                        <div class='input-field col s6'>
                            <i class="material-icons prefix">title</i>
                            <label for="title">Title</label>
                            <input class="validate" id="title" type="text" name="title">
                        </div>
                        <div class="input-field col s3">
                            <i class="material-icons prefix">add_shopping_cart</i>
                            <select name="discountId" id="select_disc">
                                <option value="0">Default</option>
                            </select>
                            <label for="select_disc">Choose discount</label>
                        </div>
                        <div class="input-field col s3">
                            <i class="material-icons prefix">search</i>
                            <input class='validate' type='text' onkeyup="fetchDiscounts()" id='search_title'/>
                            <label for="search_title">Search discount <span id="discount_numbers"></span></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class='input-field col s6'>
                            <i class="material-icons prefix">euro_symbol</i>
                            <input class='validate' type='number' name='defaultPrice' id='price'/>
                            <label for="price">Price</label>
                        </div>
                        <div class="input-field col s3">
                            <i class="material-icons prefix">add</i>
                            <select name="groupId" id="select_group">
                                <option value="0">Default</option>
                            </select>
                            <label for="select_group">Choose group</label>
                        </div>
                        <div class="input-field col s3">
                            <i class="material-icons prefix">search</i>
                            <input class='validate' type='text' onkeyup="fetchGroups()" id='search_group'/>
                            <label for="search_group">Search group <span id="group_numbers"></span></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <i class="material-icons prefix">cached</i>
                            <select name="statusName" id="select_product_status">
                                <option value="ACTUAL">ACTUAL</option>
                                <option value="PLANNED">PLANNED</option>
                            </select>
                            <label for="select_disc">Choose product status</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <i class="material-icons prefix">description</i>
                            <textarea id="descProduct" name="description" class="materialize-textarea"></textarea>
                            <label for="descProduct">Description</label>
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="row">
                        <div class="col s6">
                            <button class="btn waves-effect waves-light" type="submit" name="action">Create Product
                                <i class="material-icons right">send</i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
        <div id="swipe-discount-form" class="col s12">
            <div class="row">
                <form class="col s12" id="addDiscount">
                    <div class="row">
                        <div class='input-field col s6'>
                            <i class="material-icons prefix">title</i>
                            <input class='validate' type='text' name='title' id='disc_title'/>
                            <label for="disc_title">Title</label>
                        </div>
                        <div class="switch">
                            <i class="material-icons prefix">touch_app</i>
                            <label>
                                Off
                                <input name="active" type="checkbox" checked="checked" id="disc_active">
                                <span class="lever"></span>
                                On
                            </label>
                        </div>
                    </div>
                    <div class="row">
                        <div class='input-field col s6'>
                            <i class="material-icons prefix">call_received</i>
                            <input class='validate' type='number' name='percentage' id='disc_percentage'/>
                            <label for="disc_percentage">Percentage</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <i class="material-icons prefix">description</i>
                            <textarea id="disc_description" name="description" class="materialize-textarea"></textarea>
                            <label for="disc_description">Description</label>
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="row">
                        <div class="col s6">
                            <button class="btn waves-effect waves-light" type="submit" name="action">Create Discount
                                <i class="material-icons right">send</i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
        <div id="swipe-group-form" class="col s12">
            <div class="row">
                <form class="col s12" id="addGroup">
                    <div class="row">
                        <div class='input-field col s6'>
                            <i class="material-icons prefix">short_text</i>
                            <input class='validate' type='text' name='name' id='group_name'/>
                            <label for="group_name">Name</label>
                        </div>
                        <div class="input-field col s3">
                            <i class="material-icons prefix">add_shopping_cart</i>
                            <select name="discountId" id="select_group_disc">
                                <option value="0">Default</option>
                            </select>
                            <label for="select_group_disc">Choose discount</label>
                        </div>
                        <div class="input-field col s3">
                            <i class="material-icons prefix">search</i>
                            <input class='validate' type='text' onkeyup="fetchGroupDiscounts()"
                                   id='search_for_group_title'/>
                            <label for="search_for_group_title">Search discount <span
                                    id="group_discount_numbers"></span></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class='input-field col s6'>
                            <i class="material-icons prefix">done_all</i>
                            <select multiple="multiple" id="products_without_group" name="products">
                                <option value="" disabled selected>Choose products</option>
                            </select>
                            <label>Products without group</label>
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="row">
                        <div class="col s6">
                            <button class="btn waves-effect waves-light" type="submit" name="action">Create Group
                                <i class="material-icons right">send</i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </sec:authorize>
</div>
<script>
    $(document).ready(function () {
        $('ul.tabs').tabs();
        $('select').material_select();
        $('#user_role option[value="ROLE_CUSTOMER"]').attr("selected", true);

        var organizationData = {};
        $.get("/organization/all", function (data) {
            data.forEach(function (item) {
                organizationData[item.name] = null;
            });

            $('#customer_organization_name').autocomplete({
                data: organizationData,
                limit: 5, // The max amount of results that can be shown at once. Default: Infinity.
                onAutocomplete: function (val) {
                    $('#customer_organization_name').val(val);
                }
            });
        });
    });
</script>
<script>
    $('#map').locationpicker({
        location: {
            latitude: 40.7324319,
            longitude: -73.82480777777776
        },
        locationName: "",
        radius: 1,
        inputBinding: {
            locationNameInput: $('#customer_address')
        },
        enableAutocomplete: true,
        enableReverseGeocode: true,
        draggable: true,
        onchanged: function (currentLocation, radius, isMarkerDropped) {
            var mapContext = $(this).locationpicker('map');
            $('#customer_region_name').val(mapContext.location.addressComponents.stateOrProvince);
            $('#customer_address_lat').val(mapContext.location.latitude);
            $('#customer_address_long').val(mapContext.location.longitude);
        },
        addressFormat: 'street_number'
    });
</script>


<script>
    function fetchDiscounts() {
        var title = $('#search_title').val();
        if (title.length > 1) {
            $('#select_disc').children().remove();
            $.get("/csr/discountByTitle/" + title).success(function (data) {
                $('#select_disc').append($('<option value="0">Default</option>'));
                $.each(data, function (i, item) {
                    $('#select_disc').append($('<option/>', {
                        value: item.id,
                        text: item.title + ' - ' + item.percentage + '%'
                    }));
                });
                $('#select_disc').material_select('updating');
                $('#discount_numbers').html(", results " + data.length);
            });
        }
    }

    function fetchGroupDiscounts() {
        var title = $('#search_for_group_title').val();
        if (title.length > 1) {
            $('#select_group_disc').children().remove();
            $.get("/csr/discountByTitle/" + title).success(function (data) {
                $('#select_group_disc').append($('<option value="0">Default</option>'));
                $.each(data, function (i, item) {
                    $('#select_group_disc').append($('<option/>', {
                        value: item.id,
                        text: item.title + ' - ' + item.percentage + '%'
                    }));
                });
                $('#select_group_disc').material_select('updating');
                $('#group_discount_numbers').html(", results " + data.length);
            });
        }
    }

    function fetchGroups() {
        var title = $('#search_group').val();
        if (title.length > 1) {
            $('#select_group').children().remove();
            $.get("/csr/groupByName/" + title).success(function (data) {
                $('#select_group').append($('<option value="0">Default</option>'));
                $.each(data, function (i, item) {
                    console.log(item);
                    $('#select_group').append($('<option/>', {
                        value: item.id,
                        text: item.name
                    }));
                });
                $('#select_group').material_select('updating');
                $('#group_numbers').html(", results " + data.length);
            });
        }
    }


    $(document).ready(function () {
        $('select').material_select();

//      load products without group
        loadProductsWithoutGroup();
        function loadProductsWithoutGroup() {
            $.get("/csr/load/productWithoutGroup").success(function (data) {
                $('#products_without_group').children().remove();
                $('#products_without_group').append('<option value="" disabled selected>Choose products</option>')
                $.each(data, function (i, item) {
                    $('#products_without_group').append($('<option/>', {
                        value: item.id,
                        text: item.title + ' - ' + item.statusName
                    }));
                });
                $('#products_without_group').material_select('updating');
            });
        }

//        create product

        $("#addProduct").on("submit", function (e) {
            e.preventDefault();
            var title = $('#title').val();
            var price = $('#price').val();
            if (title.length < 5) {
                Materialize.toast("Please enter title at least 5 characters", 10000, 'rounded');
            } else if (price < 1) {
                Materialize.toast("Please enter price more 0", 10000, 'rounded');
            } else {
                $.post("/csr/addProduct", $("#addProduct").serialize(), function (data) {
                    $("#addProduct")[0].reset();
                    Materialize.toast(data, 10000, 'rounded');
                });
                loadProductsWithoutGroup();
            }
        });

//      create discount
        $("#addDiscount").on("submit", function (e) {
                e.preventDefault();
                var title = $('#disc_title').val();
                var percentage = $('#disc_percentage').val();
                if (title.length < 5) {
                    Materialize.toast("Please enter title at least 5 characters", 10000, 'rounded');
                } else if (percentage < 0 || percentage > 100) {
                    Materialize.toast("Please enter percentage more 0 and less 100", 10000, 'rounded');
                } else {
                    $.post("/csr/addDiscount", $("#addDiscount").serialize(), function (data) {
                        $("#addDiscount")[0].reset();
                        Materialize.toast(data, 10000, 'rounded');
                    });
                }
            }
        );


//        create group
        $("#addGroup").on("submit", function (e) {
            e.preventDefault();
            var grpName = $('#group_name').val();

            if (grpName.length < 5) {
                Materialize.toast("Please enter group name at least 5 characters", 10000, 'rounded');
            } else {
                $.post("/csr/addGroup", $("#addGroup").serialize(), function (data) {
                    $("#addGroup")[0].reset();
                    Materialize.toast(data, 10000, 'rounded');
                    loadProductsWithoutGroup();
                });
            }
        });
    });
</script>
