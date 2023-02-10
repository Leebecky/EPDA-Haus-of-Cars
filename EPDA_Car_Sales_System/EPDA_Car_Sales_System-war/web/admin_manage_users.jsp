<%-- Document : manage_users Created on : 03-Feb-2023, 23:49:32 Author : leebe --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html>

        <head>
            <jsp:include page="html_head.jspf" />
            <title>Haus of Cars - Admin - Manage Users</title>
        </head>

        <body>
            <jsp:include page="_layout/header.jsp" />
            <h1>Manage Users!</h1>

            <!-- User Data Table -->
            <div class="mx-4">
                <table class="table table-striped align-middle">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Name</th>
                            <th scope="col">Email</th>
                            <th scope="col">User Role</th>
                            <th scope="col">Status</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <th scope="row">1</th>
                            <td>Somebody Once Told Me</td>
                            <td>Somebody@mail.com</td>
                            <td>Somebody</td>
                            <td>Active</td>
                            <td>
                                <button type="button" class="btn btn-outline-warning bi bi-pencil-square me-2"
                                    style="font-size: 1.5rem;"></button>
                                <button type="button" class="btn btn-outline-danger bi bi-trash3-fill"
                                    style="font-size: 1.5rem;"></button>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">2</th>
                            <td>Somebody</td>
                            <td>Somebody@mail.com</td>
                            <td>Somebody</td>
                            <td>Active</td>
                            <td>
                                <button type="button" class="btn btn-outline-warning bi bi-pencil-square me-2"
                                    style="font-size: 1.5rem;"></button>
                                <button type="button" class="btn btn-outline-danger bi bi-trash3-fill"
                                    style="font-size: 1.5rem;"></button>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">3</th>
                            <td>Somebody</td>
                            <td>Somebody@mail.com</td>
                            <td>Somebody</td>
                            <td>Active</td>
                            <td>
                                <button type="button" class="btn btn-outline-warning bi bi-pencil-square me-2"
                                    style="font-size: 1.5rem;"></button>
                                <button type="button" class="btn btn-outline-danger bi bi-trash3-fill"
                                    style="font-size: 1.5rem;"></button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </body>

        </html>