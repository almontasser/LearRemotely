/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.Helpers.Auth;
import Main.Helpers.DataAccess;
import Main.Helpers.Template;
import Main.Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mahmoud
 */
@WebServlet(name = "CreateSubjectServlet", urlPatterns = {"/create-subject"})
public class CreateSubjectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!Auth.isAdmin(req, resp)) {
            resp.sendRedirect("/");
            return;
        }

        req.setAttribute("action", "/create-subject");
        List<User> teachers = DataAccess.getAllTeachers();
        req.setAttribute("teachers", teachers);

        Template.Header(req, resp, "إضافة مقرر");
        Template.Navbar(req, resp);
        Template.Include(req, resp, "WEB-INF/edit-subject.jsp");
        Template.Footer(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String teacherId = req.getParameter("teacher_id");
        DataAccess.executeUpdate("INSERT INTO Subjects (name, password, teacher_id) VALUES('" + name + "', '" + password + "', "+teacherId+");");
        resp.sendRedirect("/subjects");
    }
}
