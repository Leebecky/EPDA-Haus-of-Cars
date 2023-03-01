<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-sm" style="background-color: #263159;">
    <a class="navbar-brand" href="Home">
        <img src="images/car-logo-rectangle-white.png" alt="Haus of Cars" width="100">
    </a>
    <button class="navbar-toggler d-lg-none" type="button" data-bs-toggle="collapse"
            data-bs-target="#collapsibleNavId" aria-controls="collapsibleNavId" aria-expanded="false"
            aria-label="Toggle navigation"></button>
    <div class="collapse navbar-collapse" id="collapsibleNavId">

        <!-- Configuring Menu based on Login -->
        <c:choose>

            <%-- IF User is NOT Logged In --%>
            <c:when test="${sessionScope.user == null}">
                <ul class="navbar-nav me-auto mt-2 mt-lg-0">
                    <li class="nav-item">
                        <a class="nav-link text-light fs-5" href="Home" aria-current="page">Home</a>
                    </li>
                </ul>

                <div class="d-flex my-2 my-lg-0 mx-2"><a href="Login">
                        <i class="bi bi-person-circle" style="font-size: 2rem; color: white;"></i>
                    </a>
                </div>
            </c:when>

            <%-- IF User is Logged In --%>
            <c:when test="${sessionScope.user != null}">

                <%-- Admin Menu --%>
                <c:if test="${sessionScope.user.userType == 'Admin'}">

                    <ul class="navbar-nav me-auto mt-2 mt-lg-0">
                        <li class="nav-item">
                            <a class="nav-link text-light fs-5" href="Admin_Home" aria-current="page">Dashboard</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link text-light fs-5" href="Admin_Manage_Users" aria-current="page">User Management</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link text-light fs-5" href="Admin_Manage_Cars" aria-current="page">Car Management</a>
                        </li>
                    </ul>
                </c:if>

                <%-- Salesman Menu --%>
                <c:if test="${sessionScope.user.userType == 'Salesman'}">

                    <ul class="navbar-nav me-auto mt-2 mt-lg-0">
                        <li class="nav-item">
                            <a class="nav-link text-light fs-5" href="Home" aria-current="page">Dashboard</a>
                        </li>                        
                  
                        <li class="nav-item">
                            <a class="nav-link text-light fs-5" href="Sales" aria-current="page">Sales</a>
                        </li>                        
                    </ul>
                </c:if>

                <%-- Customer Menu --%>
                <c:if test="${sessionScope.user.userType == 'Customer'}">

                    <ul class="navbar-nav me-auto mt-2 mt-lg-0">
                        <li class="nav-item">
                            <a class="nav-link text-light fs-5" href="Home" aria-current="page">Catalogue</a>
                        </li>
                        
                    </ul>
                </c:if>

                <div class="nav-item dropstart d-flex mx-2">
                    <a class="nav-link dropdown-toggle" style="color:white;" href="#" id="dropdownId"
                       data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span>
                            <c:out value="${sessionScope.user.username}" />
                        </span>
                        <i class="bi bi-person-circle" style="font-size: 2rem; color: white;"></i>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownId">
                        <a class="dropdown-item" href="User_Profile?id='${sessionScope.user.userId}'">User
                            Profile</a>
                        <a class="dropdown-item" href="Logout">Logout</a>
                    </div>
                </div>
            </c:when>

        </c:choose>

    </div>
</nav>