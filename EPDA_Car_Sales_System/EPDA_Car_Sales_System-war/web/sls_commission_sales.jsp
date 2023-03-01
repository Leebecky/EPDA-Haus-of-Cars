<%-- Document : manage_users Created on : 03-Feb-2023, 23:49:32 Author : leebe --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <title>Haus of Cars - Admin - Manage Users</title>
        <jsp:include page="html_head.jspf" />

        <script>

            $(document).ready(function () {
                // Table
                $("#userTable").fancyTable({
                    sortColumn: 0,
                    pagination: true,
                    perPage: 10,
                    globalSearch: true,
                    globalSearchExcludeColumns: [0]
                });

            });
            
            // approve user
            function approveUser(userId) {
//                let req = confirm("Are you sure you want to delete user: " + email + "?");
//                if (req) {
                    $.post("Admin_Approve_User", $.param({"userId": userId}), function (response) {
                        rep = JSON.parse(response);
                        if (rep.msg == "Success") {
                            alert("User successfully approved");
                            location.reload();
                        } else {
                            alert(rep.msg);
                        }
                    })
//                }
            }

            // delete user
            function deleteUserData(userId, email) {
                let req = confirm("Are you sure you want to delete user: " + email + "?");
                if (req) {
                    $.post("Admin_Delete_User", $.param({"userId": userId}), function (response) {
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
            
            <div class="mx-4">
                <div class="d-flex justify-content-between align-items-center">
                    <h1 class="">Salesman Sales Commission</h1>

                    <a href="User_Profile?mode=New" role="button"
                        class="btn btn-outline-primary my-2 bi bi-pencil-square" style="font-size: 1.5rem;">New</a>
                </div>

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

                                <c:if test="${data.status != 'Approved'}">
                                    <!-- Approve -->
                                    <button type="button" class="btn btn-outline-success bi bi-check-lg"
                                            style="font-size: 1.5rem;"
                                            onclick="approveUser('${data.userId}')"> Approve</button>
                                </c:if>

                                <!-- Edit -->
                                <a href="User_Profile?id='${data.userId}'" role="button" 
                                   class="btn btn-outline-warning bi bi-pencil-square me-2"
                                   style="font-size: 1.5rem;">Edit</a>

                                <!-- Delete -->
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