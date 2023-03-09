<%-- Document : catalogue_cars Created on : 02-Feb-2023, 17:10:25 Author : leebe --%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>

                <!DOCTYPE html>
                <html>

                <head>
                    <title>Haus of Cars</title>
                    <jsp:include page="html_head.jspf" />

                    <script>
                        $(document).ready(() => {

                            toggleStatusVisibility("Paid", "hide");
                            toggleStatusVisibility("Booked", "hide");

                            $("#availableFilter").change(() => {
                                let filterVal = $("#availableFilter").prop("checked");
                                if (filterVal) {
                                    toggleStatusVisibility("Available", "show");
                                } else {
                                    toggleStatusVisibility("Available", "hide");
                                }
                            });

                            $("#bookedFilter").change(() => {
                                let filterVal = $("#bookedFilter").prop("checked");

                                if (filterVal) {
                                    toggleStatusVisibility("Booked", "show");
                                } else {
                                    toggleStatusVisibility("Booked", "hide");
                                }
                            });

                            $("#paidFilter").change(() => {
                                let filterVal = $("#paidFilter").prop("checked");

                                if (filterVal) {
                                    toggleStatusVisibility("Paid", "show");
                                } else {
                                    toggleStatusVisibility("Paid", "hide");
                                }
                            });
                        });

                        function toggleStatusVisibility(status, visible) {
                            if (visible == "show") {
                                $(".car-" + status).show()
                            } else {
                                console.log("HELP")
                                $(".car-" + status).hide()
                            }
                        }
                    </script>
                </head>

                <body>
                    <jsp:include page="_layout/header.jsp" />


                    <div class="container mt-5 mb-5">
                        <div class="d-flex justify-content-center row">

                            <div class="col-md-10">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="d-flex justify-content-start align-items-center">
                                        <h1>Catalogue - Cars</h1>

                                        <!-- Filter -->
                                        <div class="dropdown ms-3">
                                            <button class="btn btn-secondary dropdown-toggle" type="button"
                                                data-bs-toggle="dropdown" aria-expanded="false">
                                                Filter
                                            </button>
                                            <ul class="dropdown-menu">
                                                <li class="ms-2"><input class="form-check-input" type="checkbox"
                                                        checked="true" name="availableFilter" id="availableFilter">
                                                    <label class="form-check-label" for="availableFilter">
                                                        Available
                                                    </label>
                                                </li>
                                                <li class="ms-2"><input class="form-check-input" type="checkbox"
                                                        value="false" name="bookedFilter" id="bookedFilter">
                                                    <label class="form-check-label" for="bookedFilter">
                                                        Booked
                                                    </label>
                                                </li>
                                                <li class="ms-2"><input class="form-check-input" type="checkbox"
                                                        value="false" name="paidFilter" id="paidFilter">
                                                    <label class="form-check-label" for="paidFilter">
                                                        Paid
                                                    </label>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <a class="btn btn-outline-dark" href="Catalogue_Comparison">Compare Cars
                                        <span
                                            class="badge rounded-pill text-bg-info">${sessionScope.comparison.size()}</span>
                                    </a>
                                </div>
                                <!-- Car -->
                                <c:forEach items="${requestScope.model}" var="data" varStatus="loop">

                                    <div class="car-${data.status} row p-2 bg-white border rounded mb-3">
                                        <div class="col-md-3 mt-1">
                                            <img class="img-fluid img-responsive rounded product-image" src="
                                     <c:choose>
                                         <c:when test='${data.carImage != null && !data.carImage.isEmpty()}'>
                                             ${data.carImage}
                                         </c:when>
                                         <c:otherwise>
                                             images/car-placeholder.avif
                                         </c:otherwise>
                                     </c:choose>">
                                        </div>
                                        <div class="col-md-6 mt-1">
                                            <h5>${data.brand} | ${data.model}</h5>
                                            <div class="d-flex flex-row">
                                                <!--                                    <div class="ratings mr-2">
                                                                <i class="bi-star"></i>
                                                            </div>
                                                            <span>310</span>-->
                                                <span class="
                                          <c:choose>
                                              <c:when test='${data.status.equals(" Available")}'>text-success</c:when>
                                                    <c:when test='${data.status.equals("Booked")}'>text-danger</c:when>
                                                    <c:when test='${data.status.equals("Paid")}'>text-secondary</c:when>
                                                    <c:otherwise>text-primary</c:otherwise>
                                                    </c:choose>
                                                    ">${data.status}
                                                </span>
                                            </div>
                                            <div class="mt-1 mb-1 spec-1">
                                                <span>${data.colour}</span>
                                                <span class="dot"></span><span>Seating Capacity: ${data.capacity}</span>
                                                <span class="dot"></span><span>${data.transmissionType}<br></span>
                                            </div>

                                        </div>
                                        <div class="col-md-3 border-left mt-1 d-flex flex-column"
                                            style="justify-content: space-between;">
                                            <div class="d-flex flex-row align-items-center mt-2">
                                                <h4 class="mr-1">RM
                                                    <fmt:formatNumber type="number" maxFractionDigits="2"
                                                        value="${data.price}" />
                                                </h4>
                                            </div>
                                            <!-- <h6 class="text-success">Free shipping</h6> -->
                                            <div class="d-flex flex-column mt-3">
                                                <!-- <a class="btn btn-primary btn-sm" href="Catalogue_Cars_Details?id=${data.carId}">Details</a> -->

                                                <!-- Create Booking -->
                                                <form action="Customer_Booking_Create" method="post" id="frmBooking">
                                                    <input type="hidden" name="carId" value="${data.carId}">
                                                    <button class="form-control btn btn-primary btn-sm" type="submit"
                                                        <c:if test="${data.status != 'Available'}">disabled</c:if>
                                                        >Book car</button>
                                                </form>
                                                <c:choose>
                                                    <c:when test="${comparison.contains(data.carId)}">
                                                        <button class="btn btn-secondary btn-sm mt-2"
                                                            type="button">Added to Comparison</button>
                                                    </c:when>
                                                    <c:otherwise>


                                                        <form action="Catalogue_Comparison" method="post"
                                                            id="frmComparison">
                                                            <input type="hidden" name="carId" value="${data.carId}">
                                                            <button
                                                                class="form-control btn btn-outline-secondary btn-sm mt-2"
                                                                type="submit">Add to comparison</button>
                                                        </form>
                                                    </c:otherwise>
                                                </c:choose>

                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </body>

                </html>