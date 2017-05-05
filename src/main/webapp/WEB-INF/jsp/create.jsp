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
        <li class="tab col s3"><a href="#swipe-user-form">User</a></li>
    </ul>
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
                                <option value="" disabled selected>Choose your option</option>
                                <option value="ROLE_ADMIN">Administrator</option>
                                <option value="ROLE_CUSTOMER">Customer</option>
                                <option value="ROLE_CSR">CSR</option>
                                <option value="ROLE_PMG">PMG</option>
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
                                name="action" style="display: none">Create Customer
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
<script>
    $(document).ready(function () {
        $('ul.tabs').tabs();
        $('select').material_select();
        $('.customer-field').css('display', 'none');

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
