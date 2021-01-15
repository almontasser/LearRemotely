/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.Helpers.Auth;
import Main.Helpers.DataAccess;
import Main.Models.Post;
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
@WebServlet(name = "DeletePost", urlPatterns = {"/delete-post"})
public class DeletePostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        Integer id = Integer.parseInt(req.getParameter("id"));
        Post post = DataAccess.getPostById(id);
        
        if (!Auth.isAdmin(req, resp) && !Auth.isTeacher(req, resp) && !req.getSession().getAttribute("auth.id").equals(post.getUserId())) {
            resp.sendRedirect("/");
            return;
        }
        
        Main.Helpers.DataAccess.executeUpdate("DELETE FROM comments WHERE post_id='"+id+"';");
        Main.Helpers.DataAccess.executeUpdate("DELETE FROM posts WHERE id='"+id+"';");
        
        resp.sendRedirect("/subject?id=" + post.getSubjectId());
    }
}
