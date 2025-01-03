<%--
  Created by IntelliJ IDEA.
  User: hp-alamincsme
  Date: 10/10/2024
  Time: 11:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="sec" uri="http://alamin.com/functions" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="<c:url value='/' />">e-Shoppers</a>
        <button class="navbar-toggler" type="button"
                data-bs-toggle="collapse" data-bs-target="#navbarResponsive"
                aria-controls="navbarResponsive"
                aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="<c:url value="/"/> "/>Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/"/> "/>About</a>
                </li>
                <c:choose>
                    <c:when test="${sec:isAuthenticated(pageContext.request)}">
                        <a class="nav-link" href="#" onclick="logout()">
                            Logout
                            [${sec:getCurrentUser(pageContext.request).firstName}]
                        </a>
                        <%--logout js--%>
                        <script>
                            function logout() {
                                document.getElementById("logoutForm").submit();
                            }
                        </script>
                        <form style="visibility: hidden" id="logoutForm" action="<c:url value="/logout"/>"
                              method="POST">
                        </form>

                    </c:when>
                    <c:otherwise>
                        <a class="nav-link" href="<c:out value="/login"/>" >Log In</a>
                    </c:otherwise>
                </c:choose>
                
            </ul>
        </div>
    </div>
</nav>
