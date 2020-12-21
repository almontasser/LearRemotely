/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Models;

import Main.Helpers.DataAccess;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud
 */
public class User {
    private Integer id;
    private String name;
    private String username;
    private String password;
    private Integer type;
    private Integer status;
    private Integer joinId;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
    
    public List<Subject> getSubjects() {
        if (type == 1)
            return DataAccess.getStudentSubjects(id);
        else if (type == 2)
            return DataAccess.getTeacherSubjects(id);
        else return null;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getJoinId() {
        return joinId;
    }

    public void setJoinId(Integer joinId) {
        this.joinId = joinId;
    }
}
