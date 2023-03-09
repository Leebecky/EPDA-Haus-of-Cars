<%-- Document : home Created on : 02-Feb-2023, 17:10:25 Author : leebe --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

                <!DOCTYPE html>
                <html>

                <head>
                    <title>Haus of Cars - Booking</title>
                    <jsp:include page="html_head.jspf" />
                    <script>
                        var carList = [];
                        var customerList = [];

                        $(document).ready(() => {
                            var mode = "${mode}";
                            if (mode == "New") {
                                $("#salesmanInCharge").val("${sessionScope.user.fullname}");
                                getCarDetails();
                                getCustomerDetails();

                                $("#carSelection").change(() => {
                                    selectCarDetails();
                                });

                            }
                        });

                        // AJAX to retrieve data from database
                        function getCarDetails() {
                            $.post("Booking_Car_List", function (response) {
                                var dataList = JSON.parse(response);
                                carList = dataList;
                                var htmlOutput = [];
                                let html = "";

                                Object.entries(dataList).forEach((obj) => {
                                    const [data, value] = obj;
                                    html = "<option value=" + value.carId + ">" + value.brand + " | " + value.model + " | " + value.colour + "</option>";
                                    htmlOutput.push(html);
                                });

                                $("#carSelection").append(htmlOutput.join(''));
                                // var htmlDetails = ;
                            })
                        }

                        // Set details for car selected
                        function selectCarDetails() {
                            var selectedData;

                            Object.entries(carList).forEach((obj) => {
                                const [data, value] = obj;

                                if (value.carId == $("#carSelection").val()) {
                                    selectedData = value;
                                    return;
                                }
                            });

                            let price = (selectedData.price == null || selectedData.price == "") ? 0 : parseFloat(selectedData.price);
                            let total = price + 15;

                            $("#selectedCar").val(selectedData.brand + " | " + selectedData.model);
                            $("#carColour").val(selectedData.colour);
                            $("#transmissionType").val(selectedData.transmissionType);
                            $("#carCapacity").val(selectedData.capacity);
                            $("#carImage").attr("src", selectedData.carImage);
                            $("#carPrice").val("RM " + selectedData.price.toFixed(2));
                            $("#totalPayable").val("RM " + total.toFixed(2))
                        }

                        function getCustomerDetails() {
                            $.post("Booking_Customer_List", function (response) {
                                var dataList = JSON.parse(response);
                                customerList = dataList;
                                var htmlOutput = [];
                                let html = "";

                                Object.entries(dataList).forEach((obj) => {
                                    const [data, value] = obj;
                                    html = "<option value=" + value.userId + ">" + value.fullname + "</option>";
                                    htmlOutput.push(html);
                                });

                                $("#customerSelection").append(htmlOutput.join(''));
                            })
                        }
                    </script>
                </head>

                <body>
                    <jsp:include page="_layout/header.jsp" />

                    <div class="container rounded bg-white mt-5 mb-5">
                        <div class="row">
                            <div class="col-md-3 border-right">
                                <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                                    <div class="profile-container">

                                        <img id="carImage" class="img-fluid img-responsive mt-5" alt="Car Image"
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
                                    <span class="font-weight-bold">${model.orderStatus} -
                                        ${model.salesDate}</span>

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

                                            <c:if test="${mode == 'New'}">
                                                <div class="col-md-12">
                                                    <label class="labels">Select Car</label>
                                                    <select class="form-select" name="carSelection" id="carSelection">
                                                        <option selected disabled>Select Car</option>
                                                    </select>
                                                </div>
                                            </c:if>

                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label class="labels">Selected Car</label>
                                                    <input type="text" class="form-control" id="selectedCar"
                                                        value="${model.car.brand} | ${model.car.model}" disabled />
                                                </div>

                                                <div class="col-md-6">
                                                    <label class="labels">Car Colour</label>
                                                    <input type="text" class="form-control" id="carColour"
                                                        value="${model.car.colour}" disabled />
                                                </div>
                                            </div>

                                            <div class="row mt-3">
                                                <div class="col-md-6">
                                                    <label class="labels">Car Seating Capacity</label>
                                                    <input type="text" class="form-control" id="carCapacity"
                                                        value="${model.car.capacity}" disabled />
                                                </div>

                                                <div class="col-md-6">
                                                    <label class="labels">Car Transmission Type</label>
                                                    <input type="text" class="form-control" id="transmissionType"
                                                        value="${model.car.transmissionType}" disabled />
                                                </div>
                                            </div>

                                            <div class="row mt-3">
                                                <div class="col-md-6">
                                                    <label class="labels">Total Payable</label>
                                                    <input type="text" class="form-control" id="totalPayable"
                                                        value="RM <fmt:formatNumber type='number' maxFractionDigits='2' value='${model.totalPayable}'/>"
                                                        disabled />
                                                </div>

                                                <div class="col-md-6">
                                                    <label class="labels">Car Price</label>
                                                    <input type="text" class="form-control" id="carPrice"
                                                        value="RM <fmt:formatNumber type='number' maxFractionDigits='2' value='${model.car.price}'/>"
                                                        disabled />
                                                </div>
                                                <small>Booking Fee of RM
                                                    <fmt:formatNumber type='number' maxFractionDigits='2'
                                                        value='${bookingFee}' /> is charged
                                                </small>
                                            </div>

                                            <div class="col-md-12 mt-3">
                                                <label class="labels">Salesman in Charge</label>
                                                <input id="salesmanInCharge" type="text" class="form-control"
                                                    value="<c:choose><c:when test='${model.salesman != null}'>${model.salesman.fullname}</c:when><c:otherwise>[Pending]</c:otherwise></c:choose>"
                                                    disabled />
                                            </div>

                                            <!-- Salesman Only -->
                                            <c:if test="${sessionScope.user.userType != 'Customer'}">

                                                <c:if test="${mode.equals('New')}">
                                                    <div class="col-md-12">
                                                        <label class="labels">Select Customer</label>
                                                        <select class="form-select" name="customerSelection"
                                                            id="customerSelection">
                                                            <option selected disabled>Select Customer</option>
                                                        </select>
                                                    </div>
                                                </c:if>

                                                <c:if test="${!mode.equals('New')}">
                                                    <div class="col-md-12 mt-3">
                                                        <label class="labels">Customer</label>
                                                        <input type="text" class="form-control"
                                                            value="${model.customer.fullname}" disabled />
                                                    </div>
                                                </c:if>

                                                <div class="col-md-12 mt-3">
                                                    <label class="labels">Salesman's Comments</label>
                                                    <textarea class="form-control" name="salesmanComments" rows="3"
                                                        <c:if
                                                        test="${mode == 'View'}">disabled</c:if>>${model.salesmanComments}</textarea>
                                                </div>
                                            </c:if>

                                            <!-- Customer Section -->
                                            <div class="row mt-3">
                                                <div class="col-md-6">
                                                    <label class="labels">Car Rating</label>

                                                    <input id="input-id" type="text" class="form-control rating"
                                                        data-min="0" data-max="5" data-step="0.5" data-size="sm"
                                                        data-show-clear="false" data-show-caption="false"
                                                        name="carRating" value="${model.carRating}" <c:if
                                                        test="${sessionScope.user.userType != 'Customer'}">disabled
                                                    </c:if>>
                                                </div>

                                                <div class="col-md-6">
                                                    <label class="labels">Service Rating</label>
                                                    <input id="input-id" type="text" class="form-control rating"
                                                        data-min="0" data-max="5" data-step="0.5" data-size="sm"
                                                        data-show-clear="false" data-show-caption="false"
                                                        name="salesmanRating" value="${model.salesmanRating}" <c:if
                                                        test="${sessionScope.user.userType != 'Customer'}">disabled
                                                    </c:if>>
                                                </div>
                                            </div>

                                            <div class="col-md-12 mt-3">
                                                <label class="labels">Review</label>
                                                <textarea class="form-control" rows="3" name="customerReview" <c:if
                                                    test="${sessionScope.user.userType != 'Customer'}">disabled</c:if>
                                              >${model.customerReview}</textarea>
                                            </div>

                                            <div class="mt-5 text-center">
                                                <!-- Back -->
                                                <a href="
                                                   <c:choose>
                                                       <c:when test='${sessionScope.user.userType == "Customer"}'>Customer_Booking</c:when>
                                                    <c:when test='${mode=="View"}'>Sls_Commission_Sales</c:when>
                                                    <c:otherwise>Sls_Manage_Sales</c:otherwise>
                                                    </c:choose>
                                                    " role="button"
                                                    class="btn btn-outline-secondary bi bi-arrow-left-circle me-2">
                                                    Back
                                                </a>

                                                <c:if test="${mode != 'View'}">
                                                    <button class="btn btn-primary profile-button" type="submit">Save
                                                        Details</button>
                                                </c:if>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                </body>

                </html>