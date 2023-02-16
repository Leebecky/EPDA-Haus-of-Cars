<%-- Document : manage_users Created on : 03-Feb-2023, 23:49:32 Author : leebe --%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html>

            <head>
                <jsp:include page="html_head.jspf" />
                <title>Haus of Cars - Admin - Manage Users</title>

                <script>

                    $(document).ready(function () {   
                        // Table
                        $("#userTable").fancyTable({
                            sortColumn: 0,
                            pagination: true,
                            perPage: 10,
                            globalSearch: true,
                            globalSearchExcludeColumns:[0]
                        });

                    });

                    // delete user
                    function deleteUserData(userId, email) {
                        let req = confirm("Are you sure you want to delete user: " + email + "?");
                        if (req) {
                            $.post("Admin_Delete_User", $.param({ "userId": userId }), function (response) {
                                rep = JSON.parse(response);
                                if (rep.msg == "Success") {
                                    alert("User successfully deleted");
                                    location.reload();                                    
                                } else {
                                    alert(rep.msg);
                                }
                            })
                        }
                    }    
                </script>

            </head>

            <body>
                <jsp:include page="_layout/header.jsp" />

                <!-- User Data Table -->
                <div class="mx-4">
                    <h1>User Management</h1>
                    <table id="userTable" class="table table-striped align-middle">
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

                            <c:forEach items="${requestScope.model}" var="data" varStatus="loop">
                                <tr>
                                    <td scope="row">
                                        <b><c:out value="${loop.index+1}" /></b>
                                    </td>
                                    <td>${data.fullname}</td>
                                    <td>${data.email}</td>
                                    <td>${data.userType}</td>
                                    <td>${data.status}</td>
                                    <td style="text-align: end;">
                                        <button type="button" class="btn btn-outline-warning bi bi-pencil-square me-2"
                                            style="font-size: 1.5rem;" onclick="editUserData('${data.userId}')">
                                            Edit</button>
                                        <button type="button" class="btn btn-outline-danger bi bi-trash3-fill"
                                            style="font-size: 1.5rem;"
                                            onclick="deleteUserData('${data.userId}', '${data.email}')"> Delete</button>
                                    </td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>
                </div>
            </body>

            </html>