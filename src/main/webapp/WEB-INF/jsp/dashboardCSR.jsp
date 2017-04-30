<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .content-body-wrapper {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
    }

    .content-element {
        margin: 15px auto 0;
        background-color: #fff;
    }

    .element6 {
        width: calc(50% - 30px);
    }

    .element12 {
        width: calc(100% - 30px);
    }

    .orders-dounut-chart .ct-label {
        fill: #fff;
        color: #fff;
        font-size: 0.75rem;
        line-height: 1;
    }

    .orders-dounut-chart .ct-series:nth-child(4n-1) .ct-slice-donut-solid {
        fill: #9c27b0;
    }

    .orders-dounut-chart .ct-series:nth-child(4n-2) .ct-slice-donut-solid {
        fill: #4a148c;
    }

    .orders-dounut-chart .ct-series:nth-child(4n-3) .ct-slice-donut-solid {
        fill: #aa00ff;
    }

    .orders-dounut-chart .ct-series:nth-child(4n-4) .ct-slice-donut-solid {
        fill: #7b1fa2;
    }

    .orders-chart {
        margin-top: 50px;
    }

    .orders-chart span {
        color: #000;
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

    <div class="content-element z-depth-1 element6 card">
        <div class="card-content">
            <span class="card-title activator">Orders (1 week) Bar<i class="material-icons right">more_vert</i></span>
            <div class="orders-bar-chart-wrapper">
                <div class="orders-bar-chart"></div>
            </div>
        </div>
        <div class="card-action">
            <a href="#search">See all information</a>
        </div>
        <div class="card-reveal">
            <span class="card-title grey-text text-darken-4">Orders (1 week) Information<i class="material-icons right">close</i></span>
            <p>10m/s - 800000</p>
            <p>500m/s - 1200000</p>
            <p>1000m/s - 1400000</p>
            <p>2000m/s - 1300000</p>
        </div>
    </div>

    <div class="content-element z-depth-1 element6 card">
        <div class="card-content">
            <span class="card-title activator">Orders (1 week) Dounut<i class="material-icons right">more_vert</i></span>
            <div class="orders-dounut-chart">
                <div class="orders-dounut-chart"></div>
            </div>
        </div>
        <div class="card-action">
            <a href="#search">See all information</a>
        </div>
        <div class="card-reveal">
            <span class="card-title grey-text text-darken-4">Orders (1 week) Information<i class="material-icons right">close</i></span>
            <p>10m/s - 800000</p>
            <p>500m/s - 1200000</p>
            <p>1000m/s - 1400000</p>
            <p>2000m/s - 1300000</p>
        </div>
    </div>
    <div class="content-element z-depth-1 element12 card">
        <div class="card-content">
            <span class="card-title activator">All orders (1 week)</span>
            <div class="orders-chart-wrapper">
                <div class="orders-chart"></div>
            </div>
        </div>
        <div class="card-action">
            <a href="#search">See all information</a>
        </div>
    </div>
    <div class="footer"></div>
</div>
<script>
    var seq = 0,
        delays = 80,
        durations = 250;

    var pieOptions = {
        height: 300,
        donut: true,
        donutWidth: 60,
        donutSolid: true,
        startAngle: 270,
        showLabel: true
    };

    var animatePie = function (data) {
        if (data.type === 'slice') {
            seq++;
            data.element.animate({
                opacity: {
                    begin: seq * durations,
                    dur: durations,
                    from: 0,
                    to: 1,
                    easing: 'easeOutQuart'
                }
            })
        }
    };

    var animateBar = function (data) {
        if (data.type === 'bar') {
            data.element.attr({
                style: 'stroke-width: 30px'
            });
            seq++;
            data.element.animate({
                opacity: {
                    begin: seq * durations,
                    dur: durations,
                    from: 0,
                    to: 1,
                    easing: 'easeOutQuart'
                }
            })
        }
    };

    var lineOption = {
        low: 0,
        height: 300,
        // showArea: true,
        showPoint: true,
        fullWidth: true,
        chartPadding: 20,
        showLabel: true
    };

    var barOption = {
        stackBars: true,
        height: 300,
        chartPadding: 20
    };


    var animateLine = function (data) {
        seq++;
        if (data.type === 'line' || data.type === 'area') {
            data.element.animate({
                opacity: {
                    begin: seq * delays + 1000,
                    dur: durations,
                    from: 0,
                    to: 1
                }
            });
        } else if (data.type === 'point') {
            data.element.animate({
                x1: {
                    begin: seq * delays,
                    dur: durations,
                    from: data.x - 10,
                    to: data.x,
                    easing: 'easeOutQuart'
                },
                x2: {
                    begin: seq * delays,
                    dur: durations,
                    from: data.x - 10,
                    to: data.x,
                    easing: 'easeOutQuart'
                },
                opacity: {
                    begin: seq * delays,
                    dur: durations,
                    from: 0,
                    to: 1,
                    easing: 'easeOutQuart'
                }
            })
        }
    };

    new Chartist.Bar('.orders-bar-chart', {
        labels: ['10m/s', '500m/s', '1000m/s', '2000m/s'],
        series: [
            [800000, 1200000, 1400000, 1300000]
        ]
    }, barOption).on('draw', animateBar);

    new Chartist.Pie('.orders-dounut-chart', {
        labels: ['10m/s', '500m/s', '1000m/s', '2000m/s'],
        series: [800000, 1200000, 1400000, 1300000]
    }, pieOptions).on('draw', animatePie);

    new Chartist.Line('.orders-chart', {
        labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
        series: [
            [12, 9, 7, 8, 5, 5, 7]
        ]
    }, lineOption).on('created', function () {
        seq = 0;
    }).on('draw', animateLine);
</script>