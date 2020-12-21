<%@page import="Main.Models.Subject"%>
<%@page import="Main.Models.User"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container mt-4">
    <div class="card">
        <div class="card-body">
            <h4 class="card-title">${title}</h4>
            <form action="${action}" method="POST">
                <input type="hidden" name="id" value="${subject != null ? subject.id : ""}">
                <div class="mb-3">
                    <label for="name" class="form-label">إسم المقرر</label>
                    <input type="text" class="form-control" id="name" name="name" value="${subject != null ? subject.name : ""}">
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">كلمة المرور</label>
                    <input type="text" class="form-control" id="password" name="password" value="${subject != null ? subject.password : ""}">
                </div>
                <div class="mb-3">
                    <label for="teacher_id" class="form-label">الأستاذ</label>
                    <select class="form-select" id="teacher_id" name="teacher_id">
                        <option value="null">اختر أستاذ المقرر</option>
                        <% 
                            List<User> teachers = (List<User>) request.getAttribute("teachers");
                            for (User teacher : teachers) {
                                String selected = "";
                                Subject subject = (Subject)request.getAttribute("subject");
                                if (subject != null && subject.getTeacherId() == teacher.getId())
                                    selected = "selected";
                        %>
                        <option <%= selected %> value="<%= teacher.getId() %>"><%= teacher.getName() %></option>
                        <% } %>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">حفظ</button>
            </form>
        </div>
    </div>
</div>