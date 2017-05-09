<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .content-body-wrapper {
        width: calc(100% - 20px * 2);
        margin: 20px;
        background-color: #fff;
    }

    #button-cancel, #swipe-customer-form{
        display: none;
    }

    #map2{
        width: 1040px;
    }

    #map, #map2 {
        height: 270px;

    }

    #map {
        //width: 785px;
        width: 1040px;
    }

    .little{
        height:80px;
    }

    .left_tab{
        //margin-left: 150px;
    }

    #address_input{
        margin-top: 30px;
    }
</style>


<div class="content-header z-depth-1 valign-wrapper">
    <i class="black-text material-icons">person_pin</i>
    <span>Profile</span>
</div>
<div class="content-body-wrapper z-depth-1" >
    <div id="head-name" class="center-align">
        <h1 id="FIO" class="card-panel hoverable">Іванов Іван Іванович</h1>
    </div>
    <div class="col s12">
        <a id="button-change" onclick="get_edit_form()"   >
            <div class="fixed-action-btn horizontal">
                <div   class="btn-floating btn-large red">
                    <i class="large material-icons">mode_edit</i>
                </div>
            </div>
        </a>
        <a id="button-cancel" onclick="get_info()" >
            <div class="fixed-action-btn horizontal">
                <div   class="btn-floating btn-large red">
                    <i class="large material-icons ">clear</i>
                </div>
            </div>
        </a>
    </div>
    <div id="swipe-admin-form" class="col s12">
        <div class="row" >
            <div class="row little left_tab" >
                <div class="col s3" >
                    <div class="input-field">
                        <i id="user_email" class="material-icons prefix">email</i>
                        <label id="user_email_data" for="user_email">yarusrpog@mail.ru</label>
                    </div>
                </div>
                <div class="col s3">
                    <div class="input-field">
                        <i id="user_phone" class="material-icons prefix">phone</i>
                        <label id="user_phone_data" for="user_phone">0933794982</label>
                    </div>
                </div>
                <div class="col s3">
                    <div class="input-field">
                        <i id="user_phone" class="material-icons prefix">lock</i>
                        <label id="user_phone_data" for="user_phone">Acount unlocked</label>
                    </div>
                </div>
                <div class="col s3">
                    <div class="input-field">
                        <i id="user_phone" class="material-icons prefix">assignment_turned_in</i>
                        <label id="user_phone_data" for="user_phone">Acount activated</label>
                    </div>
                </div>

            </div>
            <div  class="row little left_tab" >
                <div class="col s3">
                    <div class="input-field">
                        <i id="customer_organization" class="material-icons prefix">business</i>
                        <label id="user_organization_data" for="customer_organization">КПІ</label>
                    </div>
                </div>
                <div class="col s3">
                    <div class="input-field ">
                        <i id="cus" class="material-icons prefix">open_with</i>
                        <label id="address_details" for="cus">Квартира 53</label>
                    </div>
                </div>

            </div>
            <div class="row left_tab">
                <div class="col s12 " >
                    <span>Your Address</span>
                    <div id="map" ></div>
                </div>
            </div>

        </div>
    </div>
    <div id="swipe-customer-form" class="col s12">
        <div class="row">
            <form class="col s12">
                <div class="row">
                    <div class="col s6">
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input onclick="$('#customer_fn').css('display', 'block');" id="customer_first_name" type="text" class="validate">
                            <label id="customer_fn" for="customer_first_name">First Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input onclick="$('#customer_mn').css('display', 'block');" id="customer_middle_name" type="text" class="validate">
                            <label id="customer_mn" for="customer_middle_name">Middle Name</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input onclick="$('#customer_ln').css('display', 'block');" id="customer_last_name" type="text" class="validate">
                            <label id="customer_ln" for="customer_last_name">Last Name</label>
                        </div>
                        <div class="input-field">
                            <input type="checkbox" class="filled-in" id="customer_contact_person"
                                   />
                            <label for="customer_contact_person">Contact Person</label>
                        </div>
                        <div class="input-field" id="address_input">
                            <span>Choose Address</span>
                            <div id="map2" ></div>
                        </div>

                    </div>
                    <div class="col s6">

                        <div class="input-field">
                            <i class="material-icons prefix">phone</i>
                            <input onclick="$('#customer_p').css('display', 'block');" id="customer_phone" type="tel" class="validate">
                            <label id="customer_p" for="customer_phone">Phone</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">business</i>
                            <input onclick="$('#customer_od').css('display', 'block');" type="text" id="customer_organization_data" class="autocomplete">
                            <label id="customer_od" for="customer_organization_data">Organization</label>
                        </div>
                        <div class="input-field ">
                            <i class="material-icons prefix">open_with</i>
                            <input onclick="$('#customer_ad').css('display', 'block');" id="customer_address_details" type="text" class="validate">
                            <label id="customer_ad"for="customer_address_details">Address Details</label>
                        </div>
                        <div class="input-field">
                            <input type="checkbox" class="filled-in" id="customer_locked"/>
                            <label for="customer_locked">Locked </label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col s6">
                        <button class="btn waves-effect waves-light" type="submit" name="action">Save data
                            <i class="material-icons right">done</i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script>


    console.log("profile");
</script>
<script>
    var contact_person;
    var lat = 50.431622;
    var lng = 30.516645;

    $(document).ready(function () {
        $.get("/users", function (data) {
            $("#FIO").html(data.firstName + " " + data.middleName + " " + data.lastName);
            $("#customer_first_name").val(data.firstName);
            $("#customer_fn").css("display", "none");
            $("#customer_mn").css("display", "none");
            $("#customer_ln").css("display", "none");
            $("#customer_p").css("display", "none");
            $("#customer_od").css("display", "none");
            $("#customer_ad").css("display", "none");
            $("#customer_middle_name").val(data.middleName);
            $("#customer_last_name").val(data.lastName);
            $("#user_email_data").html(data.email);

            $("#user_phone_data").html(data.phone);
            $("#customer_phone").val(data.phone);
            $("#user_organization_data").html(data.organization.name);
            $("#customer_organization_data").val(data.organization.name);
            $("#address_details").html(data.address.details);
            $("#customer_address_details").val(data.address.details);
            document.getElementById("customer_contact_person").checked = data.contactPerson;
            //lat = data.address.latitude;
            //lng = data.address.longitude;
        });
    });


    function get_edit_form() {
        document.getElementById("head-name").style.display = "none";
        document.getElementById("swipe-admin-form").style.display = "none";
        $("#swipe-customer-form").fadeIn(3000);
        document.getElementById("button-cancel").style.display = "block";
        document.getElementById("button-change").style.display = "none";
    }

    function get_info() {
        $("#head-name").fadeIn(3000);
        $("#swipe-admin-form").fadeIn(3000);
        document.getElementById("swipe-customer-form").style.display = "none";
        document.getElementById("button-cancel").style.display = "none";
        document.getElementById("button-change").style.display = "block";
    }

    function initMap() {
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

    function initMap2() {
        var lat = 60.431622;
        var lng = 30.516645;
        var map_center = new google.maps.LatLng(lat, lng);
        var mapOptions = {
            center: map_center,
            zoom: 16,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var mapCanvas2 = document.getElementById("map2");
        var map2 = new google.maps.Map(mapCanvas2, mapOptions);
        new google.maps.Marker({
            map: map2,
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

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCT7tBQN8l0fcDdcZUwuxD0XGjgM7qbTL4&callback=initMap2"
async defer></script>
<%--Google API Key: AIzaSyCT7tBQN8l0fcDdcZUwuxD0XGjgM7qbTL4 --%>
