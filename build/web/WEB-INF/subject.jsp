<%@page import="Main.Models.Comment"%>
<%@page import="Main.Models.Post"%>
<%@page import="java.util.List"%>
<%@page import="Main.Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container mt-4">
    <h4 class="">${title}</h4>
    <ul class="nav nav-tabs mt-4" id="myTab" role="tablist">
        <li class="nav-item" role="presentation">
            <a class="nav-link active" id="posts-tab" data-bs-toggle="tab" href="#posts" role="tab" aria-controls="posts" aria-selected="true">المقالات</a>
        </li>
        <% if (request.getAttribute("isTeacher").equals(true) || request.getAttribute("isAdmin").equals(true)) { %>
        <li class="nav-item" role="presentation">
            <a class="nav-link" id="students-tab" data-bs-toggle="tab" href="#students" role="tab" aria-controls="students" aria-selected="false">الطلبة</a>
        </li>
        <% }%>
    </ul>
    <div class="tab-content mt-4" id="myTabContent">
        <div class="tab-pane fade show mb-4 active" id="posts" role="tabpanel" aria-labelledby="posts-tab">
            <div class="row">
                <div class="col-md-12 col-lg-8 mx-auto">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <form action="/create-post?id=${subjectId}" method="POST">
                                <div class="mb-3">
                                    <label for="text" class="form-label">المقال</label>
                                    <textarea class="form-control" id="text" name="text" rows="3"></textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="video" class="form-label">فيديو</label>
                                    <input class="form-control" id="video" name="video"/>
                                </div>
                                <button type="submit" class="btn btn-primary">إرسال</button>
                            </form>
                        </div>
                    </div>
                </div>
                <%
                    List<Post> posts = (List<Post>) request.getAttribute("posts");
                    for (Post post : posts) {
                %>
                <div class="col-md-12 col-lg-8 mx-auto mt-4">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <div class="d-flex border-bottom pb-3">
                                <img src="https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png"
                                     class="rounded-circle" alt="" style="width: 48px"/>
                                <div class="ms-2">
                                    <h6 class="mb-0"><%= post.getUser().getName()%></h6>
                                    <small class="text-muted"><%= post.getDate()%></small>
                                </div>
                            </div>
                            <div class="mt-2">
                                <%= post.getText()%>
                            </div>
                            <% if (!post.getVideo().equals("null") && !post.getVideo().isEmpty()) {%>
                            <div class="ratio ratio-16x9 mt-2">
                                <iframe type="text/html"src="<%= post.getVideo()%>" frameborder="0"></iframe>
                            </div>
                            <% }%>
                            <div class="border-top mt-4">
                                <form class="mt-4" action="/create-comment?id=<%= post.getId()%>" method="POST">
                                    <div class="input-group mb-3">
                                        <input type="text" class="form-control" name="text" placeholder="اكتب تعليق">
                                        <button class="btn btn-outline-secondary" type="submit" id="button-addon2">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-left" viewBox="0 0 16 16">
                                            <path fill-rule="evenodd" d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z"/>
                                            </svg>
                                        </button>
                                    </div>
                                </form>
                                <%
                                    List<Comment> comments = post.getComments();
                                    for (Comment comment : comments) {
                                %>
                                <div class="d-flex pb-3">
                                    <img src="https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png"
                                         class="rounded-circle" alt="" style="width: 48px"/>
                                    <div class="ms-2">
                                        <div class="d-flex">
                                            <h6 class="mb-0 me-2"><%= comment.getUser().getName()%></h6>
                                            <% if (comment.getUser().getType().equals(3)) { %>
                                            <span class="badge bg-info text-dark me-2">مدير</span>
                                            <% } else if (comment.getUser().getType().equals(2)) { %>
                                            <span class="badge bg-success me-2">الأستاذ</span>
                                            <% }%>
                                            <small class="text-muted"><%= comment.getDate()%></small>
                                        </div>
                                        <div><%= comment.getText()%> </div>
                                    </div>
                                </div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
        </div>
        <% if (request.getAttribute("isTeacher").equals(true) || request.getAttribute("isAdmin").equals(true)) { %>
        <div class="tab-pane fade mb-4" id="students" role="tabpanel" aria-labelledby="students-tab">
            <table class="table table-hover mt-4">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">اسم الدخول</th>
                        <th scope="col">اسم الطالب</th> 
                        <th scope="col">الحالة</th>
                        <th scope="col">أوامر</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<User> students = (List<User>) request.getAttribute("students");
                        for (User student : students) {%>
                    <tr>
                        <th scope="row"><%= student.getId()%></th>
                        <td><%= student.getUsername()%></td>
                        <td><%= student.getName()%></td>
                        <td>
                            <% if (student.getStatus() == 0) { %>
                            <span class="badge bg-danger">لم تتم الموافقة</span>
                            <% } else { %>
                            <span class="badge bg-success">تمت الموافقة</span>
                            <% }%>
                        </td>
                        <td class="d-flex d-gap-3">
                            <form class="ms-2" action="/approve-join?id=<%= student.getJoinId()%>" method="POST">
                                <button type="submit" class="btn btn-outline-primary btn-sm">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="M10.97 4.97a.75.75 0 0 1 1.071 1.05l-3.992 4.99a.75.75 0 0 1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093 3.473-4.425a.236.236 0 0 1 .02-.022z"/>
                                    </svg>
                                </button>
                            </form>
                            <form class="ms-2" action="/reject-join?id=<%= student.getJoinId()%>" method="POST">
                                <button type="submit" class="btn btn-outline-primary btn-sm">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                                    </svg>
                                </button>
                            </form>
                        </td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>
        <% }%>
    </div>
</div>