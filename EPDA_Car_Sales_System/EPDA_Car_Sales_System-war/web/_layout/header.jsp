<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-sm" style="background-color: #263159;">
    <a class="navbar-brand" href="home.jsp">
        <img src="images/car-logo-rectangle-white.png" alt="Haus of Cars" width="100">
    </a>
    <button class="navbar-toggler d-lg-none" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavId"
            aria-controls="collapsibleNavId" aria-expanded="false" aria-label="Toggle navigation"></button>
    <div class="collapse navbar-collapse" id="collapsibleNavId">
        <ul class="navbar-nav me-auto mt-2 mt-lg-0">
            <li class="nav-item">
                <a class="nav-link text-light fs-5" href="home.jsp" aria-current="page">Home</a>
            </li>
        </ul>

        <!-- IF User is NOT Logged In -->
        <c:if test = "${sessionScope.user == null}">
            <div class="d-flex my-2 my-lg-0 mx-2"><a href="login.jsp">
                    <i class="bi bi-person-circle" style="font-size: 2rem; color: white;"></i>
                </a>
            </div>
        </c:if>

        <!-- IF User is Logged In -->
        <c:if test = "${sessionScope.user != null}">
            <div class="nav-item dropstart d-flex mx-2">
                <a class="nav-link dropdown-toggle" style="color:white;" href="#" id="dropdownId" data-bs-toggle="dropdown" aria-haspopup="true"
                   aria-expanded="false">
                    <span><c:out value="${sessionScope.user.username}"/></span>
                    <i class="bi bi-person-circle" style="font-size: 2rem; color: white;"></i>
                </a>
                <div class="dropdown-menu" aria-labelledby="dropdownId">
                    <a class="dropdown-item" href="home.jsp">User Profile</a>
                    <a class="dropdown-item" href="Logout">Logout</a>
                </div>
            </div>
        </c:if>

    </div>
</nav>