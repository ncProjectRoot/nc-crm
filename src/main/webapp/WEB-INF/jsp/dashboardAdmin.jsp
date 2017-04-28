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

    .ct-label {
        fill: #fff;
        color: #fff;
        font-size: 0.75rem;
        line-height: 1;
    }

    .region-errors-chart .ct-series:nth-child(3n-1) .ct-slice-donut-solid {
        fill: #d50000;
    }

    .region-errors-chart .ct-series:nth-child(3n-2) .ct-slice-donut-solid {
        fill: #f44336;
    }

    .region-errors-chart .ct-series:nth-child(3n-3) .ct-slice-donut-solid {
        fill: #b71c1c;
    }

    .region-orders-chart .ct-series:nth-child(3n-1) .ct-slice-donut-solid {
        fill: #00c853
    }

    .region-orders-chart .ct-series:nth-child(3n-2) .ct-slice-donut-solid {
        fill: #1b5e20;
    }

    .region-orders-chart .ct-series:nth-child(3n-3) .ct-slice-donut-solid {
        fill: #4caf50;
    }

    .region-profit-chart {
        margin-top: 50px;
    }

    .region-profit-chart span {
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
            <span class="card-title activator">Region Errors<i class="material-icons right">more_vert</i></span>
            <div class="region-errors-chart-wrapper">
                <div class="region-errors-chart"></div>
            </div>
        </div>
        <div class="card-action">
            <a href="#">See all information</a>
        </div>
        <div class="card-reveal">
            <span class="card-title grey-text text-darken-4">Region Error Information<i class="material-icons right">close</i></span>
            <p>Kiev - 20</p>
            <p>Lviv - 15</p>
            <p>Zhitomir - 40</p>
        </div>
    </div>

    <div class="content-element z-depth-1 element6 card">
        <div class="card-content">
            <span class="card-title activator">Region Orders<i class="material-icons right">more_vert</i></span>
            <div class="region-orders-chart-wrapper">
                <div class="region-orders-chart"></div>
            </div>
        </div>
        <div class="card-action">
            <a href="#">See all information</a>
        </div>
        <div class="card-reveal">
            <span class="card-title grey-text text-darken-4">Region Orders Information<i class="material-icons right">close</i></span>
            <p>Kiev - 99</p>
            <p>Lviv - 12</p>
            <p>Zhitomir - 5</p>
        </div>
    </div>
    <div class="content-element z-depth-1 element12 card">
        <div class="card-content">
            <span class="card-title activator">Profit in the regions</span>
            <div class="region-profit-chart-wrapper">
                <div class="region-profit-chart"></div>
            </div>
        </div>
        <div class="card-action">
            <a href="#profit">Read more</a>
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

    var lineOption = {
        low: 0,
        height: 300,
        // showArea: true,
        showPoint: true,
        fullWidth: true,
        chartPadding: 20,
        showLabel: true
    };


    var animateLine = function (data) {
        seq++;
        if (data.type === 'line' || data.type === 'area') {
            if (data.type === 'line') {
                var obj = $(data.element._node);
                var index = "a";
                lineLabels.forEach(function (element, i, array) {
                    if (obj.is(".ct-series-" + index + " .ct-line")) {
                        obj.tooltip({
                            delay: 0
                        });
                        obj.attr("data-tooltip", element);
                    }
                    index = String.fromCharCode(index.charCodeAt(0) + 1);
                });
            }
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
    }

    new Chartist.Pie('.region-errors-chart', {
        labels: ['Kiev', 'Lviv', 'Zhitomir'],
        series: [20, 15, 40],
    }, pieOptions).on('draw', animatePie);

    new Chartist.Pie('.region-orders-chart', {
        labels: ['Kiev', 'Lviv', 'Zhitomir'],
        series: [99, 12, 5],
    }, pieOptions).on('draw', animatePie);

    var lineLabels = ["Kiev", "Lviv", "Zhitomir"];
    new Chartist.Line('.region-profit-chart', {
        labels: [1, 2, 3, 4, 5, 6, 7],
        series: [
            [12, 9, 7, 8, 5, 5, 7],
            [2, 1, 3.5, 7, 3, 4, 5],
            [1, 3, 4, 5, 6, 6, 5]
        ]
    }, lineOption).on('created', function () {
        seq = 0;
    }).on('draw', animateLine);
</script>