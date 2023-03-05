function customAlert(msg, alertType) {
    // Close open alerts first
    closeAlerts();

    // Initialise new alert
    var alertPlaceholder = $("#alertPlaceholder")
    var alertColour = (alertType == "error") ? "alert-danger" : "alert-success";

    const wrapper = document.createElement('div')
    wrapper.innerHTML = [
        `<div class="alert ${alertColour} d-flex justify-content-between " role="alert">`,
        `${msg}`,
        `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`
    ].join('')

    alertPlaceholder.append(wrapper)
    window.scrollTo(0,0)
}

function closeAlerts() {
    const alertList = document.querySelectorAll('.alert')
    const alerts = [...alertList].map(element => new bootstrap.Alert(element).close())
}