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

    .avatar {
        display: none;
        border-radius: 50%;
        width: 150px;
    }

</style>
<div class="content-body z-depth-1" data-page-name="Profile">
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR', 'ROLE_PMG', 'ROLE_CUSTOMER')">
        <a class="modal-trigger brown-text change-trigger" href="#update"><i class='material-icons medium'>settings</i></a>
        <div id="update" class="modal modal-fixed-footer">
            <form id="update-user-form">
                <div class="modal-content row">
                    <h4>Update user</h4>
                    <input type="hidden" id="user-id" name="id" value="${profile.id}"/>
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
        <div><img id="user-avatar" class="avatar" src=""/></div>
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

    $.get('/users/' + $('#user-id').val() + '/avatar', function (data) {
        console.log('/users/' + ${user.id} +'/avatar')
    }).done(function (data) {
        $("#user-avatar").attr('src', data);
        $("#user-avatar").css('display', 'block');
    }).fail(function () {
        $("#user-avatar").attr('src', 'data:image/png;base64,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');
        $("#user-avatar").css('display', 'block');
    });

</script>