<%-- Document : login Created on : 02-Feb-2023, 10:59:03 Author : leebe --%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html>

            <head>
                <title>Haus of Cars - Register</title>
                <jsp:include page="html_head.jspf" />

                <style>
                    #btnShowPassword:hover {
                        background-color: slategrey;
                    }
                </style>

                <script>
                    $(document).ready(() => {
                        $("#btnShowPassword").click(() => {
                            togglePassword()
                        })

                        $('#frmRegister').submit((e) => {
                            if (!verifyData()) {
                                e.preventDefault();
                            }
                        })
                    })

                    // Show/Hide Password
                    function togglePassword() {
                        var passwordField = $("#passwordField");
                        var passwordIcon = $("#passwordIcon");

                        if (passwordField.attr("type") == "password") {
                            passwordField.attr("type", "text")

                            passwordIcon.addClass("bi-eye-slash-fill");
                            passwordIcon.removeClass("bi-eye-fill");

                        } else {
                            passwordField.attr("type", "password")
                            passwordIcon.removeClass("bi-eye-slash-fill");
                            passwordIcon.addClass("bi-eye-fill");
                        }
                    }

                    // Verify Data
                    function verifyData() {
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

                        if ($("#userRole").val() == "Select User Role") {
                            customAlert("Please select a user role!", "error");
                            return false;
                        }

                        return true;
                    }
                </script>
            </head>

            <body
                style="background-image: url(images/login-background.jpg); background-repeat: no-repeat; background-size: cover;">
                <jsp:include page="_layout/header.jsp" />

                <div class="container">
                    <div class="row justify-content-center mt-5">
                        <div class="col-md-6 col-lg-4">
                            <div class="login-wrap p-0">
                                <h3 class="mb-4 text-center">Welcome to the Haus of Cars!</h3>

                                <form id="frmRegister" action="Register" method="post" autocomplete="off">
                                    <!-- Username -->
                                    <div class="form-group mb-3">
                                        <input type="text" class="form-control" placeholder="Username" name="username" required autocomplete="off">
                                    </div>

                                    <!-- Email -->
                                    <div class="form-group mb-3">
                                        <input type="email" class="form-control" placeholder="Email" name="email"
                                            required>
                                    </div>

                                    <!-- Password -->
                                    <div class="input-group mb-3">
                                        <input id="passwordField" type="password" name="password" class="form-control"
                                            placeholder="Password" required>
                                        <button id="btnShowPassword" class="input-group-text" type="button">
                                            <i id="passwordIcon" class="bi bi-eye-fill"></i>
                                        </button>
                                    </div>

                                    <!-- Confirm Password -->
                                    <div class="input-group mb-3">
                                        <input id="confirmPasswordField" type="password" class="form-control"
                                            placeholder="Confirm Password" required>
                                    </div>

                                    <!-- Select User Role -->
                                    <div class="input-group mb-3">
                                        <select class="form-select" id="userRole" name="userRole"
                                            aria-label="Select User Role" required>
                                            <option selected>Select User Role</option>
                                            <option value="Customer">Customer</option>
                                            <option value="Salesman">Salesman</option>
                                        </select>
                                    </div>

                                    <!-- Submit -->
                                    <div class="form-group mt-5 mb-3">
                                        <button id="btnSubmit" form="frmRegister" type="submit"
                                            class="form-control btn btn-primary submit px-3">Register</button>
                                    </div>

                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </body>

            </html>