<%-- Document : admin_dashboard_sales Created on : 08-Mar-2023, 09:18:46 Author : leebe --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>

        <script>
            function rpt_carColours() {
                $.post("Admin_Home", $.param({ "report": "rpt_carColours" }), function (response) {
                    var json = JSON.parse(response);
                    var data = json.data;
                    var labels = json.labels;

                    const ctx = document.getElementById('rpt_carColours');

                    new Chart(ctx, {
                        type: 'pie',
                        data: {
                            labels: JSON.parse(labels),
                            datasets: [{
                                label: 'Cars Sold',
                                data: JSON.parse(data),
                                borderWidth: 1
                            }]
                        },
                        options: {
                            responsive: true,
                            plugins: {
                                title: {
                                    display: true,
                                    text: 'Number of Cars by Colours Sold'
                                },
                            }
                        }
                    });
                });
            }

            function rpt_carSales() {
                $.post("Admin_Home", $.param({ "report": "rpt_carSales" }), function (response) {
                    var json = JSON.parse(response);
                    // console.log(json)
                    var rating = json.rating;
                    var sales = json.sales;
                    var labels = json.labels;

                    const ctx = document.getElementById('rpt_carSales');

                    new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: JSON.parse(labels),
                            datasets: [{
                                label: 'Cars Sold',
                                data: sales,
                                borderWidth: 1,
                                order: 1
                            }, {
                                label: 'Average Car Rating',
                                data: rating,
                                borderWidth: 1,
                                type: 'line',
                                order: 0
                            }]
                        },
                        options: {
                            parsing: {
                                xAxisKey: 'brand',
                                yAxisKey: 'data',
                            },
                            responsive: true,
                            scales: {
                                y: {
                                    type: 'linear',
                                    display: true,
                                    position: 'left',
                                },
                                y1: {
                                    type: 'linear',
                                    display: true,
                                    position: 'right',

                                    // grid line settings
                                    grid: {
                                        drawOnChartArea: false, // only want the grid lines for one axis to show up
                                    },
                                }
                            }, plugins: {
                                title: {
                                    display: true,
                                    text: 'Car Sales & Rating'
                                },
                            }
                        }
                    });
                });
            }

            function rpt_carCards() {
                $.post("Admin_Home", $.param({ "report": "rpt_carCards" }), function (response) {
                    var json = JSON.parse(response);
                    var data = JSON.parse(json.data);
                    var labels = JSON.parse(json.labels);
                    for (let i = 0; i < data.length; i++) {
                        let element = "#" + labels[i];
                            $(element).html(data[i]);
                    }
                });
            }
        </script>

        <div class="container text-center mt-5 mb-3">
            <!-- Cards -->
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title" id="availableCars">0</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Available Cars</h6>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title" id="bookedCars">0</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Booked Cars</h6>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title" id="bestSellingModel">N/A</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Best Selling Model</h6>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row row-cols-1 row-cols-md-2 mt-5">

                <div class="col">
                    <canvas id="rpt_carColours"></canvas>
                </div>

                <div class="col">
                    <canvas id="rpt_carSales"></canvas>
                </div>
            </div>
        </div>