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
@WebServlet(name = "TeachersServlet", urlPatterns = {"/teachers"})
public class TeachersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (!Auth.isAdmin(req, resp)) {
            resp.sendRedirect("/");
            return;
        }
        
        List<User> teachers = DataAccess.getAllTeachers();
        req.setAttribute("teachers", teachers);
        
        Template.Header(req, resp, "الأساتذة");
        Template.Navbar(req, resp);
        Template.Include(req, resp, "WEB-INF/teachers.jsp");
        Template.Footer(req, resp);
    }
    
}
