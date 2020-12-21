/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.Helpers.Auth;
import Main.Helpers.DataAccess;
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
@WebServlet(name = "DeleteSubjectServlet", urlPatterns = {"/delete-subject"})
public class DeleteSubjectServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!Auth.isAdmin(req, resp)) {
            resp.sendRedirect("/");
            return;
        }
        
        String id = req.getParameter("id");
        DataAccess.executeUpdate("DELETE FROM Subjects WHERE id='"+id+"';");
        resp.sendRedirect("/subjects");
    }
    
}
