<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container mt-4">
    <div class="card">
        <div class="card-body">
            <h4 class="card-title">${title}</h4>
            <form action="${action}" method="POST">
                <input type="hidden" name="id" value="${user != null ? user.id : ""}">
                <div class="mb-3">
                    <label for="username" class="form-label">إسم الدخول</label>
                    <input type="text" class="form-control" id="username" name="username" value="${user != null ? user.username : ""}">
                </div>
                <div class="mb-3">
                    <label for="name" class="form-label">الإسم</label>
                    <input type="text" class="form-control" id="name" name="name" value="${user != null ? user.name : ""}">
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">كلمة المرور</label>
                    <input type="text" class="form-control" id="password" name="password" value="${user != null ? user.password : ""}">
                </div>
                <button type="submit" class="btn btn-primary">حفظ</button>
            </form>
        </div>
    </div>
</div>