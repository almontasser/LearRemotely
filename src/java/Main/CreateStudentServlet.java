/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.Helpers.Auth;
import Main.Helpers.DataAccess;
import Main.Helpers.Template;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mahmoud
 */
@WebServlet(name = "CreateStudentServlet", urlPatterns = {"/create-student"})
public class CreateStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!Auth.isAdmin(req, resp)) {
            resp.sendRedirect("/");
            return;
        }

        req.setAttribute("action", "/create-student");

        Template.Header(req, resp, "إضافة طالب");
        Template.Navbar(req, resp);
        Template.Include(req, resp, "WEB-INF/edit-user.jsp");
        Template.Footer(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        String username = req.getParameter("username");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        DataAccess.executeUpdate("INSERT INTO Users (username, name, password, type) VALUES('"+username+"', '" + name + "', '" + password + "', 1);");
        resp.sendRedirect("/students");
    }
}
