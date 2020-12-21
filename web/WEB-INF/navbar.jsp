<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="/">التعلم عن بعد</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="/">الرئيسية</a>
                </li>
                <% if (request.getAttribute("isAdmin").equals(true)) { %>
                <li class="nav-item">
                    <a class="nav-link" href="/subjects">المقررات</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/teachers">الأساتذة</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/students">الطلبة</a>
                </li>
                <% } %>
            </ul>
            <div class="d-flex">
                <div class="nav-link"><%= session.getAttribute("auth.name")%></div>
                <form action="/logout" method="POST">
                    <button class="btn btn-outline-danger" type="submit">تسجيل الخروج</button>
                </form>
            </div>
        </div>
    </div>
</nav>