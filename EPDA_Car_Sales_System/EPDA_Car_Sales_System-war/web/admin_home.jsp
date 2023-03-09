<%-- Document : adminHome Created on : 03-Feb-2023, 23:36:52 Author : leebe --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <!DOCTYPE html>
            <html>

            <head>
                <jsp:include page="html_head.jspf" />
                <title>Haus of Cars - Dashboard</title>

                <script>

                    $(document).ready(() => {

                        $("#btnFilter").click(() => {
                            if (validateFilters()) {
                                $("#frmReportFilters").submit();
                            }
                        })

                        // -- Init Reports --

                        // Sales Reports
                        rpt_salesPerMonth();
                        rpt_BookingsByStatus();
                        rpt_SalesCards();

                        // Customer Reports
                        rpt_BookingsByCustomers();
                        rpt_customerCards();

                        // Salesman Reports
                        rpt_salesmanSales();
                        rpt_salesmanCancelled();
                        rpt_salesmanAvgRating();
                        rpt_salesmanCards();

                        // Car Reports
                        rpt_carColours();
                        rpt_carSales();
                        rpt_carCards();
                    })

                    function validateFilters() {
                        let toDate = $("#rpt_toDateFilter").val();
                        let fromDate = $("#rpt_fromDateFilter").val();

                        if (toDate < fromDate) {
                            customAlert("Start Date cannot be before End Date!", "error");
                            return false;
                        }

                        return true;
                    }
                </script>
            </head>

            <body>
                <jsp:include page="_layout/header.jsp" />

                <h1>Dashboard</h1>

                <!-- Filters -->
                <div class="accordion" id="accordionExample">
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="headingOne">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                Report Filters
                            </button>
                        </h2>
                        <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne"
                            data-bs-parent="#accordionExample">
                            <div class="accordion-body">

                                <form action="Admin_Home" method="post" id="frmReportFilters">
                                    <input type="hidden" name="report" value="filter">
                                    <div class="row row-cols-2 row-cols-md-3">
                                        <div class="col">
                                            <label class="labels">Start Date</label>
                                            <input class="form-control" type="date" name="rpt_fromDateFilter"
                                                id="rpt_fromDateFilter" value="${sessionScope.fromDate}">
                                            <!-- <select class="input-select" name="rpt_monthFilter" id="rpt_monthFilter">
                                            <option value="January">January</option>
                                            <option value="February">February</option>
                                            <option value="March">March</option>
                                            <option value="April">April</option>
                                            <option value="May">May</option>
                                            <option value="June">June</option>
                                            <option value="July">July</option>
                                            <option value="August">August</option>
                                            <option value="September">September</option>
                                            <option value="October">October</option>
                                            <option value="November">November</option>
                                            <option value="December">December</option>
                                        </select> -->
                                        </div>

                                        <div class="col">
                                            <label class="labels">End Date</label>
                                            <input class="form-control" type="date" name="rpt_toDateFilter"
                                                id="rpt_toDateFilter" value="${sessionScope.toDate}">
                                        </div>
                                    </div>

                                    <div class="mt-3 d-flex justify-content-end">
                                        <button class="btn btn-primary" type="button"
                                            id="btnFilter">Filter</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <nav class="mt-5">
                    <div class="nav nav-tabs nav-justified" id="report-tab" role="tablist">
                        <button class="nav-link active" id="report-sales-tab" data-bs-toggle="tab"
                            data-bs-target="#report-sales" type="button" role="tab" aria-controls="report-sales"
                            aria-selected="true">Sales</button>

                        <button class="nav-link" id="report-customer-tab" data-bs-toggle="tab"
                            data-bs-target="#report-customer" type="button" role="tab" aria-controls="report-customer"
                            aria-selected="false">Customer</button>

                        <button class="nav-link" id="report-salesman-tab" data-bs-toggle="tab"
                            data-bs-target="#report-salesman" type="button" role="tab" aria-controls="report-salesman"
                            aria-selected="false">Salesman</button>

                        <button class="nav-link" id="report-cars-tab" data-bs-toggle="tab" data-bs-target="#report-cars"
                            type="button" role="tab" aria-controls="report-cars" aria-selected="false">Cars</button>
                    </div>
                </nav>
                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="report-sales" role="tabpanel"
                        aria-labelledby="report=sales-tab" tabindex="0">
                        <jsp:include page="admin_dashboard_sales.jsp" />
                    </div>

                    <div class="tab-pane fade" id="report-customer" role="tabpanel"
                        aria-labelledby="report-customer-tab" tabindex="0">
                        <jsp:include page="admin_dashboard_customer.jsp" />
                    </div>

                    <div class="tab-pane fade" id="report-salesman" role="tabpanel"
                        aria-labelledby="report-salesman-tab" tabindex="0">
                        <jsp:include page="admin_dashboard_salesman.jsp" />
                    </div>

                    <div class="tab-pane fade" id="report-cars" role="tabpanel" aria-labelledby="report-cars-tab"
                        tabindex="0">
                        <jsp:include page="admin_dashboard_cars.jsp" />
                    </div>
                </div>

            </body>

            </html>