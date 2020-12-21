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
@WebServlet(name = "CreateCommentServlet", urlPatterns = {"/create-comment"})
public class CreateCommentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       if (!Auth.isLoggedIn(req, resp)) {
            resp.sendRedirect("/login");
            return;
        }

        req.setCharacterEncoding("utf8");

        Integer postId = Integer.parseInt(req.getParameter("id"));
        Integer userId = (Integer) req.getSession().getAttribute("auth.id");
        
        Post post = DataAccess.getPostById(postId);

        if (!DataAccess.canUserAccessSubject(userId, post.getSubjectId())) {
            resp.sendRedirect("/");
            return;
        }
        
        String text = req.getParameter("text");
        
        DataAccess.executeUpdate("INSERT INTO comments (post_id, user_id, text) VALUES(" + postId + ", " + userId + ", '" + text + "');");
        
        resp.sendRedirect("/subject?id="+post.getSubjectId());
    }
    
}
