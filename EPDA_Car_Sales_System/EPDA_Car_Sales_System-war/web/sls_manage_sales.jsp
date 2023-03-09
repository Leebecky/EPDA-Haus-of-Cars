<%-- Document : manage_users Created on : 03-Feb-2023, 23:49:32 Author : leebe --%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <jsp:include page="html_head.jspf" />
                    <title>Haus of Cars - Salesman</title>

                    <script>

                        $(document).ready(function () {
                            // Table
                            $("#salesTable").fancyTable({
                                sortColumn: 0,
                                pagination: true,
                                perPage: 10,
                                globalSearch: true,
                                globalSearchExcludeColumns: [1, 7]
                            });

                        });


                        // cancel booking
                        function cancelBooking(salesId) {
                            let req = confirm("Are you sure you want to cancel the booking?");
                            if (req) {
                                $("#frmCancelBooking-" + salesId).submit();
                            }
                        }

                        // pay booking
                        function payBooking(salesId) {
                            let req = confirm("Has the customer paid for this transaction?");
                            if (req) {
                                $("#frmPayBooking-" + salesId).submit();
                            }
                        }
                    </script>

                </head>

                <body>
                    <jsp:include page="_layout/header.jsp" />

                    <!-- User Data Table -->
                    <div class="mx-4">
                        <div class="d-flex justify-content-between align-items-center">
                            <h1 class="">Manage Sales</h1>
                            <div>
                                <a href="Sls_Commission_Sales" role="button"
                                    class="btn btn-outline-primary my-2 bi bi-calendar-check"
                                    style="font-size: 1.5rem;">
                                    Accept Sales Booking</a>

                                <a href="Booking_Details?id=-1&mode=New" role="button"
                                    class="btn btn-outline-primary my-2 bi bi-plus-circle" style="font-size: 1.5rem;">
                                    Add New Booking</a>
                            </div>
                        </div>

                        <table id="salesTable" class="table table-striped align-middle">
                            <thead class="table-dark">
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Car</th>
                                    <th scope="col">Customer</th>
                                    <th scope="col">Total Payable (RM)</th>
                                    <th scope="col">Sales Date</th>
                                    <th scope="col">Order Status</th>
                                    <th scope="col"></th>
                                </tr>
                            </thead>
                            <tbody>

                                <c:forEach items="${requestScope.model}" var="data" varStatus="loop">
                                    <tr>
                                        <td scope="row">
                                            <b>
                                                <c:out value="${loop.index+1}" />
                                            </b>
                                        </td>
                                        <td>${data.car.brand}</td>
                                        <td>${data.customer.fullname}</td>
                                        <td>
                                            <fmt:formatNumber type="number" maxFractionDigits="2"
                                                value="${data.totalPayable}" />
                                        </td>
                                        <td>${data.salesDate}</td>
                                        <td>${data.orderStatus}</td>
                                        <td style="text-align: end;">

                                            <!-- Review -->
                                            <a href="Booking_Details?mode=Review&id=${data.salesId}" role="button"
                                                class="btn btn-outline-warning bi bi-card-text me-2"
                                                style="font-size: 1.5rem;"> Review</a>

                                            <!-- Pay -->
                                            <c:if
                                                test="${!data.orderStatus.equals('Paid') && !data.orderStatus.equals('Cancelled')}">
                                                <form id="frmPayBooking-${data.salesId}" action="Sls_Booking_Paid"
                                                    method="post" class="btn">
                                                    <input type="hidden" name="salesId" value="${data.salesId}">
                                                    <button type="button" class="btn btn-outline-success bi bi-coin"
                                                        style="font-size: 1.5rem;"
                                                        onclick="payBooking('${data.salesId}')">
                                                        Pay Booking</button>
                                                </form>
                                            </c:if>

                                            <!-- Cancel -->
                                            <c:if test="${data.orderStatus.equals('Booked')}">
                                                <form id="frmCancelBooking-${data.salesId}"
                                                    action="Customer_Booking_Cancel" method="post" class="btn">
                                                    <input type="hidden" name="salesId" value="${data.salesId}">
                                                    <button type="button" class="btn btn-outline-danger bi bi-x-circle"
                                                        style="font-size: 1.5rem;"
                                                        onclick="cancelBooking('${data.salesId}')">
                                                        Cancel</button>
                                                </form>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <!-- If no data -->
                                <c:if test="${model.size() == 0}">
                                    <tr>
                                        <td class="text-center">No data found</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </body>

                </html>