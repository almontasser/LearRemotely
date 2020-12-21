/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.Helpers.DataAccess;
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
@WebServlet(name = "JoinSubjectServlet", urlPatterns = {"/join-subject"})
public class JoinSubjectServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        Subject subject = DataAccess.getSubjectByPassword(password);
        Integer studentId = (Integer) req.getSession().getAttribute("auth.id");
        if (!DataAccess.isStudentJoinedSubject(studentId, subject.getId())) {
            DataAccess.executeUpdate("INSERT INTO subjects_students (student_id, subject_id, status) VALUES(" + studentId + "," + subject.getId() + ",0)");
        }
        resp.sendRedirect("/");
    }

}
