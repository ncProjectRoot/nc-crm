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
<div class="content-body z-depth-1" data-page-name="User #${user.id}">
    <%--<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">--%>
        <%--<a class="modal-trigger brown-text change-trigger" href="#update"><i class='material-icons medium'>settings</i></a>--%>
        <%--<div id="update" class="modal modal-fixed-footer">--%>
            <%--<form id="update-user-form">--%>
                <%--<div class="modal-content row">--%>
                    <%--<h4>Update user</h4>--%>
                    <%--<input type="hidden" name="id" value="${user.id}"/>--%>
                    <%--<input type="hidden" name="password" value="${user.password}"/>--%>
                    <%--<input type="hidden" name="email" value="${user.email}"/>--%>
                    <%--<input type="hidden" name="phone" value="${user.phone}"/>--%>
                    <%--<input type="hidden" name="enable" value="${user.enable}"/>--%>
                    <%--<input type="hidden" name="accountNonLocked" value="${user.accountNonLocked}"/>--%>
                    <%--<input type="hidden" name="address" value="${user.address}"/>--%>
                    <%--<input type="hidden" name="contactPerson" value="${user.contactPerson}"/>--%>
                    <%--<input type="hidden" name="userRole" value="${user.userRole}"/>--%>
                    <%--<input type="hidden" name="organization" value="${user.organization}"/>--%>

                    <%--<input type="hidden" name="organizationName"/>--%>
                    <%--<input type="hidden" name="addressLatitude"/>--%>
                    <%--<input type="hidden" name="addressLongitude"/>--%>
                    <%--<input type="hidden" name="formattedAddress"/>--%>
                    <%--<input type="hidden" name="addressDetails"/>--%>
                    <%--<input type="hidden" name="addressRegionName"/>--%>
                    <%--<div class='input-field col s7'>--%>
                        <%--<i class="material-icons prefix">title</i>--%>
                        <%--<input class='validate' type='text' name='lastName' id='user_lname'--%>
                               <%--value="${user.lastName}"/>--%>
                        <%--<label for="user_lname" class="active">Last name</label>--%>
                    <%--</div>--%>
                    <%--<div class='input-field col s7'>--%>
                        <%--<i class="material-icons prefix">title</i>--%>
                        <%--<input class='validate' type='text' name='middleName' id='user_mname'--%>
                               <%--value="${user.middleName}"/>--%>
                        <%--<label for="user_mname" class="active">Middle name</label>--%>
                    <%--</div>--%>
                    <%--<div class='input-field col s7'>--%>
                        <%--<i class="material-icons prefix">title</i>--%>
                        <%--<input class='validate' type='text' name='firstName' id='user_fname'--%>
                               <%--value="${user.firstName}"/>--%>
                        <%--<label for="user_fname" class="active">First name</label>--%>
                    <%--</div>--%>
                    <%--<div class="input-field col s7">--%>
                        <%--<i class="material-icons prefix">call_received</i>--%>
                        <%--<input class='validate' type='number' name='percentage' id='disc_percentage'--%>
                               <%--value="${discount.percentage}"/>--%>
                        <%--<label for="disc_percentage" class="active">Percentage</label>--%>
                    <%--</div>--%>
                    <%--<div class='switch col s7'>--%>
                        <%--<i class="material-icons prefix">touch_app</i>--%>
                        <%--<label>--%>
                            <%--Inactive--%>
                            <%--<input name="active" type="checkbox" id="disc_active"--%>
                                   <%--<c:if test="${discount.active==true}">checked="checked"</c:if>>--%>
                            <%--<span class="lever"></span>--%>
                            <%--Active--%>
                        <%--</label>--%>
                    <%--</div>--%>
                    <%--<div class="input-field col s7">--%>
                        <%--<i class="material-icons prefix">description</i>--%>
                        <%--<textarea id="descProduct" name="description"--%>
                                  <%--class="materialize-textarea">${discount.description}</textarea>--%>
                        <%--<label for="descProduct">Description</label>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="modal-footer center-align">--%>
                    <%--<button class="btn waves-effect waves-light" id="submit-update-user" type="submit" name="action">Update--%>
                        <%--<i class="material-icons right">send</i>--%>
                    <%--</button>--%>
                <%--</div>--%>
            </form>
        </div>
    <%--</sec:authorize>--%>
    <div class="container">
        <h4 class="name field">${user.lastName} ${user.middleName} ${user.firstName}</h4>
        <div class="divider"></div>
        <div class="section">
            <div class="div-phone field">
                <h5 class="price">${user.phone}</h5>
            </div>
            <div class="div-email field">
                <h5 class="price">${user.email}</h5>
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

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">

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

    $('.modal').modal({
            opacity: .5, // Opacity of modal background
            endingTop: '8%' // Starting top style attribute
        }
    );

    $('.materialize-textarea').trigger('autoresize');
    Materialize.updateTextFields();

    $("#update-user-form").on("submit", function (e) {
        e.preventDefault();
        sendPut("#update-user-form", "/users").done(function () {
            $('.modal').modal('close');
            $(window).trigger('hashchange')
        })
    });

    </sec:authorize>

</script>