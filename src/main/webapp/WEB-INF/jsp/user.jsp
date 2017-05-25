<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>

    .content-body {
        position: relative;
    }

    .container {
        padding-top: 100px;
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

    .row .map-wrapper.col {
        float: none;
        margin: 30px auto;
        clear: both;
    }

    .a-settings {
        position: absolute;
        margin: 20px;
        right: 0;
    }

    .modal .modal-footer .btn {
        float: none;
    }

    .modal-content h4 {
        margin-top: 20px;
    }

    .avatar {
        /*display: none;*/
        border-radius: 50%;
        width: 150px;
    }

</style>
<div class="content-body z-depth-1" data-page-name="User #${user.id}" data-latitude="${user.address.latitude}" data-longitude="${user.address.longitude}">
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
        <a class="modal-trigger teal-text text-darken-3 change-trigger" href="#update"><i class='material-icons medium'>cached</i></a>
        <div id="update" class="modal modal-fixed-footer">
            <form id="update-user-form">
                <div class="modal-content row">
                    <h4>Update user</h4>
                    <input id="user-id" type="hidden" name="id" value="${user.id}"/>
                    <input type="hidden" name="email" value="${user.email}"/>
                    <input type="hidden" name="accountNonLocked" value="${user.accountNonLocked}"/>
                    <input type="hidden" name="contactPerson" value="${user.contactPerson}"/>
                    <input type="hidden" name="userRole" value="${user.userRole}"/>

                    <input type="hidden" name="orgId" value="${user.organization.id}"/>
                    <input type="hidden" name="addressId" value="${user.address.id}"/>

                    <input type="hidden" name="organizationName" value="${user.organization.name}"/>
                    <input type="hidden" name="addressRegionName" id="customer_region_name"
                           value="${user.address.region}">
                    <input type="hidden" name="addressLatitude" id="customer_address_lat"
                           value="${user.address.latitude}">
                    <input type="hidden" name="addressLongitude" id="customer_address_long"
                           value="${user.address.longitude}">
                    <input type="hidden" name="formattedAddress" id="customer_formatted_address"
                           value="${user.address.formattedAddress}">
                    <input type="hidden" name="addressDetails" value="${user.address.details}"/>
                    <div class='input-field col s12 m6'>
                        <i class="material-icons prefix">title</i>
                        <input class='validate' type='text' name='lastName' id='user_lname'
                               value="${user.lastName}"/>
                        <label for="user_lname" class="active">Last name</label>
                    </div>
                    <div class='input-field col s12 m6'>
                        <i class="material-icons prefix">title</i>
                        <input class='validate' type='text' name='middleName' id='user_mname'
                               value="${user.middleName}"/>
                        <label for="user_mname" class="active">Middle name</label>
                    </div>
                    <div class='input-field col s12 m6'>
                        <i class="material-icons prefix">title</i>
                        <input class='validate' type='text' name='firstName' id='user_fname'
                               value="${user.firstName}"/>
                        <label for="user_fname" class="active">First name</label>
                    </div>
                    <div class='input-field col s12 m6'>
                        <i class="material-icons prefix">phone</i>
                        <input class='validate' type='text' name='phone' id='user_phone'
                               value="${user.phone}"/>
                        <label for="user_phone" class="active">Phone</label>
                    </div>
                    <input type="hidden" name="accountNonLocked" >
                    <div class="switch col s12">
                        <label>
                            Disactivate
                            <input type="checkbox" name="enable" id="enable-input"
                            <c:if test="${user.enable}">
                                   checked
                            </c:if>
                            >
                            <span class="lever"></span>
                            Activate
                        </label>
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
    <c:if test="${isProfile}">
        <a class="teal-text text-darken-3 a-settings" href="#settings"><i class='material-icons medium'>settings</i></a>
    </c:if>
    <div class="container row">
        <div class="col s6 m3"><img id="user-avatar" class="avatar" src="${avatar}" style="width: 100%;"/></div>
        <div class="col s12 m9">
            <h4 class="name field">${user.lastName} ${user.middleName} ${user.firstName}</h4>
            <div class="divider"></div>
            <div class="section">
                <div class="div-work field">
                    <h5 class="price">${user.organization.name} (${user.userRole.formattedName})</h5>
                </div>
                <div class="div-phone field">
                    <h5 class="price">Phone: ${user.phone}</h5>
                </div>
                <div class="div-email field">
                    <h5 class="price">Email: ${user.email}</h5>
                </div>
            </div>
        </div>
        <div class="section col s10 m8 map-wrapper">
            <div class="divider"></div>
            <div class="section">
                <div class="customer-field" id="map" style="width: auto; height: 270px;"></div>
            </div>
        </div>
    </div>
</div>
<script>

    $('#map').locationpicker({
        location: {
            latitude: $(".content-body").data("latitude"),
            longitude: $(".content-body").data("longitude")
        },
        radius: 1,
        enableAutocomplete: true,
        enableReverseGeocode: true,
        draggable: false
    });

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR', 'ROLE_PMG')">

    $('.modal').modal({
            opacity: .5, // Opacity of modal background
            endingTop: '8%' // Starting top style attribute
        }
    );

    $('.materialize-textarea').trigger('autoresize');
    Materialize.updateTextFields();

    $("#update-user-form").on("submit", function (e) {
        e.preventDefault();
        $("input[name='accountNonLocked']").val($("input[name='enable']").is(":checked"))
        send("#update-user-form", "/users", 'PUT').done(function () {
            $('.modal').modal('close');
            $(window).trigger('hashchange')
        })
    });

    </sec:authorize>

</script>