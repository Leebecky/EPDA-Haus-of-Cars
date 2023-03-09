<%-- Document : admin_dashboard_sales Created on : 08-Mar-2023, 09:18:46 Author : leebe --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <script>
            function rpt_salesmanCancelled() {
                $.post("Admin_Home", $.param({ "report": "rpt_salesmanCancelled" }), function (response) {
                    var json = JSON.parse(response);
                    var data = json.data;
                    var labels = json.labels;

                    const ctx = document.getElementById('rpt_salesmanCancelled');

                    new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: JSON.parse(labels),
                            datasets: [{
                                label: 'Number of Bookings',
                                data: JSON.parse(data),
                                borderWidth: 1
                            }]
                        },
                        options: {
                            responsive: true,
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            },
                            plugins: {
                                title: {
                                    display: true,
                                    text: 'Total Number of Cancelled Bookings'
                                }
                            }
                        }
                    });
                });
            }

            function rpt_salesmanSales() {
                $.post("Admin_Home", $.param({ "report": "rpt_salesmanSales" }), function (response) {
                    var json = JSON.parse(response);
                    var data = json.data;
                    var labels = json.labels;

                    const ctx = document.getElementById('rpt_salesmanSales');

                    new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: JSON.parse(labels),
                            datasets: [{
                                label: 'Number of Bookings',
                                data: JSON.parse(data),
                                borderWidth: 1
                            }]
                        },
                        options: {
                            responsive: true,
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            },
                            plugins: {
                                title: {
                                    display: true,
                                    text: 'Total Number of Sales'
                                }
                            }
                        }
                    });
                });
            }

            function rpt_salesmanAvgRating() {
                $.post("Admin_Home", $.param({ "report": "rpt_salesmanAvgRating" }), function (response) {
                    var json = JSON.parse(response);
                    var data = json.data;
                    var labels = json.labels;

                    const ctx = document.getElementById('rpt_salesmanAvgRating');

                    new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: JSON.parse(labels),
                            datasets: [{
                                label: 'Average Rating',
                                data: JSON.parse(data),
                                borderWidth: 1
                            }]
                        },
                        options: {
                            responsive: true,
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            },
                            plugins: {
                                title: {
                                    display: true,
                                    text: 'Average Service Rating per Salesman'
                                }
                            }
                        }
                    });
                });
            }

            function rpt_salesmanCards() {
                $.post("Admin_Home", $.param({ "report": "rpt_salesmanCards" }), function (response) {
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
                            <h5 class="card-title" id="approvedSalesman">0</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Approved Salesman</h6>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title" id="pendingSalesman">0</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Pending Salesman</h6>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title" id="bestService">N/A</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Highest Rated Salesman</h6>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row row-cols-1 row-cols-md-2 mt-5">

                <div class="col">
                    <canvas id="rpt_salesmanSales"></canvas>
                </div>

                <div class="col">
                    <canvas id="rpt_salesmanCancelled"></canvas>
                </div>

                <div class="col">
                    <canvas id="rpt_salesmanAvgRating"></canvas>
                </div>
            </div>
        </div>