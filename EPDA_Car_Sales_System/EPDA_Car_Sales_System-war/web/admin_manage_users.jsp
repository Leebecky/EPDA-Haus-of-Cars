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
                            globalSearchExcludeColumns: [1, 6],
                            globalSearch: true,
                        });

                    });

                    // approve user
                    // function approveUser(userId) {
                    //     //                let req = confirm("Are you sure you want to delete user: " + email + "?");
                    //     //                if (req) {
                    //     $.post("Admin_Approve_User", $.param({ "userId": userId }), function (response) {
                    //         rep = JSON.parse(response);
                    //         if (rep.msg == "Success") {
                    //             customAlert("User successfully approved", "msg");
                    //             $("#userTable").load("Admin_Manage_Users #userTable");
                    //         } else {
                    //             customAlert(rep.msg, "error");
                    //         }
                    //     })
                    //     //                }
                    // }

                    // delete user
                    function deleteUserData(email,userId) {
                        let req = confirm("Are you sure you want to delete user: " + email + "?");
                        if (req) {
                            $("#frmDeleteUser-"+userId).submit();
                            // $.post("Admin_Delete_User", $.param({ "userId": userId }), function (response) {
                            //     rep = JSON.parse(response);
                            //     if (rep.msg == "Success") {
                            //         customAlert("User successfully deleted", "msg");
                            //         location.reload();
                            //     } else {
                            //         customAlert(rep.msg, "error");
                            //     }
                            // })
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
                            <h1 class="">User Management</h1>

                            <a href="User_Profile?id=-1&mode=New" role="button"
                                class="btn btn-outline-primary my-2 bi bi-person-fill-add"
                                style="font-size: 1.5rem;"> New</a>
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
                                            <b>
                                                <c:out value="${loop.index+1}" />
                                            </b>
                                        </td>
                                        <td>${data.fullname}</td>
                                        <td>${data.email}</td>
                                        <td>${data.userType}</td>
                                        <td>${data.status}</td>
                                        <td style="text-align: end;">

                                            <c:if test="${data.status != 'Approved'}">
                                                <!-- Approve -->
                                                <form id="frmApproveUser-${data.userId}" action="Admin_Approve_User" method="post" class="btn">
                                                    <input type="hidden" name="approveUserId" value="${data.userId}">
                                                    <button type="submit" class="btn btn-outline-success bi bi-check-lg"
                                                        style="font-size: 1.5rem;" form="frmApproveUser-${data.userId}">
                                                        Approve</button>
                                                </form>
                                            </c:if>

                                            <!-- Edit -->
                                            <a href="User_Profile?id=${data.userId}&mode=Edit" role="button"
                                                class="btn btn-outline-warning bi bi-pencil-square me-2"
                                                style="font-size: 1.5rem;"> Edit</a>

                                            <!-- Delete -->
                                            <c:if test="${data.userId != user.userId}">
                                                <form id="frmDeleteUser-${data.userId}" action="Admin_Delete_User" method="post" class="btn">
                                                    <input type="hidden" name="userId" value="${data.userId}">
                                                    <button type="button"
                                                        class="btn btn-outline-danger bi bi-trash3-fill"
                                                        style="font-size: 1.5rem;"
                                                        onclick="deleteUserData('${data.email}', '${data.userId}')" form="frmDeleteUser">
                                                        Delete</button>
                                                </form>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>
                    </div>
            </body>

            </html>