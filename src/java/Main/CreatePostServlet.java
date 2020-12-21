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
@WebServlet(name = "CreatePostServlet", urlPatterns = {"/create-post"})
public class CreatePostServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!Auth.isLoggedIn(req, resp)) {
            resp.sendRedirect("/login");
            return;
        }

        req.setCharacterEncoding("utf8");

        Integer subjectId = Integer.parseInt(req.getParameter("id"));
        Integer userId = (Integer) req.getSession().getAttribute("auth.id");

        if (!DataAccess.canUserAccessSubject(userId, subjectId)) {
            resp.sendRedirect("/");
            return;
        }

        String text = req.getParameter("text");
        String video = req.getParameter("video");
        
        // convert youtube watch link to embedded link
        video = video.replace("youtube.com/watch?v=", "youtube.com/embed/");

        DataAccess.executeUpdate("INSERT INTO posts (subject_id, user_id, text, video) VALUES(" + subjectId + ", " + userId + ", '" + text + "', '" + video + "');");
        resp.sendRedirect("/subject?id="+subjectId);
    }

}
