package com.express.bean;

import java.util.Date;

public class BootStrapTableCourier {
    private int id; //编号/主键
    private String username;    //快递员姓名
    private String userPhone;   //快递员手机号
    private String idNumber;    //身份证号
    private String password;    //密码
    private String courierSendNumber;      //派件数
    private String  regLstTime;    //快递员注册时间
    private String  loginTime;     //上一次登陆时间

    @Override
    public String toString() {
        return "BootStrapTableCourier{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", password='" + password + '\'' +
                ", courierSendNumber='" + courierSendNumber + '\'' +
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

    public String getCourierSendNumber() {
        return courierSendNumber;
    }

    public void setCourierSendNumber(String courierSendNumber) {
        this.courierSendNumber = courierSendNumber;
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

    public BootStrapTableCourier() {
    }

    public BootStrapTableCourier(int id, String username, String userPhone, String idNumber, String password, String courierSendNumber, String regLstTime, String loginTime) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        this.idNumber = idNumber;
        this.password = password;
        this.courierSendNumber = courierSendNumber;
        this.regLstTime = regLstTime;
        this.loginTime = loginTime;
    }
}
