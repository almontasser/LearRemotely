/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.Helpers.Auth;
import Main.Helpers.DataAccess;
import Main.Helpers.Template;
import Main.Models.Subject;
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
@WebServlet(name = "EditSubjectServlet", urlPatterns = {"/edit-subject"})
public class EditSubjectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!Auth.isAdmin(req, resp)) {
            resp.sendRedirect("/");
            return;
        }

        req.setAttribute("action", "/edit-subject");
        
        int id = Integer.parseInt(req.getParameter("id"));
        Subject subject = DataAccess.getSubject(id);
        req.setAttribute("subject", subject);
        List<User> teachers = DataAccess.getAllTeachers();
        req.setAttribute("teachers", teachers);

        Template.Header(req, resp, "تعديل مقرر");
        Template.Navbar(req, resp);
        Template.Include(req, resp, "WEB-INF/edit-subject.jsp");
        Template.Footer(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String teacherId = req.getParameter("teacher_id");
        System.out.println("teacherId=" + teacherId);
        DataAccess.executeUpdate("UPDATE Subjects SET name='" + name + "', password='" + password + "', teacher_id="+teacherId+" WHERE id='"+id+"';");
        resp.sendRedirect("/subjects");
    }
}
