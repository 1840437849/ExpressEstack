package com.express.bean;

import java.util.Date;
import java.util.Objects;

public class User {
    private int id;          //编号    主键
    private String username;   //用户名
    private String userPhone;   //用户手机号
    private String idNumber;    //身份证号
    private String password;    //密码
    private Date regLstTime;    //用户注册时间
    private Date loginTime;     //上一次登陆时间
    private boolean user;       //表示身份是用户
    public User() {
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    //插入用到
    public User(String username, String userPhone, String idNumber, String password) {
        this.username = username;
        this.userPhone = userPhone;
        this.idNumber = idNumber;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(userPhone, user.userPhone) && Objects.equals(idNumber, user.idNumber) && Objects.equals(password, user.password) && Objects.equals(regLstTime, user.regLstTime) && Objects.equals(loginTime, user.loginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, userPhone, idNumber, password, regLstTime, loginTime);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", password='" + password + '\'' +
                ", regLstTime=" + regLstTime +
                ", loginTime=" + loginTime +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegLstTime() {
        return regLstTime;
    }

    public void setRegLstTime(Date regLstTime) {
        this.regLstTime = regLstTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public User(int id, String username, String userPhone, String idNumber, String password, Date regLstTime, Date loginTime) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        this.idNumber = idNumber;
        this.password = password;
        this.regLstTime = regLstTime;
        this.loginTime = loginTime;
    }
}
