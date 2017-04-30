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
            <form class="col s12">
                <div class="row">
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="admin_first_name" type="text" class="validate">
                            <label for="admin_first_name">First Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="admin_middle_name" type="text" class="validate">
                            <label for="admin_middle_name">Middle Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="admin_last_name" type="text" class="validate">
                            <label for="admin_last_name">Last Name</label>
                        </div>
                    </div>
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">email</i>
                            <input id="admin_email" type="email" class="validate">
                            <label for="admin_email">E-mail</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">phone</i>
                            <input id="admin_phone" type="tel" class="validate">
                            <label for="admin_phone">Phone</label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col s6">
                        <button class="btn waves-effect waves-light" type="submit" name="action">Create Admin
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div id="swipe-customer-form" class="col s12">
        <div class="row">
            <form class="col s12">
                <div class="row">
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="customer_first_name" type="text" class="validate">
                            <label for="customer_first_name">First Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="customer_middle_name" type="text" class="validate">
                            <label for="customer_middle_name">Middle Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="customer_last_name" type="text" class="validate">
                            <label for="customer_last_name">Last Name</label>
                        </div>
                        <div class="input-field ">
                            <i class="material-icons prefix">open_with</i>
                            <input id="customer_address_details" type="text" class="validate">
                            <label for="customer_address_details">Address Details</label>
                        </div>
                        <div>
                            <span>Choose Address</span>
                            <div id="map"></div>
                        </div>
                        <div class="input-field">
                            <input type="checkbox" class="filled-in" id="customer_contact_person" checked="checked"/>
                            <label for="customer_contact_person">Contact Person</label>
                        </div>
                    </div>
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">email</i>
                            <input id="customer_email" type="email" class="validate">
                            <label for="customer_email">E-mail</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">phone</i>
                            <input id="customer_phone" type="tel" class="validate">
                            <label for="customer_phone">Phone</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">business</i>
                            <input type="text" id="customer_organization" class="autocomplete">
                            <label for="customer_organization">Organization</label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col s6">
                        <button class="btn waves-effect waves-light" type="submit" name="action">Create Customer
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div id="swipe-csr-form" class="col s12">
        <div class="row">
            <form class="col s12">
                <div class="row">
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="csr_first_name" type="text" class="validate">
                            <label for="csr_first_name">First Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="csr_middle_name" type="text" class="validate">
                            <label for="csr_middle_name">Middle Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="csr_last_name" type="text" class="validate">
                            <label for="csr_last_name">Last Name</label>
                        </div>
                    </div>
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">email</i>
                            <input id="csr_email" type="email" class="validate">
                            <label for="csr_email">E-mail</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">phone</i>
                            <input id="csr_phone" type="tel" class="validate">
                            <label for="csr_phone">Phone</label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col s6">
                        <button class="btn waves-effect waves-light" type="submit" name="action">Create CSR
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div id="swipe-pmg-form" class="col s12">
        <div class="row">
            <form class="col s12">
                <div class="row">
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="pmg_first_name" type="text" class="validate">
                            <label for="pmg_first_name">First Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="pmg_middle_name" type="text" class="validate">
                            <label for="pmg_middle_name">Middle Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="pmg_last_name" type="text" class="validate">
                            <label for="pmg_last_name">Last Name</label>
                        </div>
                    </div>
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">email</i>
                            <input id="pmg_email" type="email" class="validate">
                            <label for="pmg_email">E-mail</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">phone</i>
                            <input id="pmg_phone" type="tel" class="validate">
                            <label for="pmg_phone">Phone</label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col s6">
                        <button class="btn waves-effect waves-light" type="submit" name="action">Create PMG
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

        $('#customer_organization').autocomplete({
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
                // Callback function when value is autcompleted.
            },
            minLength: 1, // The minimum length of the input for the autocomplete to start. Default: 1.
        });
    });

    console.log("createUser");
</script>
<script>
    function initMap() {
        var lat = 50.431622;
        var lng = 30.516645;
        var map_center = new google.maps.LatLng(lat, lng);
        var mapOptions = {
            center: map_center,
            zoom: 16,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var mapCanvas = document.getElementById("map");
        var map = new google.maps.Map(mapCanvas, mapOptions);
        new google.maps.Marker({
            map: map,
            draggable: false,
            position: new google.maps.LatLng(lat, lng)
        });
        google.maps.event.addDomListener(window, 'resize', function () {
            map.setCenter(map_center);
        });
    }
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCT7tBQN8l0fcDdcZUwuxD0XGjgM7qbTL4&callback=initMap"
        async defer></script>
<%--Google API Key: AIzaSyCT7tBQN8l0fcDdcZUwuxD0XGjgM7qbTL4 --%>
