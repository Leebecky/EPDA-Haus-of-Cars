<%-- Document : login Created on : 02-Feb-2023, 10:59:03 Author : leebe --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Haus of Cars - Login</title>
            <jsp:include page="html_head.jspf" />

            <style>
                .forget-password {
                    color: black;
                    font-weight: bolder;
                }

                .forget-password:hover {
                    background-color: aliceblue;
                }

                #btnShowPassword:hover {
                    background-color: slategrey;
                }
            </style>

            <script>
                $(document).ready(() => {
                    $("#btnShowPassword").click(() => {
                        togglePassword();
                    })

                    $("#btnResetPassword").click(() => {
                        $("#frmResetPassword").submit();
                    })

                    $("#frmResetPassword").submit((e) => {
                        if (!validateResetPassword()) {
                            alert("Email must be provided!");
                            e.preventDefault();
                        }
                    })
                })

                function togglePassword() {
                    var passwordField = $("#passwordField");
                    var passwordIcon = $("#passwordIcon");

                    if (passwordField.attr("type") === "password") {
                        passwordField.attr("type", "text");

                        passwordIcon.addClass("bi-eye-slash-fill");
                        passwordIcon.removeClass("bi-eye-fill");

                    } else {
                        passwordField.attr("type", "password");
                        passwordIcon.removeClass("bi-eye-slash-fill");
                        passwordIcon.addClass("bi-eye-fill");
                    }
                }

                function validateResetPassword() {
                    var resetEmail = $("#resetEmail").val();

                    if (resetEmail == "" || resetEmail == null) {
                        return false;
                    }

                    return true;
                }
            </script>
        </head>

        <body
            style="background-image: url(images/login-background.jpg); background-repeat: no-repeat; background-size: cover;">
            <jsp:include page="_layout/header.jsp" />
            <jsp:include page="forgot_password.jsp" />

            <div class="container">
                <div class="row justify-content-center mt-5">
                    <div class="col-md-6 col-lg-4">
                        <div class="login-wrap p-0">
                            <h3 class="mb-4 text-center">Have an account?</h3>

                            <b class="text-danger text-center">${error}</b>

                            <form action="Login" method="POST">
                                <!-- Email -->
                                <div class="form-group mb-3">
                                    <input type="email" class="form-control" placeholder="Email" name="email" required>
                                </div>

                                <!-- Password -->
                                <div class="input-group mb-3">
                                    <input id="passwordField" type="password" class="form-control"
                                        placeholder="Password" name="password" required>
                                    <button id="btnShowPassword" class="input-group-text" type="button">
                                        <i id="passwordIcon" class="bi bi-eye-fill"></i></button>

                                </div>

                                <!-- Submit -->
                                <div class="form-group mt-5 mb-3">
                                    <button type="submit"
                                        class="form-control btn btn-primary submit px-3">Login</button>
                                </div>

                                <!-- Forgot Password -->
                                <p class="text-end">
                                    <a href="#" class="forget-password" data-bs-toggle="modal"
                                        data-bs-target="#forgotPasswordModal">Forgot Password?</a>
                                </p>

                                <!-- Registration Link -->
                                <p class="text-center my-5">
                                    <a href="register.jsp" class="forget-password">No Account? Sign up here!</a>
                                </p>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </body>

        </html>