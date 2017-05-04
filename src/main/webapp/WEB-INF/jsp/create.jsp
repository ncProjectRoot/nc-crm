<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .content-body-wrapper {
        height: 800px;
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
        <li class="tab col s3"><a href="#swipe-admin-form">Admin</a></li>
        <li class="tab col s3"><a href="#swipe-customer-form">Customer</a></li>
        <li class="tab col s3"><a href="#swipe-csr-form" class="active">CSR</a></li>
        <li class="tab col s3"><a href="#swipe-pmg-form">PMG</a></li>
    </ul>
    <div id="swipe-admin-form" class="col s12">
        <div class="row">
            <form id="form-admin-create" class="col s12">
                <div class="row">
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="admin_first_name" name="firstName" type="text" class="validate">
                            <label for="admin_first_name">First Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="admin_middle_name" name="middleName" type="text" class="validate">
                            <label for="admin_middle_name">Middle Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="admin_last_name" name="lastName" type="text" class="validate">
                            <label for="admin_last_name">Last Name</label>
                        </div>
                    </div>
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">email</i>
                            <input id="admin_email" name="email" type="email" class="validate">
                            <label for="admin_email">E-mail</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">phone</i>
                            <input id="admin_phone" name="phone" type="tel" class="validate">
                            <label for="admin_phone">Phone</label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col s6">
                        <input type="hidden" name="userRole" value="ROLE_ADMIN"/>
                        <button id="submit-admin-create" class="btn waves-effect waves-light" type="submit"
                                name="action">Create Admin
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div id="swipe-customer-form" class="col s12">
        <div class="row">
            <form id="form-customer-create" class="col s12">
                <div class="row">
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="customer_first_name" name="firstName" type="text" class="validate">
                            <label for="customer_first_name">First Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="customer_middle_name" name="middleName" type="text" class="validate">
                            <label for="customer_middle_name">Middle Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="customer_last_name" name="lastName" type="text" class="validate">
                            <label for="customer_last_name">Last Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">location_on</i>
                            <input type="text" id="customer_address"/>
                            <label for="customer_address">Address</label>
                        </div>
                        <div class="input-field ">
                            <i class="material-icons prefix">open_with</i>
                            <input id="customer_address_details" name="addressDetails" type="text" class="validate">
                            <label for="customer_address_details">Address Details</label>
                        </div>
                        <div>
                            <div id="map" style="width: auto; height: 270px;"></div>
                        </div>
                    </div>
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">email</i>
                            <input id="customer_email" name="email" type="email" class="validate">
                            <label for="customer_email">E-mail</label>
                        </div>
                        <div class="row">
                            <div class="input-field col s9" style="margin-left: -10px;">
                                <i class="material-icons prefix">phone</i>
                                <input id="customer_phone" name="phone" type="tel" class="validate">
                                <label for="customer_phone">Phone</label>
                            </div>
                            <div class="col s3">
                                <p>
                                    <input type="checkbox" class="filled-in" id="customer_contact_person" name="contactPerson"
                                           checked="checked"/>
                                    <label for="customer_contact_person">Contact Person</label>
                                </p>
                            </div>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">business</i>
                            <input type="text" id="customer_organization_name" name="organizationName" class="autocomplete">
                            <label for="customer_organization_name">Organization</label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col s6">
                        <input type="hidden" name="userRole" value="ROLE_CUSTOMER"/>
                        <input type="hidden" name="addressRegionName" id="customer_region_name">
                        <input type="hidden" name="addressLatitude" id="customer_address_lat">
                        <input type="hidden" name="addressLongitude" id="customer_address_long">
                        <button id="submit-customer-create" class="btn waves-effect waves-light" type="submit"
                                name="action">Create Customer
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div id="swipe-csr-form" class="col s12">
        <div class="row">
            <form id="form-csr-create" class="col s12">
                <div class="row">
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="csr_first_name" name="firstName" type="text" class="validate">
                            <label for="csr_first_name">First Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="csr_middle_name" name="middleName" type="text" class="validate">
                            <label for="csr_middle_name">Middle Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="csr_last_name" name="lastName" type="text" class="validate">
                            <label for="csr_last_name">Last Name</label>
                        </div>
                    </div>
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">email</i>
                            <input id="csr_email" name="email" type="email" class="validate">
                            <label for="csr_email">E-mail</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">phone</i>
                            <input id="csr_phone" name="phone" type="tel" class="validate">
                            <label for="csr_phone">Phone</label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col s6">
                        <input type="hidden" name="userRole" value="ROLE_CSR"/>
                        <button id="submit-сsr-create" class="btn waves-effect waves-light" type="submit" name="action">
                            Create CSR
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div id="swipe-pmg-form" class="col s12">
        <div class="row">
            <form id="form-pmg-create" class="col s12">
                <div class="row">
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="pmg_first_name" name="firstName" type="text" class="validate">
                            <label for="pmg_first_name">First Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="pmg_middle_name" name="middleName" type="text" class="validate">
                            <label for="pmg_middle_name">Middle Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="pmg_last_name" name="lastName" type="text" class="validate">
                            <label for="pmg_last_name">Last Name</label>
                        </div>
                    </div>
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">email</i>
                            <input id="pmg_email" name="email" type="email" class="validate">
                            <label for="pmg_email">E-mail</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">phone</i>
                            <input id="pmg_phone" name="phone" type="tel" class="validate">
                            <label for="pmg_phone">Phone</label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col s6">
                        <input type="hidden" name="userRole" value="ROLE_PMG"/>
                        <button id="submit-pmg-create" class="btn waves-effect waves-light" type="submit" name="action">
                            Create PMG
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('ul.tabs').tabs();

        $('#customer_organization_name').autocomplete({
            data: {
                "Acer": null,
                "Microsoft": null,
                "Apple": null,
                "Nokia": null,
                "AKG": null,
                "Google": null
            },
            limit: 5, // The max amount of results that can be shown at once. Default: Infinity.
            onAutocomplete: function (val) {
                $('#customer_organization_name').val(val);
            },
            minLength: 1 // The minimum length of the input for the autocomplete to start. Default: 1.
        });
    });

    console.log("createUser");
</script>
<script>
    $('#map').locationpicker({
        location: {
            latitude: 40.7324319,
            longitude: -73.82480777777776
        },
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
