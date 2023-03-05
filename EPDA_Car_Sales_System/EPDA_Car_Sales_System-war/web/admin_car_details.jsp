<%-- Document : admin_edit_user Created on : 28-Feb-2023, 16:07:38 Author : leebe --%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html>

            <head>
                <jsp:include page="html_head.jspf" />
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Haus of Cars - Car Details</title>

                <script>
                    $(document).ready(() => {
                        $("#btnCarImage").click(() => {
                            updateCarImage();
                        });

                        // Set value for brand
                        var brand = "${model.brand}";

                        if (brand != "" && brand != null) {
                            $("#carBrand").val(brand);
                        }

                        // Set value for colour
                        var colour = "${model.colour}";

                        if (colour != "" && colour != null) {
                            $("#carColour").val(colour);
                        }

                        // Set value for transmission type
                        var transmissionType = "${model.transmissionType}";

                        if (transmissionType != "" && transmissionType != null) {
                            $("#carTransmissionType").val(transmissionType);
                        }
                    });

                    // Update Profile Image
                    function updateCarImage() {
                        $("#carImage").click();
                        $("#carImage").change(() => {
                            $("#frmCarImage").submit();
                        });
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

                                    <img class="profile-image mt-5" alt="User Profile Image"
                                        style="border-radius: 25px 0px 25px 0px;" src="
                                 <c:choose>
                                     <c:when test='${model.carImage != null && !model.carImage.isEmpty()}'>
                                    ${model.carImage}
                                    </c:when>
                                    <c:otherwise>
                                        images/car-placeholder.avif
                                    </c:otherwise>
                                    </c:choose>">

                                    <c:if test="${mode != 'New'}">
                                        <button id="btnCarImage" class="profile-overlay mt-5"
                                            style="border-radius: 25px 0px 25px 0px;" type="button">
                                            <div class="profile-overlay-text">Update Image?</div>
                                        </button>
                                    </c:if>
                                </div>
                                <span class="font-weight-bold">${model.status}</span>

                                <form id="frmCarImage" action="Admin_Car_Details" method="post"
                                    enctype="multipart/form-data">
                                    <input type="hidden" name="carUpdateType" value="imageUpdate">
                                       <input type="hidden" name="id" value="${id}">
                                    <input type="hidden" name="mode" value="${mode}">
                                    <input type="file" id="carImage" name="carImage" class="d-none"
                                        accept="image/*">
                                </form>
                            </div>
                        </div>


                        <div class="col-md-9 border-right">
                            <div class="p-3 py-5">
                                <div class="d-flex justify-content-between align-items-center mb-3">
                                    <h4 class="text-right">Car Details</h4>
                                </div>

                                <div class="row mt-3">

                                    <form id="frmCarDetails" action="Admin_Car_Details" method="post"
                                        enctype="multipart/form-data">

                                        <!-- Hidden Fields -->
                                        <input type="hidden" name="id" value="${id}">
                                        <input type="hidden" name="mode" value="${mode}">
                                        <input type="hidden" name="carUpdateType" value="detailsUpdate">


                                        <c:if test="${mode == 'New'}">
                                            <label class="labels">Car Image</label>
                                            <input type="file" class="form-control mb-3" id="carImageNew"
                                                name="carImage" accept="image/jpeg, image/jpg, image/png">
                                        </c:if>

                                        <div class="col-md-12">
                                            <label class="labels">Brand</label>
                                            <input type="text" class="form-select" id="carBrand" name="brand"
                                                list="car-brand" required />
                                            <datalist id="car-brand">
                                                <option value="Toyota">Toyota</option>
                                                <option value="Honda">Honda</option>
                                                <option value="Perodua">Perodua</option>
                                            </datalist>
                                        </div>

                                        <div class="col-md-12 mt-3">
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
                                        </div>

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


                                        <div class="col-md-12 mt-3">
                                            <label class="labels">Price</label>
                                            <input type="number" min="1" step="0.01" class="form-control" name="price"
                                                placeholder="Price" value="${model.price}" required>
                                        </div>
                                        <div class="col-md-12 mt-3">
                                            <label class="labels">Capacity</label>
                                            <input type="number" min="2" step="1" name="capacity" class="form-control"
                                                placeholder="Capacity" value="${model.capacity}" required>
                                        </div>



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