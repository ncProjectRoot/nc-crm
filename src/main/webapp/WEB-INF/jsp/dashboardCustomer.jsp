<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .content-body-wrapper {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
    }

    .content-element {
        margin: 0 auto;
        margin-top: 15px;
        background-color: #fff;
    }

    .card-element {
        /*width: calc(25% - 30px);*/
        min-width: 250px;
        margin: 30px;
    }

    .element6 {
        width: calc(50% - 30px);
    }

    .element12 {
        width: calc(100% - 30px);
    }

    .orders-wrapper, .products-wrapper {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        align-items: center;
    }


    .card-element.new {
        background-color: #3e2723;
    }

    .card-element.in-queue {
        background-color: #ffb300;
    }

    .card-element.processing {
        background-color: #4527a0;
    }

    .card-element.active {
        background-color: #33691e;
    }

    .card-element.disabled {
        background-color: #757575;
    }

    .card-element.paused {
        background-color: #b71c1c;
    }


    .card-element.planned {
        background-color: #ffb300;
    }

    .card-element.actual {
        background-color: #33691e;
    }

    .card-element.outdated {
        background-color: #b71c1c;
    }

    .content-body-wrapper .footer {
        height: 50px;
        width: 100%
    }
</style>
<div class="content-header z-depth-1 valign-wrapper">
    <i class="black-text material-icons">dashboard</i>
    <span>Dashboard</span>
</div>
<div class="content-body-wrapper">

    <div class="content-element z-depth-1 element12 card">
        <div class="card-content">
            <span class="card-title activator">Orders<i class="material-icons right">more_vert</i></span>
            <div class="orders-wrapper">

                <div class="card card-element processing">
                    <div class="card-content white-text">
                        <span class="card-title">Order #12</span>
                        <p>Status: Processing</p>
                    </div>
                    <div class="card-action">
                        <a href="#">More</a>
                        <a href="#">Complain</a>
                    </div>
                </div>

                <div class="card card-element active">
                    <div class="card-content white-text">
                        <span class="card-title">Order #15</span>
                        <p>Status: Active</p>
                    </div>
                    <div class="card-action">
                        <a href="#">More</a>
                        <a href="#">Complain</a>
                    </div>
                </div>

                <div class="card card-element paused">
                    <div class="card-content white-text">
                        <span class="card-title">Order #16</span>
                        <p>Status: Paused</p>
                    </div>
                    <div class="card-action">
                        <a href="#">More</a>
                        <a href="#">Complain</a>
                    </div>
                </div>


            </div>
        </div>
        <div class="card-reveal">
            <span class="card-title grey-text text-darken-4">Orders statistics<i class="material-icons right">close</i></span>
            <div class="orders-dounut-chart-wrapper">

            </div>
        </div>
    </div>

    <div class="content-element z-depth-1 element12 card">
        <div class="card-content">
            <span class="card-title activator">Products<i class="material-icons right">more_vert</i></span>
            <div class="products-wrapper">

                <div class="card card-element actual">
                    <div class="card-content white-text">
                        <span class="card-title">Product #64</span>
                        <p>Status: Actual</p>
                    </div>
                    <div class="card-action">
                        <a href="#">More</a>
                        <a href="#">Complain</a>
                    </div>
                </div>

                <div class="card card-element outdated">
                    <div class="card-content white-text">
                        <span class="card-title">Product #21</span>
                        <p>Status: Outdated</p>
                    </div>
                    <div class="card-action">
                        <a href="#">More</a>
                        <a href="#">Complain</a>
                    </div>
                </div>


            </div>
        </div>
        <div class="card-reveal">
            <span class="card-title grey-text text-darken-4">Products statistics<i
                    class="material-icons right">close</i></span>

        </div>
    </div>

    <div class="footer"></div>
</div>
<script></script>