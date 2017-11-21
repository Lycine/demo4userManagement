package org.jozif.demo4usermanagement.entity;

import javax.persistence.*;

/**
 * @author hongyu 2017-11-18
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SALT")
    private String salt;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CELL_PHONE")
    private String cellPhone;

    @Column(name = "ROLE")
    private Integer role;

    @Column(name = "DEPARTMENT")
    private Integer department;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "GMT_CREATE")
    private String gmtCreate;

    @Column(name = "GMT_LAST_LOGIN", nullable = true)
    private String gmtLastLogin;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", role=" + role +
                ", department=" + department +
                ", status=" + status +
                ", gmtCreate='" + gmtCreate + '\'' +
                ", gmtLastLogin='" + gmtLastLogin + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtLastLogin() {
        return gmtLastLogin;
    }

    public void setGmtLastLogin(String gmtLastLogin) {
        this.gmtLastLogin = gmtLastLogin;
    }
}
