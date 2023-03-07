<%-- Document : admin_edit_user Created on : 28-Feb-2023, 16:07:38 Author : leebe --%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html>

            <head>
                <jsp:include page="html_head.jspf" />
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Haus of Cars - User Profile</title>

                <style>
                    #btnShowPassword:hover {
                        background-color: slategrey;
                    }
                </style>

                <script>
                    $(document).ready(() => {
                        // Configuring display
                        var userRole = "${model.userType}";
                        if (userRole == "Customer") {
                            toggleCustomerSection(true);
                        }

                        // Set value for gender
                        var userGender = "${model.gender}";

                        if (userGender != "" && userGender != null) {
                            $("#gender").val(userGender);
                        }

                        // Applying triggers
                        $("#userRole").change(() => {
                            if ($("#userRole").val() == "Customer") {
                                toggleCustomerSection(true);
                            } else {
                                toggleCustomerSection(false);
                            }
                        });

                        $("#btnShowPassword").click(() => {
                            togglePassword();
                        });

                        $("#btnProfile").click(() => {
                            updateProfileImage();
                        });

                        $('#frmChangePassword').submit((e) => {
                            if (!verifyPasswordData()) {
                                e.preventDefault();
                            }
                        });

                        $('#frmUserProfile').submit((e) => {
                            if (!verifyData()) {
                                e.preventDefault();
                            }
                        });
                    });

                    // Show/Hide Password
                    function togglePassword() {
                        var passwordField = $("#passwordField");
                        var passwordIcon = $("#passwordIcon");

                        if (passwordField.attr("type") == "password") {
                            passwordField.attr("type", "text");

                            passwordIcon.addClass("bi-eye-slash-fill");
                            passwordIcon.removeClass("bi-eye-fill");

                        } else {
                            passwordField.attr("type", "password");
                            passwordIcon.removeClass("bi-eye-slash-fill");
                            passwordIcon.addClass("bi-eye-fill");
                        }
                    }

                    // Verify Password Data
                    function verifyPasswordData() {
                        var password = $("#passwordField");
                        var confirmPass = $("#confirmPasswordField");

                        if (password.val().length < 8) {
                            customAlert("Password should be at least 8 characters!", "error");
                            return false;
                        }

                        if (password.val() !== confirmPass.val()) {
                            customAlert("Passwords do not match!", "error");
                            return false;
                        }
                        return true;
                    }

                    // Verify Data
                    function verifyData() {
                        if ($("#userRole").val() == "Select User Role") {
                            customAlert("Please select a user role!", "error");
                            return false;
                        }

                        var dob = new Date($("#dob").val()).getFullYear();
                        var currentYear = new Date().getFullYear();
                        if (currentYear - dob < 21) {
                            customAlert("User cannot be younger than 21!", "error");
                            return false;
                        }

                        return true;
                    }

                    // Update Profile Image
                    function updateProfileImage() {
                        $("#profileImage").click();
                        $("#profileImage").change(() => {
                            $("#frmProfileImage").submit();
                        })
                    }

                    // Toggle Display for Customer Section 
                    function toggleCustomerSection(show) {
                        if (show) {
                            $("#icDiv").removeClass("d-none");
                            $("#licenseDiv").removeClass("d-none");
                        } else {
                            $("#icDiv").addClass("d-none");
                            $("#licenseDiv").addClass("d-none");
                        }
                    }
                </script>
            </head>

            <body>
                <jsp:include page="_layout/header.jsp" />
                <jsp:include page="change_password.jsp" />


                <!-- Profile -->
                <div class="container rounded bg-white mt-5 mb-5">
                    <div class="row">
                        <div class="col-md-3 border-right">
                            <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                                <div class="profile-container">
                                    <img class="profile-image rounded-circle mt-5" alt="User Profile Image" src="
                                 <c:choose>
                                     <c:when test=" ${model.profileImage !=null && !model.profileImage.isEmpty()}">
                                    ${model.profileImage}
                                    </c:when>
                                    <c:otherwise>
                                        images/user-icon-placeholder.png
                                    </c:otherwise>
                                    </c:choose>">

                                    <c:if test="${mode != 'New'}">
                                        <button id="btnProfile" class="profile-overlay rounded-circle mt-5"
                                            type="button">
                                            <div class="profile-overlay-text">Update Image?</div>
                                        </button>
                                    </c:if>
                                </div>
                                <span class="font-weight-bold">${model.username}</span>
                                <span class="text-black-50">${requestScope.model.email}</span>

                                <c:if test="${mode == 'Edit'}">
                                    <span><button class="btn btn-secondary mt-3" type="button" data-bs-toggle="modal"
                                            data-bs-target="#changePasswordModal">Change Password</button></span>
                                </c:if>

                                <form id="frmProfileImage" action="User_Profile" method="post"
                                    enctype="multipart/form-data">
                                    <input type="hidden" name="profileUpdateType" value="imageUpdate">
                                    <input type="hidden" name="id" value="${id}">
                                    <input type="hidden" name="mode" value="${mode}">
                                    <input type="file" id="profileImage" name="profileImage" class="d-none"
                                        accept="image/jpeg, image/jpg,image/png">
                                </form>
                            </div>
                        </div>


                        <div class="col-md-9 border-right">
                            <div class="p-3 py-5">
                                <div class="d-flex justify-content-between align-items-center mb-3">
                                    <h4 class="text-right">Profile</h4>
                                </div>

                                <div class="row mt-3">

                                    <form id="frmUserProfile" action="User_Profile" method="post"
                                        enctype="multipart/form-data">

                                        <!-- Hidden Fields -->
                                        <input type="hidden" name="id" value="${id}">
                                        <input type="hidden" name="mode" value="${mode}">
                                        <input type="hidden" name="profileUpdateType" value="profileUpdate">

                                        <c:if test="${sessionScope.user.userType == 'Admin' && mode == 'New'}">
                                            <!-- Select User Role -->
                                            <div class="col-md-12 mb-3">
                                                <select class="form-select" id="userRole" name="userRole"
                                                    aria-label="Select User Role" required>
                                                    <option selected disabled>Select User Role</option>
                                                    <option value="Customer">Customer</option>
                                                    <option value="Salesman">Salesman</option>
                                                    <option value="Admin">Admin</option>
                                                </select>
                                            </div>

                                            <div class="col-md-12 mb-3">
                                                <label class="labels">Email</label>
                                                <input type="text" class="form-control" placeholder="Email" name="email"
                                                    value="" required>
                                            </div>
                                        </c:if>

                                        <c:if test="${sessionScope.user.userType == 'Admin' && mode == 'Edit'}">
                                            <!-- Select User Role -->
                                            <div class="col-md-12 mb-3">
                                                <label class="labels">User Role</label>
                                                <input type="text" class="form-control bg-dark-subtle" name="userRole"
                                                    value="${model.userType}" readonly>
                                            </div>
                                        </c:if>

                                        <c:if test="${sessionScope.user.userType != 'Admin'}">
                                            <input type="hidden" name="userRole"
                                                value="${model.userType}" readonly>
                                        </c:if>

                                        <div class="col-md-12">
                                            <label class="labels">Full Name</label>
                                            <input type="text" class="form-control" placeholder="Full name"
                                                name="fullname" value="${model.fullname}" required>
                                        </div>

                                        <div class="col-md-12 mt-3">
                                            <label class="labels">Username</label>
                                            <input type="text" class="form-control" placeholder="Username"
                                                name="username" value="${model.username}" required>
                                        </div>

                                        <div class="col-md-12 mt-3">
                                            <label class="labels">IC Number</label>
                                            <input type="text" class="form-control" placeholder="IC Number" name="icNo"
                                                value="${model.icNo}" required>
                                        </div>

                                        <div class="col-md-12 mt-3">
                                            <label class="labels">Gender</label>
                                            <select class="form-select" id="gender" name="gender" required>
                                                <option value="Male">Male</option>
                                                <option value="Female">Female</option>
                                            </select>
                                        </div>

                                        <div class="col-md-12 mt-3">
                                            <label class="labels">Birthday</label>
                                            <input id="dob" type="date" class="form-control" name="dob" required
                                                value="${model.dob}">
                                        </div>

                                        <div class="col-md-12 mt-3">
                                            <label class="labels">Mobile Number</label>
                                            <input type="text" class="form-control" name="telNo"
                                                placeholder="Phone number" value="${model.telNo}" required>
                                        </div>
                                        <div class="col-md-12 mt-3">
                                            <label class="labels">Address</label>
                                            <input type="text" name="address" class="form-control" placeholder="Address"
                                                value="${model.address}" required>
                                        </div>

                                        <!-- Customer Only Details -->
                                        <%--<c:if test="${model.userType == 'Customer'}">--%>

                                            <div id="icDiv" class="col-md-12 mt-3 d-none">
                                                <label class="labels">Upload IC</label>
                                                <br>
                                                <c:if
                                                    test="${model.userType == 'Customer' && (model.icImage != null && !model.icImage.isEmpty())}">
                                                    <a class="link-primary mb-3" href="${model.icImage}"
                                                        target="_blank">IC Image</a>
                                                </c:if>

                                                <input type="file" name="icImage" class="form-control"
                                                    value="<c:if test=" ${model.userType=='Customer' }">${model.icImage}
                                                </c:if>">
                                            </div>
                                            <div id="licenseDiv" class="col-md-12 mt-3 d-none">
                                                <label class="labels">Upload Driving License</label>
                                                <br>
                                                <c:if
                                                    test="${model.userType == 'Customer' && (model.drivingLicense != null &&!model.drivingLicense.isEmpty())}">
                                                    <a class="link-primary mb-3" href="${model.drivingLicense}"
                                                        target="_blank">Driving License</a>
                                                </c:if>

                                                <input type="file" name="drivingLicense" class="form-control"
                                                    value="<c:if test=" ${model.userType=='Customer'
                                                    }">${model.drivingLicense}</c:if>">
                                            </div>
                                            <%--< /c:if>--%>

                                                <div class="mt-5 text-center">
                                                    <!-- Back -->
                                                    <c:if test="${sessionScope.user.userType == 'Admin'}">
                                                        <a href="Admin_Manage_Users" role="button"
                                                            class="btn btn-outline-secondary bi bi-arrow-left-circle me-2">
                                                            Back</a>
                                                    </c:if>

                                                    <button class="btn btn-primary profile-button" type="submit">Save
                                                        Profile</button>

                                                </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
            </body>

            </html>