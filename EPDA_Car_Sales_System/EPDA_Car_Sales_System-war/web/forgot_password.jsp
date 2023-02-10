<!-- Modal -->
<div class="modal fade" id="forgotPasswordModal" tabindex="-1" aria-labelledby="forgotPasswordModalLabel"
  aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="forgotPasswordModalLabel">Reset Password?</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form action="ResetPassword" method="POST" id="frmResetPassword">
          <div class="mb-3">
            <label for="" class="form-label">Email</label>
            <input type="email" class="form-control" id="resetEmail" name="resetEmail" aria-describedby="emailHelpId"
              placeholder="Email">
            <small id="emailHelpId" class="form-text text-muted">${requestScope.resetResponse}</small>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        <button id="btnResetPassword" type="button" class="btn btn-primary" form="frmResetPassword">Submit</button>
      </div>
    </div>
  </div>
</div>