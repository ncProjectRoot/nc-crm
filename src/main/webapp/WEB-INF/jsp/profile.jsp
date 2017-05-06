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

    #map, #map2 {
        height: 270px;
    }

    #map {width: 800px; }

    .little{
        height:80px;
    }
</style>
<script>
    function get_edit_form() {
        document.getElementById("head-name").style.display = "none"
        document.getElementById("swipe-admin-form").style.display = "none"
        document.getElementById("swipe-customer-form").style.display = "block"
        document.getElementById("button-cancel").style.display = "block"
        document.getElementById("button-change").style.display = "none"
    }

    function get_info() {
        document.getElementById("head-name").style.display = "block"
        document.getElementById("swipe-admin-form").style.display = "block"
        document.getElementById("swipe-customer-form").style.display = "none"
        document.getElementById("button-cancel").style.display = "none"
        document.getElementById("button-change").style.display = "block"
    }
</script>

<div class="content-header z-depth-1 valign-wrapper">
    <i class="black-text material-icons">person_pin</i>
    <span>Profile</span>
</div>
<div class="content-body-wrapper z-depth-1" >
    <div id="head-name" class="center-align">
        <h1 class="card-panel hoverable">Іванов Іван Іванович</h1>        
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
        <div class="row">
            <form class="col s12">
                <div class="row little" >
                    <div class="col s6" >                        
                        <div class="input-field">
                            <i id="user_email" class="material-icons prefix">email</i>                            
                            <label for="user_email">yarusrpog@mail.ru</label>                                       
                        </div>
                    </div>
                    <div class="col s6">                        
                        <div class="input-field">
                            <i id="user_phone" class="material-icons prefix">phone</i>                            
                            <label for="user_phone">0933794982</label>
                        </div>   
                    </div>
                </div>
                <div  class="row little" >
                    <div class="col s6">                        
                        <div class="input-field">
                            <i id="customer_organization" class="material-icons prefix">business</i>                            
                            <label for="customer_organization">КПІ</label>
                        </div>
                    </div>
                    <div class="col s6">  
                        <div class="input-field ">
                            <i id="cus" class="material-icons prefix">open_with</i>
                            <label for="cus">Квартира 53</label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col s6" > 
                        <span>Your Address</span>
                        <div id="map" ></div>
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
                            <div id="map2" ></div>
                        </div>
                        <div class="input-field">
                            <input type="checkbox" class="filled-in" id="customer_contact_person"
                                   checked="checked"/>
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

    function initMap2() {
        var lat = 50.431622;
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
