/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Helpers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mahmoud
 */
public class Template {
    public static void Header(HttpServletRequest req, HttpServletResponse resp, String title) throws ServletException, IOException {    
        resp.setContentType("text/html; charset=UTF-8");
        req.setAttribute("title", title);
        req.setAttribute("isTeacher", Auth.isTeacher(req, resp));
        req.setAttribute("isAdmin", Auth.isAdmin(req, resp));
        req.setAttribute("isStudent", Auth.isStudent(req, resp));
        req.getRequestDispatcher("WEB-INF/header.jsp").include(req, resp);
    }
    
    public static void Navbar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/navbar.jsp").include(req, resp);
    }
    
    public static void Footer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/footer.jsp").include(req, resp);
    }
    
    public static void Include(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
        req.getRequestDispatcher(path).include(req, resp);
    }
}
