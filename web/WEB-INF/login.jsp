<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container min-vh-100">
    <div class="row min-vh-100 justify-content-center align-items-center">
        <div class="card col-4 shadow">
            <div class="card-body">
                <h4 class="card-title text-center">تسجيل الدخول</h4>
                <form action="/login" method="POST">
                    <form>
                        <div class="mb-3">
                            <label for="username" class="form-label">إسم المستخدم</label>
                            <input type="text" class="form-control" id="username" name="username">
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">كلمة المرور</label>
                            <input type="password" class="form-control" id="password" name="password">
                        </div>
                        <div class="mb-3 text-center">
                            <button type="submit" class="btn btn-primary">تسجيل الدخول</button>
                        </div>
                    </form>
                </form>
            </div>
        </div>
    </div>
</div>