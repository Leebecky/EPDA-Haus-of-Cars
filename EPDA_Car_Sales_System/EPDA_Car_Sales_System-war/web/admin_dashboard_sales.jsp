<%-- Document : admin_dashboard_sales Created on : 08-Mar-2023, 09:18:46 Author : leebe --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>

        <script>
            function rpt_salesPerMonth(month) {
                $.post("Admin_Home", $.param({ "report": "rpt_salesPerMonth", "monthFilter": month }), function (response) {
                    var json = JSON.parse(response);
                    var data = json.data;
                    var labels = json.labels;

                    const ctx = document.getElementById('rpt_salesPerMonth');

                    new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: JSON.parse(labels),
                            datasets: [{
                                label: 'Total Sales',
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
                                    text: 'Total Sales per Month for ${sessionScope.currentYear}'
                                }
                            }
                        }
                    });
                });
            }

            function rpt_BookingsByStatus() {
                $.post("Admin_Home", $.param({ "report": "rpt_BookingsByStatus" }), function (response) {
                    var json = JSON.parse(response);
                    var data = json.data;
                    var labels = json.labels;

                    const ctx = document.getElementById('rpt_BookingsByStatus');

                    new Chart(ctx, {
                        type: 'pie',
                        data: {
                            labels: JSON.parse(labels),
                            datasets: [{
                                label: 'Bookings',
                                data: JSON.parse(data),
                                borderWidth: 1
                            }]
                        },
                        options: {
                            responsive: true,
                            plugins: {
                                legend: {
                                    position: 'top',
                                },
                                title: {
                                    display: true,
                                    text: 'Number of Bookings by Status'
                                }
                            }
                        }
                    });
                });
            }

            function rpt_SalesCards() {
                $.post("Admin_Home", $.param({ "report": "rpt_SalesCards" }), function (response) {
                    var json = JSON.parse(response);
                    var data = JSON.parse(json.data);
                    var labels = JSON.parse(json.labels);
                    for (let i = 0; i < data.length; i++) {
                        let element = "#" + labels[i];

                        if (element == "#totalPaidBookingsCard") {
                            $(element).html(data[i]);
                        } else {
                            $(element).html("RM " + data[i].toFixed(2));
                        }

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
                            <h5 class="card-title" id="totalSalesCard">RM 0</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Total Sales for ${sessionScope.currentMonth}</h6>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title" id="totalPaidBookingsCard">0</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Paid Bookings for
                                ${sessionScope.currentMonth}</h6>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title" id="avgSalesCard">RM 0</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Average Sales for ${sessionScope.currentMonth}
                            </h6>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row row-cols-1 row-cols-md-2 mt-5">

                <div class="col">
                    <canvas id="rpt_salesPerMonth"></canvas>
                </div>

                <div class="col">
                    <canvas id="rpt_BookingsByStatus"></canvas>
                </div>
            </div>
        </div>