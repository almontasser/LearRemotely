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
@WebServlet(name = "HomeServlet", urlPatterns = {"/"})
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!Auth.isLoggedIn(req, resp)) {
            resp.sendRedirect("/login");
            return;
        }
        
        Template.Header(req, resp, "الرئيسية");
        Template.Navbar(req, resp);
        if (Auth.isStudent(req, resp)) {
            List<Subject> subjects = DataAccess.getStudentSubjects((Integer) req.getSession().getAttribute("auth.id"));
            req.setAttribute("subjects", subjects);
            Template.Include(req, resp, "WEB-INF/home-student.jsp");
        } else if (Auth.isTeacher(req, resp)) {
            List<Subject> subjects = DataAccess.getTeacherSubjects((Integer) req.getSession().getAttribute("auth.id"));
            req.setAttribute("subjects", subjects);
            Template.Include(req, resp, "WEB-INF/home-teacher.jsp");
        }
        Template.Footer(req, resp);
    }
    
}
