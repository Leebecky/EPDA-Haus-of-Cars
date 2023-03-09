<%-- Document : admin_dashboard_sales Created on : 08-Mar-2023, 09:18:46 Author : leebe --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>

        <script>
            function rpt_BookingsByCustomers() {
                $.post("Admin_Home", $.param({ "report": "rpt_BookingsByCustomers" }), function (response) {
                    var json = JSON.parse(response);
                    // console.log(json)
                    const ctx = document.getElementById('rpt_BookingsByCustomers');

                    new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: JSON.parse(json[0]),
                            datasets: [{
                                label: 'Booked',
                                data: json[1],
                                backgroundColor: "#FFFF00",
                                parsing: {
                                    yAxisKey: "Booked"
                                }
                            },
                            {
                                label: 'Paid',
                                data: json[1],
                                backgroundColor: "#00FF00",
                                parsing: {
                                    yAxisKey: "Paid"
                                }
                            },
                            {
                                label: 'Cancelled',
                                data: json[1],
                                backgroundColor: "#FF0000",
                                parsing: {
                                    yAxisKey: "Cancelled"
                                }
                            }, {
                                label: 'Pending Salesman',
                                data: json[1],
                                backgroundColor: "#0000FF",
                                parsing: {
                                    yAxisKey: "Pending Salesman"
                                }
                            },]
                        },
                        options: {
                            parsing: {
                                xAxisKey: 'name',
                            },
                            responsive: true,
                            scales: {
                                x: {
                                    stacked: true,
                                },
                                y: {
                                    stacked: true
                                },
                            }, plugins: {
                                title: {
                                    display: true,
                                    text: 'Bookings per Customer'
                                },
                            }
                        }
                    }
                    );
                });
            }

            function rpt_customerCards() {
                $.post("Admin_Home", $.param({ "report": "rpt_customerCards" }), function (response) {
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
                            <h5 class="card-title" id="approvedCustomers">0</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Approved Customers</h6>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title" id="pendingCustomers">0</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Pending Customers</h6>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title" id="highestSpender">N/A</h5>
                            <h6 class="card-subtitle mb-2 text-muted">All Time Highest Spender</h6>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row row-cols-1 mt-5">
                <div class="col">
                    <canvas id="rpt_BookingsByCustomers"></canvas>
                </div>
            </div>
        </div>