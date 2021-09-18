package com.example.test.bean;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="user")
public class UserBean  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @KeySql(useGeneratedKeys = true)
    private int id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column
    private String password;
    @Column
    private String role;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private Date createDate;
    @Column
    private String createId;
    @Column
    private String deleteId;
    @Column
    private Integer isDelete;
    @Column
    private Date deleteDate;

    public UserBean() {
    }

    public UserBean(int id, String name, String password, String role, String phone, String email, Date createDate, String createId, String deleteId, Integer isDelete, Date deleteDate) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.createDate = createDate;
        this.createId = createId;
        this.deleteId = deleteId;
        this.isDelete = isDelete;
        this.deleteDate = deleteDate;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", createDate=" + createDate +
                ", createId='" + createId + '\'' +
                ", deleteId='" + deleteId + '\'' +
                ", isDelete=" + isDelete +
                ", deleteDate=" + deleteDate +
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }
}