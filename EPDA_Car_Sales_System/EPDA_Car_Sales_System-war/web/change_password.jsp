<!-- Modal -->
<div class="modal fade" id="changePasswordModal" tabindex="-1" aria-labelledby="changePasswordModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="changePasswordModalLabel">Change Password</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="Change_Password" method="POST" id="frmChangePassword" autocomplete="off">
                    <input type="hidden" name="userId" value="${id}">

                    <!-- Password -->
                    <div class="input-group mb-3">
                        <input id="passwordField" type="password" name="password" class="form-control" placeholder="Password"
                               required>
                        <button id="btnShowPassword" class="input-group-text" type="button">
                            <i id="passwordIcon" class="bi bi-eye-fill"></i>
                        </button>
                    </div>

                    <!-- Confirm Password -->
                    <div class="input-group mb-3">
                        <input id="confirmPasswordField" type="password" class="form-control" placeholder="Confirm Password"
                               required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button id="btnChangePassword" type="submit" class="btn btn-primary" form="frmChangePassword">Change Password</button>
            </div>
        </div>
    </div>
</div>