package com.express.bean;

import java.util.Date;

public class BootStrapTableUser {
    private int id;          //编号    主键
    private String username;   //用户名
    private String userPhone;   //用户手机号
    private String idNumber;    //身份证号
    private String password;    //密码
    private String  regLstTime;    //用户注册时间
    private String loginTime;     //上一次登陆时间

    public BootStrapTableUser() {
    }

    public BootStrapTableUser(int id, String username, String userPhone, String idNumber, String password, String regLstTime, String loginTime) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        this.idNumber = idNumber;
        this.password = password;
        this.regLstTime = regLstTime;
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "BootStrapTableUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", password='" + password + '\'' +
                ", regLstTime='" + regLstTime + '\'' +
                ", loginTime='" + loginTime + '\'' +
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

    public String getRegLstTime() {
        return regLstTime;
    }

    public void setRegLstTime(String regLstTime) {
        this.regLstTime = regLstTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
}
