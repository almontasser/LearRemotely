<%@page import="Main.Helpers.DataAccess"%>
<%@page import="java.util.List"%>
<%@page import="Main.Models.User"%>
<%@page import="Main.Models.Subject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container mt-4">
    <div class="d-flex">
        <button type="button" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#join-class">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
            </svg>
            <span>الإنضمام لمقرر</span>
        </button>

        <!-- Modal -->
        <div class="modal fade" id="join-class" tabindex="-1" aria-labelledby="joinClassLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="joinClassLabel">الإنضمام لمقرر</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="/join-subject" method="POST">
                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="password" class="form-label">رمز المقرر</label>
                                <input type="text" class="form-control" id="password" name="password">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">الإنضمام</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="row mt-4">
        <%
            List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
            for (Subject subject : subjects) {
        %>
        <div class="col-xl-3 col-lg-4 col-sm-6 mt-4">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title"><%= subject.getName()%></h5>
                    <h6 class="card-subtitle mb-2 text-muted">
                        <%= subject.getTeacher() == null ? "< لا يوجد أستاذ >" : subject.getTeacher()%>
                    </h6>
                </div>
                <div class="card-footer">
                    <% if (subject.getStatus() == 1) {%>
                    <a type="button" class="btn btn-outline-primary btn-sm" href="/subject?id=<%= subject.getId()%>">الدخول للمقرر</a>
                    <% } else { %>
                    <a type="button" class="btn btn-outline-secondary btn-sm disabled">الدخول للمقرر</a>
                    <small class="text-muted">لم تتم الموافقة</small>
                    <% } %>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>