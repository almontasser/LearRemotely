/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Helpers;

import Main.Models.Comment;
import Main.Models.Post;
import Main.Models.Subject;
import Main.Models.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmoud
 */
public class DataAccess {

    public static Connection GetConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/learn_remotely", "root", "");
    }

    public static Integer executeUpdate(String sql) {
        try {
            Connection con = GetConnection();
            Statement stmt = con.createStatement();
            int r = stmt.executeUpdate(sql);
            stmt.close();
            con.close();
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static ResultSet executeQuery(String sql) throws ClassNotFoundException, SQLException {
        Statement stmt = GetConnection().createStatement();
        return stmt.executeQuery(sql);
    }

    public static void closeResultSet(ResultSet r) throws SQLException {
        Statement s = r.getStatement();
        Connection c = s.getConnection();
        r.close();
        s.close();
        c.close();
    }

    public static List<User> getAllTeachers() {
        return selectUsers("SELECT * FROM users WHERE type = 2;");
    }

    public static List<User> getAllStudents() {
        return selectUsers("SELECT * FROM users WHERE type = 1;");
    }

    public static User getUser(Integer id) {
        List<User> list = selectUsers("SELECT * FROM users WHERE id=" + id);
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public static List<User> selectUsers(String sql) {
        List<User> list = new ArrayList<User>();
        try {
            ResultSet r = executeQuery(sql);

            while (r.next()) {
                User user = new User();
                user.setId(r.getInt("id"));
                user.setName(r.getString("name"));
                user.setUsername(r.getString("username"));
                user.setPassword(r.getString("password"));
                user.setType(r.getInt("type"));
                list.add(user);
            }

            closeResultSet(r);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<Subject> getAllSubjects() {
        return selectSubjects("SELECT subjects.*, users.name as teacher FROM subjects LEFT JOIN users ON subjects.teacher_id=users.id");
    }

    public static Subject getSubject(Integer id) {
        List<Subject> list = selectSubjects("SELECT subjects.*, users.name as teacher FROM subjects LEFT JOIN users ON subjects.teacher_id=users.id WHERE subjects.id='" + id + "';");
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public static List<Subject> getStudentSubjects(Integer id) {
        List<Subject> list = new ArrayList<Subject>();
        try {
            ResultSet r = executeQuery("SELECT subjects_students.subject_id as id, "
                    + "subjects_students.status, subjects.name, subjects.teacher_id, "
                    + "subjects.password, users.name as teacher FROM subjects_students LEFT JOIN subjects "
                    + "ON subjects_students.subject_id = subjects.id LEFT JOIN users "
                    + "ON subjects.teacher_id = users.id WHERE subjects_students.student_id=" + id);

            while (r.next()) {
                Subject subject = new Subject();
                subject.setId(r.getInt("id"));
                subject.setName(r.getString("name"));
                subject.setPassword(r.getString("password"));
                subject.setTeacherId(r.getInt("teacher_id"));
                subject.setTeacher(r.getString("teacher"));
                subject.setStatus(r.getInt("status"));
                list.add(subject);
            }

            closeResultSet(r);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public static List<User> getSubjectStudents(Integer id) {
        List<User> list = new ArrayList<User>();
        try {
            ResultSet r = executeQuery("SELECT subjects_students.student_id as student_id, "
                    + "subjects_students.status, users.username, users.name, "
                    + "subjects_students.id FROM subjects_students LEFT JOIN users "
                    + "ON subjects_students.student_id = users.id "
                    + "WHERE subjects_students.subject_id=" + id);

            while (r.next()) {
                User student = new User();
                student.setId(r.getInt("student_id"));
                student.setName(r.getString("name"));
                student.setUsername(r.getString("username"));
                student.setStatus(r.getInt("status"));
                student.setJoinId(r.getInt("id"));
                list.add(student);
            }

            closeResultSet(r);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    private static List<Subject> selectSubjects(String sql) {
        List<Subject> list = new ArrayList<Subject>();
        try {
            ResultSet r = executeQuery(sql);

            while (r.next()) {
                Subject subject = new Subject();
                subject.setId(r.getInt("id"));
                subject.setName(r.getString("name"));
                subject.setPassword(r.getString("password"));
                subject.setTeacherId(r.getInt("teacher_id"));
                subject.setTeacher(r.getString("teacher"));
                list.add(subject);
            }

            closeResultSet(r);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<Subject> getTeacherSubjects(Integer id) {
        return selectSubjects("SELECT subjects.*, users.name as teacher FROM subjects LEFT JOIN users ON subjects.teacher_id=users.id WHERE subjects.teacher_id=" + id);
    }

    public static Subject getSubjectByPassword(String password) {
        Subject subject = null;
        try {
            ResultSet r = executeQuery("SELECT subjects.*, users.name as teacher FROM subjects LEFT JOIN users ON subjects.teacher_id=users.id WHERE subjects.password='" + password + "';");
            if (r.next()) {
                subject = new Subject();
                subject.setId(r.getInt("id"));
                subject.setName(r.getString("name"));
                subject.setPassword(r.getString("password"));
                subject.setTeacherId(r.getInt("teacher_id"));
                subject.setTeacher(r.getString("teacher"));
            }
            closeResultSet(r);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subject;
    }

    public static boolean isStudentJoinedSubject(Integer studentId, Integer subjectId) {
        Boolean result = false;
        try {
            ResultSet r = executeQuery("SELECT * from subjects_students WHERE student_id=" + studentId + " AND subject_id=" + subjectId);
            if (r.next()) {
                result = true;
            }
            closeResultSet(r);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static boolean canUserAccessSubject(Integer userId, Integer subjectId) {
        boolean result = false;
        
        User user = getUser(userId);
        Subject subject = getSubject(subjectId);
        
        if (user.getType() == 2) {
            if (subject.getTeacherId().equals(userId)) {
                result = true;
            } else {
                result = false;
            }
        } else if (user.getType() == 1) {
            try {
                ResultSet r = executeQuery("SELECT * from subjects_students WHERE status=1 AND student_id=" + userId + " AND subject_id=" + subjectId);
                if (r.next()) {
                    result = true;
                }
                closeResultSet(r);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (user.getType() == 3) {
            result = true;
        }
        return result;
    }
    
    public static SubjectStudentRelation getSubjectStudentRelationById(Integer id) {
        SubjectStudentRelation result = null;
        try {
                ResultSet r = executeQuery("SELECT * from subjects_students WHERE id=" + id);
                if (r.next()) {
                    result = new SubjectStudentRelation();
                    result.setId(r.getInt("id"));
                    result.setSubjectId(r.getInt("subject_id"));
                    result.setStudentId(r.getInt("student_id"));
                    result.setStatus(r.getInt("status"));
                }
                closeResultSet(r);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        return result;
    }
    
    public static SubjectStudentRelation getSubjectStudentRelation(Integer subjectId, Integer studentId) {
        SubjectStudentRelation result = null;
        try {
                ResultSet r = executeQuery("SELECT * from subjects_students WHERE student_id=" + studentId + " AND subject_id=" + subjectId);
                if (r.next()) {
                    result = new SubjectStudentRelation();
                    result.setId(r.getInt("id"));
                    result.setSubjectId(subjectId);
                    result.setStudentId(studentId);
                    result.setStatus(r.getInt("status"));
                }
                closeResultSet(r);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        return result;
    }
    
    public static List<Post> getSubjectPosts(Integer subjectId) {
        List<Post> list = new ArrayList<Post>();
        try {
                ResultSet r = executeQuery("SELECT posts.id, posts.subject_id, "+
                        "posts.user_id, posts.text, posts.video, posts.date, "+
                        "users.name as user_name, users.type as user_type FROM posts LEFT JOIN users ON posts.user_id=users.id WHERE subject_id="+subjectId + " ORDER BY date DESC");
                while (r.next()) {
                    Post post = new Post();
                    post.setId(r.getInt("id"));
                    post.setSubjectId(r.getInt("subject_id"));
                    post.setUserId(r.getInt("user_id"));
                    post.setDate(r.getTimestamp("date"));
                    post.setText(r.getString("text"));
                    post.setVideo(r.getString("video"));
                    User user = new User();
                    user.setId(r.getInt("user_id"));
                    user.setName(r.getString("user_name"));
                    user.setType(r.getInt("user_type"));
                    post.setUser(user);
                    list.add(post);
                }
                closeResultSet(r);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        return list;
    }
    
    public static List<Comment> getPostComments(Integer postId) {
        List<Comment> list = new ArrayList<Comment>();
        try {
                ResultSet r = executeQuery("SELECT comments.*, users.name as user_name, users.type as user_type "+
                        "FROM comments LEFT JOIN users ON comments.user_id=users.id " + 
                        "WHERE comments.post_id="+postId + " ORDER BY comments.date DESC");
                while (r.next()) {
                    Comment comment = new Comment();
                    comment.setId(r.getInt("id"));
                    comment.setPostId(r.getInt("post_id"));
                    comment.setUserId(r.getInt("user_id"));
                    comment.setDate(r.getTimestamp("date"));
                    comment.setText(r.getString("text"));
                    User user = new User();
                    user.setId(r.getInt("user_id"));
                    user.setName(r.getString("user_name"));
                    user.setType(r.getInt("user_type"));
                    comment.setUser(user);
                    list.add(comment);
                }
                closeResultSet(r);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        return list;
    }

    public static Post getPostById(Integer postId) {
        Post result = null;
        try {
                ResultSet r = executeQuery("SELECT * from posts WHERE id=" + postId);
                if (r.next()) {
                    result = new Post();
                    result.setId(r.getInt("id"));
                    result.setSubjectId(r.getInt("subject_id"));
                    result.setUserId(r.getInt("user_id"));
                    result.setDate(r.getTimestamp("date"));
                    result.setText(r.getString("text"));
                    result.setVideo(r.getString("video"));
                }
                closeResultSet(r);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        return result;
    }
    
    public static Comment getCommentById(Integer commentId) {
        Comment result = null;
        try {
                ResultSet r = executeQuery("SELECT * from comments WHERE id=" + commentId);
                if (r.next()) {
                    result = new Comment();
                    result.setId(r.getInt("id"));
                    result.setPostId(r.getInt("post_id"));
                    result.setUserId(r.getInt("user_id"));
                    result.setDate(r.getTimestamp("date"));
                    result.setText(r.getString("text"));
                }
                closeResultSet(r);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        return result;
    }
}
