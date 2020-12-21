<%@page import="Main.Helpers.DataAccess"%>
<%@page import="java.util.List"%>
<%@page import="Main.Models.User"%>
<%@page import="Main.Models.Subject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container mt-4">
    <div class="row mt-4">
        <%
            List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
            for (Subject subject : subjects) {
        %>
        <div class="col-xl-3 col-md-4 col-sm-6 mt-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title"><%= subject.getName()%></h5>
                    <h6 class="card-subtitle mb-2 text-muted">
                        <%= subject.getTeacher() == null ? "< لا يوجد أستاذ >" : subject.getTeacher() %>
                    </h6>
                    <a href="/subject?id=<%= subject.getId()%>" class="card-link">الدخول للمقرر</a>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>