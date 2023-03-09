<%-- Document : home Created on : 02-Feb-2023, 17:10:25 Author : leebe --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

                <!DOCTYPE html>
                <html>

                <head>
                    <title>Haus of Cars</title>
                    <jsp:include page="html_head.jspf" />
                </head>

                <body>
                    <jsp:include page="_layout/header.jsp" />

                    <h1 class="ms-5 mt-3">Car Comparison</h1>
                    <div class="container d-flex justify-content-center">
                        <div class="row row-cols-1 row-cols-md-3 mt-3 mb-3 g-4">

                            <c:forEach items="${requestScope.model}" var="data" varStatus="loop">

                                <div class="col-sm-4">
                                    <div class="card h-100">
                                        <img src="
                                        <c:choose>
                                            <c:when test='${data.carImage != null && !data.carImage.isEmpty()}'>
                                           ${data.carImage}
                                           </c:when>
                                           <c:otherwise>
                                               images/car-placeholder.avif
                                           </c:otherwise>
                                           </c:choose>" class="img card-img-top" width="100%"
                                            style="object-fit: cover;">
                                        <div class="card-body pt-0 px-0">
                                            <div class="d-flex flex-row justify-content-between mb-0 mt-2 px-3">
                                                <small class="text-muted mt-1">STARTING AT</small>
                                                <h6>RM
                                                    <fmt:formatNumber type="number" maxFractionDigits="2"
                                                        value="${data.price}" />
                                                </h6>
                                            </div>
                                            <hr class="mt-2 mx-3">
                                            <div class="d-flex flex-row justify-content-between px-3 pb-4">
                                                <div class="d-flex flex-column"><span class="text-muted">COLOUR</span>
                                                </div>
                                                <div class="d-flex flex-column">
                                                    <h5 class="mb-0">${data.colour}</h5>
                                                </div>
                                            </div>
                                            <div class="d-flex flex-row justify-content-between p-3">
                                                <div class="d-flex flex-column"><small class="text-muted mb-1">SEATING
                                                        CAPACITY</small>
                                                    <div class="d-flex flex-row"><i class="bi bi-people-fill"></i>
                                                        <div class="d-flex flex-column ms-3">
                                                            <h6>${data.capacity}</h6>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="d-flex flex-column"><small
                                                        class="text-muted mb-2">TRANSMISSION TYPE</small>
                                                    <div class="d-flex flex-row"><i class="bi bi-joystick"></i>
                                                        <h6 class="ms-3">${data.transmissionType}</h6>
                                                    </div>
                                                </div>
                                            </div>

                                            <form action="Customer_Booking_Create" method="post" id="frmBooking">
                                                <input type="hidden" name="carId" value="${data.carId}">
                                                <div class="text-center mt-3 mb-2"><button type="submit"
                                                        class="btn btn-danger" <c:if
                                                        test="${data.status != 'Available'}">disabled</c:if>>BOOK
                                                        NOW</button></div>
                                            </form>

                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <!-- If no data -->
                    <c:if test="${model.size() == 0}">
                        <h2 class="ms-5 text-center">[No Cars Selected for Comparison]</h2>
                    </c:if>
                </body>

                </html>