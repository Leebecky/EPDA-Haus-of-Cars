<%-- Document : manage_users Created on : 03-Feb-2023, 23:49:32 Author : leebe --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <jsp:include page="html_head.jspf" />
        <title>Haus of Cars - Admin - Manage Cars</title>

        <script>

            $(document).ready(function () {
                // Table
                $("#carTable").fancyTable({
                    sortColumn: 0,
                    pagination: true,
                    perPage: 10,
                    globalSearch: true,
                    globalSearchExcludeColumns: [1, 8]
                });

            });


            // delete user
            function deleteCarData(carId) {
                let req = confirm("Are you sure you want to delete car: " + carId + "?");
                if (req) {
                    $("#frmDeleteCar").submit();                  
                }
            }
        </script>

    </head>

    <body>
        <jsp:include page="_layout/header.jsp" />

        <!-- User Data Table -->
        <div class="mx-4">
            <div class="d-flex justify-content-between align-items-center">
                <h1 class="">Car Management</h1>

                <a href="Admin_Car_Details?mode=New" role="button"
                   class="btn btn-outline-primary my-2 bi bi-plus-circle" style="font-size: 1.5rem;"> New</a>
            </div>

            <table id="carTable" class="table table-striped align-middle">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Car Id</th>
                        <th scope="col">Brand</th>
                        <th scope="col">Model</th>
                        <th scope="col">Colour</th>
                        <th scope="col">Price (RM)</th>
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
                            <td>${data.carId}</td>
                            <td>${data.brand}</td>
                            <td>${data.model}</td>
                            <td>${data.colour}</td>
                            <td>
                                <fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${data.price}" />
                            </td>
                            <td>${data.status}</td>
                            <td style="text-align: end;">

                                <!-- Edit -->
                                <a href="Admin_Car_Details?mode=Edit&id=${data.carId}" role="button"
                                   class="btn btn-outline-warning bi bi-pencil-square me-2"
                                   style="font-size: 1.5rem;"> Edit</a>

                                <!-- Delete -->
                                <form id="frmDeleteCar" action="Admin_Car_Delete" method="post" class="btn">
                                    <input type="hidden" name="carId" value="${data.carId}">
                                    <button type="button" class="btn btn-outline-danger bi bi-trash3-fill"
                                            style="font-size: 1.5rem;" onclick="deleteCarData('${data.carId}')">
                                        Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>
        </div>
    </body>

</html>