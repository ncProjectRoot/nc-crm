<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    /* Always set the map height explicitly to define the size of the div * element that contains the map. */
    #map {
        height: 270px;
    }
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body" data-page-name="Users">
    <div id="content-body" class="row">

        <div class="col s12">
            <ul id="tabs" class="tabs">
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
                    <li class="tab col s3"><a class="active" href="#all-users-wrapper">All Users</a></li>
                    <li class="tab col s3"><a id="link-create-wrapper" href="#create-wrapper">Create</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                    <li class="tab col s3"><a class="active" href="#my-users-wrapper">My Users</a></li>
                </sec:authorize>
            </ul>
        </div>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
            <div id="all-users-wrapper" class="col s12">
                <div id="table-all-users" class="table-container row">
                    <div class="table-wrapper col s11 center-align">
                        <table class="striped responsive-table centered ">
                            <thead>
                            <tr>
                                <th data-field="1">
                                    <a href="#!" class="sorted-element a-dummy">#</a>
                                </th>
                                <th data-field="2">
                                    <a href="#!" class="sorted-element a-dummy">First Name</a>
                                </th>
                                <th data-field="3">
                                    <a href="#!" class="sorted-element a-dummy">Middle Name</a>
                                </th>
                                <th data-field="4">
                                    <a href="#!" class="sorted-element a-dummy">Last Name</a>
                                </th>
                                <th data-field="5">
                                    <a href="#!" class="sorted-element a-dummy">E-mail</a>
                                </th>
                                <th data-field="6">
                                    <a href="#!" class="sorted-element a-dummy">Phone</a>
                                </th>
                                <th class="th-dropdown" data-field="roleId">
                                    <a class='dropdown-button a-dummy' href='#'
                                       data-activates='dropdown-all-user-role' data-default-name="User Role">
                                        User Role
                                    </a>
                                    <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                    <ul id="dropdown-all-user-role" class='dropdown-content'>
                                        <li><a href="#" class="a-dummy" data-value="1">Admin</a></li>
                                        <li><a href="#" class="a-dummy" data-value="2">Customer</a></li>
                                        <li><a href="#" class="a-dummy" data-value="3">CSR</a></li>
                                        <li><a href="#" class="a-dummy" data-value="4">PMG</a></li>
                                    </ul>
                                </th>
                                <th class="th-dropdown" data-field="contactPerson">
                                    <a class='dropdown-button a-dummy' href='#'
                                       data-activates='dropdown-all-contact-person' data-default-name="Contact Person">
                                        Contact Person
                                    </a>
                                    <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                    <ul id="dropdown-all-contact-person" class='dropdown-content'>
                                        <li><a href="#" class="a-dummy" data-value="true">Yes</a></li>
                                        <li><a href="#" class="a-dummy" data-value="false">No</a></li>
                                    </ul>
                                </th>
                                <th data-field="9">
                                    <a href="#!" class="sorted-element a-dummy">Address</a>
                                </th>
                                <th data-field="10">
                                    <a href="#!" class="sorted-element a-dummy">Organization</a>
                                </th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div id="create-wrapper" class="col s12">
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
                                    <input id="customer_address_details" name="addressDetails" type="text"
                                           class="validate">
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
                                <input type="hidden" name="formattedAddress" id="customer_formatted_address">
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
        <sec:authorize access="hasRole('ROLE_CUSTOMER')">
            <div id="my-users-wrapper" class="col s12">
                <div id="table-my-users" class="table-container row">
                    <div class="table-wrapper col s11 center-align">
                        <table class="striped responsive-table centered ">
                            <thead>
                            <tr>
                                <th data-field="1">
                                    <a href="#!" class="sorted-element a-dummy">#</a>
                                </th>
                                <th data-field="2">
                                    <a href="#!" class="sorted-element a-dummy">First Name</a>
                                </th>
                                <th data-field="3">
                                    <a href="#!" class="sorted-element a-dummy">Middle Name</a>
                                </th>
                                <th data-field="4">
                                    <a href="#!" class="sorted-element a-dummy">Last Name</a>
                                </th>
                                <th data-field="5">
                                    <a href="#!" class="sorted-element a-dummy">E-mail</a>
                                </th>
                                <th data-field="6">
                                    <a href="#!" class="sorted-element a-dummy">Phone</a>
                                </th>
                                <th data-field="9">
                                    <a href="#!" class="sorted-element a-dummy">Address</a>
                                </th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
            </div>
        </sec:authorize>

    </div>
</div>
<%@ include file="/WEB-INF/jsp/component/tableScript.jsp" %>
<script>

    $('ul#tabs').tabs({

        onShow: function() {
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
                    $('#customer_formatted_address').val(mapContext.location.formattedAddress);
                },
                addressFormat: 'street_number'
            });
        }
    });

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
    //////// create ////////

    $('select').material_select();
    $('#user_role option[value="ROLE_CUSTOMER"]').attr("selected", true);

    var organizationData = {};
    $.get("/organizations", function (data) {
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

    $(document).on("change", "#user_role", function () {
        if ($('#user_role option:selected').val() == 'ROLE_CUSTOMER') {
            $('.customer-field').css("display", "block");
        } else {
            $('.customer-field').css("display", "none");
        }
        $('#customer_contact_person').prop('checked', false);
        $('#submit-user-create').css("display", "block");
        $('#submit-user-create').html('Create ' + $('#user_role option:selected').text());
    });

    $('#form-user-create').on("submit", function (e) {
        e.preventDefault();
        var url = "/users/registration";
        var form = "#form-user-create";
        send(form, url, "POST").done(function (id) {
            if (id) {
                location.hash = '#user/' + id;
            }
        })
    });

    //////// all ////////

    $("#table-all-users").karpo_table({
        urlSearch: "/users/autocomplete",
        urlTable: "/users",
        mapper: function (object) {
            var contactPerson = null;
            var tr = $("<tr>");
            tr.append($("<td>").append($("<a>", {
                text: object.id,
                href: "#user/" + object.id
            })));
            tr.append($("<td>", {text: object.firstName}));
            tr.append($("<td>", {text: object.middleName ? object.middleName : ""}));
            tr.append($("<td>", {text: object.lastName}));
            tr.append($("<td>", {text: object.email}));
            tr.append($("<td>", {text: object.phone}));
            tr.append($("<td>", {text: object.userRole}));
            if(object.contactPerson != null)
                contactPerson = (object.contactPerson == true) ? "<i class='material-icons prefix'>check</i>" : "<i class='material-icons prefix'>clear</i>";
            tr.append($("<td>", {html: contactPerson}));
            tr.append($("<td>", {text: object.formattedAddress}));
            tr.append($("<td>", {text: object.organizationName}));
            return tr;
        }
    });

    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_CUSTOMER')">
    $("#table-my-users").karpo_table({
        urlSearch: "/users/autocomplete",
        urlTable: "/users?individual=true",
        mapper: function (object) {
            var tr = $("<tr>");
            tr.append($("<td>").append($("<a>", {
                text: object.id,
                href: "#user?id=" + object.id
            })));
            tr.append($("<td>", {text: object.firstName}));
            tr.append($("<td>", {text: object.middleName ? object.middleName : ""}));
            tr.append($("<td>", {text: object.lastName}));
            tr.append($("<td>", {text: object.email}));
            tr.append($("<td>", {text: object.phone}));
            tr.append($("<td>", {text: object.formattedAddress}));
            return tr;
        }
    });
    </sec:authorize>

</script>
