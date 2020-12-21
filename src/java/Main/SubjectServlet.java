/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.Helpers.Auth;
import Main.Helpers.DataAccess;
import Main.Helpers.Template;
import Main.Models.Post;
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
@WebServlet(name = "SubjectServlet", urlPatterns = {"/subject"})
public class SubjectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!Auth.isLoggedIn(req, resp)) {
            resp.sendRedirect("/login");
            return;
        }
        
        Integer subjectId = Integer.parseInt(req.getParameter("id"));
        Subject subject = DataAccess.getSubject(subjectId);
        
        if (!DataAccess.canUserAccessSubject((Integer)req.getSession().getAttribute("auth.id"), subjectId)) {
            resp.sendRedirect("/");
            return;
        }
        
        if (Auth.isTeacher(req, resp) || Auth.isAdmin(req, resp)) {
            List<User> students = DataAccess.getSubjectStudents(subjectId);
            req.setAttribute("students", students);
        }
        
        req.setAttribute("subjectId", subject.getId());
        
        List<Post> posts = DataAccess.getSubjectPosts(subjectId);
        req.setAttribute("posts", posts);
        
        Template.Header(req, resp, subject.getName());
        Template.Navbar(req, resp);
        Template.Include(req, resp, "WEB-INF/subject.jsp");
        Template.Footer(req, resp);
    }
    
}
