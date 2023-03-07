<%-- 
    Document   : home
    Created on : 02-Feb-2023, 17:10:25
    Author     : leebe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Haus of Cars - Customer</title>
        <jsp:include page="html_head.jspf"/>
        <script>
            $(document).ready(() => {
             

            });

            
        </script>
    </head>

    <body>
        <jsp:include page="_layout/header.jsp" />

        <div class="container rounded bg-white mt-5 mb-5">
            <div class="row">
                <div class="col-md-3 border-right">
                    <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                        <div class="profile-container">

                            <img class="profile-image mt-5" alt="User Profile Image"
                                style="border-radius: 25px 0px 25px 0px;" src="
                         <c:choose>
                             <c:when test='${model.car.carImage != null && !model.car.carImage.isEmpty()}'>
                            ${model.car.carImage}
                            </c:when>
                            <c:otherwise>
                                images/car-placeholder.avif
                            </c:otherwise>
                            </c:choose>">
                        </div>
                        <span class="font-weight-bold">${model.orderStatus}</span>

                    </div>
                </div>


                <div class="col-md-9 border-right">
                    <div class="p-3 py-5">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h4 class="text-right">Booking Details</h4>
                        </div>

                        <div class="row mt-3">

                            <form id="frmBookingDetails" action="Booking_Details" method="post">

                                <!-- Hidden Fields -->
                                <input type="hidden" name="id" value="${id}">
                                <input type="hidden" name="mode" value="${mode}">

                                <div class="col-md-12">
                                    <label class="labels">Car</label>
                                    <input type="text" class="form-select" id="carBrand" 
                                    value="${model.car.brand} | ${model.car.model}" disabled />
                               
                                </div>

                                <!-- <div class="col-md-12 mt-3">
                                    <label class="labels">Model</label>
                                    <input type="text" class="form-control" placeholder="Model" name="model"
                                        value="${model.model}" required>
                                </div>

                                <div class="col-md-12 mt-3">
                                    <label class="labels">Colour</label>
                                    <input type="text" class="form-select" id="carColour" name="colour"
                                        list="car-colour" required />
                                    <datalist id="car-colour">
                                        <option value="Red">Red</option>
                                        <option value="Blue">Blue</option>
                                        <option value="White">White</option>
                                    </datalist>
                                </div>

                                <div class="col-md-12 mt-3">
                                    <label class="labels">Transmission Type</label>
                                    <select class="form-select" id="carTransmissionType" name="transmissionType"
                                        required>
                                        <option value="Auto">Auto</option>
                                        <option value="Manual">Manual</option>
                                    </select>
                                </div> -->

                                <!-- <div class="col-md-12 mt-3">
                                    <label class="labels">Status</label>
                                    <input type="text" class="form-control" name="status"
                                        value="${model.status}" readonly>
                                  <select class="form-select" id="status" name="status" required>
                                        <option value="Available">Available</option>
                                        <option value="Booked">Booked</option>
                                        <option value="Paid">Paid</option>
                                        <option value="Cancel">Cancel</option>
                                    </select> 
                                </div> -->


                                <!-- <div class="col-md-12 mt-3">
                                    <label class="labels">Price</label>
                                    <input type="number" min="1" step="0.01" class="form-control" name="price"
                                        placeholder="Price" value="${model.price}" required>
                                </div>
                                <div class="col-md-12 mt-3">
                                    <label class="labels">Capacity</label>
                                    <input type="number" min="2" step="1" name="capacity" class="form-control"
                                        placeholder="Capacity" value="${model.capacity}" required>
                                </div> -->



                                <div class="mt-5 text-center">
                                    <!-- Back -->
                                    <a href="Admin_Manage_Cars" role="button"
                                        class="btn btn-outline-secondary bi bi-arrow-left-circle me-2">
                                        Back</a>

                                    <button class="btn btn-primary profile-button" type="submit">Save
                                        Details</button>

                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

    </body>
</html>
