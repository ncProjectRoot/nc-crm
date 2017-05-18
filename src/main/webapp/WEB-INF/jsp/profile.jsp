<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url value="/img/discount.png" var="discountUrl"/>
<style>

    .content-body {
        position: relative;
    }

    .container {
        padding-top: 50px;
        padding-bottom: 100px;
    }

    .container selector {
        position: relative;
    }

    h4, h5, h6 {
        text-align: center;
        word-wrap: break-word;
    }

    .change-trigger {
        position: absolute;
        margin: 20px;
    }

    .modal.modal-fixed-footer {
        max-height: 85%;
        height: 98%;
    }

    .row .col {
        float: none;
        margin: 30px auto;
    }

    .modal .modal-footer .btn {
        float: none;
    }

    .modal-content h4 {
        margin-top: 20px;
    }

</style>
<div class="content-body z-depth-1" data-page-name="Profile">
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR', 'ROLE_PMG', 'ROLE_CUSTOMER')">
        <a class="modal-trigger brown-text change-trigger" href="#update"><i class='material-icons medium'>settings</i></a>
        <div id="update" class="modal modal-fixed-footer">
            <form id="update-user-form">
                <div class="modal-content row">
                    <h4>Update user</h4>
                    <input type="hidden" name="id" value="${profile.id}"/>
                    <input type="hidden" name="password" value="${profile.password}"/>
                    <input type="hidden" name="email" value="${profile.email}"/>
                    <input type="hidden" name="enable" value="${profile.enable}"/>
                    <input type="hidden" name="accountNonLocked" value="${profile.accountNonLocked}"/>
                    <input type="hidden" name="contactPerson" value="${profile.contactPerson}"/>
                    <input type="hidden" name="userRole" value="${profile.userRole}"/>

                    <input type="hidden" name="orgId" value="${profile.organization.id}"/>
                    <input type="hidden" name="addressId" value="${profile.address.id}"/>

                    <input type="hidden" name="organizationName" value="${profile.organization.name}"/>
                    <input type="hidden" name="addressRegionName" id="customer_region_name"
                           value="${profile.address.region}">
                    <input type="hidden" name="addressLatitude" id="customer_address_lat"
                           value="${profile.address.latitude}">
                    <input type="hidden" name="addressLongitude" id="customer_address_long"
                           value="${profile.address.longitude}">
                    <input type="hidden" name="formattedAddress" id="customer_formatted_address"
                           value="${profile.address.formattedAddress}">
                    <input type="hidden" name="addressDetails" value="${profile.address.details}"/>
                    <div class='input-field col s7'>
                        <i class="material-icons prefix">title</i>
                        <input class='validate' type='text' name='lastName' id='user_lname'
                               value="${profile.lastName}"/>
                        <label for="user_lname" class="active">Last name</label>
                    </div>
                    <div class='input-field col s7'>
                        <i class="material-icons prefix">title</i>
                        <input class='validate' type='text' name='middleName' id='user_mname'
                               value="${profile.middleName}"/>
                        <label for="user_mname" class="active">Middle name</label>
                    </div>
                    <div class='input-field col s7'>
                        <i class="material-icons prefix">title</i>
                        <input class='validate' type='text' name='firstName' id='user_fname'
                               value="${profile.firstName}"/>
                        <label for="user_fname" class="active">First name</label>
                    </div>
                    <div class='input-field col s7'>
                        <i class="material-icons prefix">phone</i>
                        <input class='validate' type='text' name='phone' id='user_phone'
                               value="${profile.phone}"/>
                        <label for="user_phone" class="active">Phone</label>
                    </div>
                </div>
                <div class="modal-footer center-align">
                    <button class="btn waves-effect waves-light" id="submit-update-user" type="submit" name="action">
                        Update
                        <i class="material-icons right">send</i>
                    </button>
                </div>
            </form>
        </div>
    </sec:authorize>
    <div class="container">
        <h4 class="name field">${profile.lastName} ${profile.middleName} ${profile.firstName}</h4>
        <div class="divider"></div>
        <div class="section">
            <div class="div-work field">
                <h5 class="price">${profile.organization.name} (${profile.userRole.formattedName})</h5>
            </div>
            <div class="div-phone field">
                <h5 class="price">${profile.phone}</h5>
            </div>
            <div class="div-email field">
                <h5 class="price">${profile.email}</h5>
            </div>
        </div>
        <div class="section">
            <div class="divider"></div>
            <div class="section">
                <div class="customer-field" id="map" style="width: auto; height: 270px;"></div>
            </div>
        </div>
    </div>
</div>
<script>

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR', 'ROLE_PMG', 'ROLE_CUSTOMER')">

    if (document.getElementById('customer_address_lat').value && document.getElementById('customer_address_long').value) {
        $('#map').locationpicker({
            location: {
                latitude: document.getElementById('customer_address_lat').value,
                longitude: document.getElementById('customer_address_long').value
            },
            radius: 1,
            enableAutocomplete: true,
            enableReverseGeocode: true,
            draggable: false,
        });
    } else {
        map.style.visibility = 'hidden';
    }

    $('.modal').modal({
            opacity: .5, // Opacity of modal background
            endingTop: '8%' // Starting top style attribute
        }
    );

    $('.materialize-textarea').trigger('autoresize');
    Materialize.updateTextFields();

    $("#update-user-form").on("submit", function (e) {
        e.preventDefault();
        send("#update-user-form", "/users", 'PUT').done(function () {
            $('.modal').modal('close');
            $(window).trigger('hashchange')
        })
    });

    </sec:authorize>

</script>