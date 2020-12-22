/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Helpers;

import Main.Models.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mahmoud
 */
public class Auth {

    public static boolean isLoggedIn(HttpServletRequest req, HttpServletResponse resp) {
        return req.getSession().getAttribute("auth.id") != null;
    }

    public static boolean isAdmin(HttpServletRequest req, HttpServletResponse resp) {
        return req.getSession().getAttribute("auth.type") != null && req.getSession().getAttribute("auth.type").equals(3);
    }

    public static boolean isTeacher(HttpServletRequest req, HttpServletResponse resp) {
        return req.getSession().getAttribute("auth.type") != null && req.getSession().getAttribute("auth.type").equals(2);
    }

    public static boolean isStudent(HttpServletRequest req, HttpServletResponse resp) {
        return req.getSession().getAttribute("auth.type") != null && req.getSession().getAttribute("auth.type").equals(1);
    }

    public static void logIn(HttpServletRequest req, HttpServletResponse resp, String username, String password) throws IOException {
        List<User> users = DataAccess.selectUsers("SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "';");
        if (users.size() == 1) {
            User user = users.get(0);
            req.getSession().setAttribute("auth.id", user.getId());
            req.getSession().setAttribute("auth.name", user.getName());
            req.getSession().setAttribute("auth.username", user.getUsername());
            req.getSession().setAttribute("auth.type", user.getType());
            DataAccess.executeUpdate("UPDATE users SET users.last_login = CURRENT_TIMESTAMP() WHERE users.id="+user.getId());
            resp.sendRedirect("/");
        } else {
            req.getSession().invalidate();
            resp.sendRedirect("/login");
        }
    }
}
