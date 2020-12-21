/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.Helpers.Auth;
import Main.Helpers.DataAccess;
import Main.Helpers.SubjectStudentRelation;
import Main.Models.Subject;
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
@WebServlet(name = "ApproveJoinServlet", urlPatterns = {"/approve-join"})
public class ApproveJoinServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        SubjectStudentRelation ssr = DataAccess.getSubjectStudentRelationById(id);
        Subject subject = DataAccess.getSubject(ssr.getSubjectId());
        
        if ((Auth.isTeacher(req, resp) && subject.getTeacherId().equals(req.getSession().getAttribute("auth.id"))) || Auth.isAdmin(req, resp)) {
            DataAccess.executeUpdate("UPDATE subjects_students SET status=1 WHERE id="+id);
            resp.sendRedirect("/subject?id="+subject.getId());
        } else {
            resp.sendRedirect("/");
        }
    }
    
}
