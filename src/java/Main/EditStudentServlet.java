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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mahmoud
 */
@WebServlet(name = "EditStudentServlet", urlPatterns = {"/edit-student"})
public class EditStudentServlet extends HttpServlet {
@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!Auth.isAdmin(req, resp)) {
            resp.sendRedirect("/");
            return;
        }

        req.setAttribute("action", "/edit-student");
        
        int id = Integer.parseInt(req.getParameter("id"));
        User student = DataAccess.getUser(id);
        req.setAttribute("user", student);

        Template.Header(req, resp, "تعديل طالب");
        Template.Navbar(req, resp);
        Template.Include(req, resp, "WEB-INF/edit-user.jsp");
        Template.Footer(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        String id = req.getParameter("id");
        String username = req.getParameter("username");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        DataAccess.executeUpdate("UPDATE Users SET username='"+username+"', name='" + name + "', password='" + password + "' WHERE id='"+id+"';");
        resp.sendRedirect("/students");
    }
}
