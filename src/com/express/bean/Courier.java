package com.express.bean;

import java.util.Date;
import java.util.Objects;

public class Courier {
    private int id; //编号/主键
    private String username;    //快递员姓名
    private String userPhone;   //快递员手机号
    private String idNumber;    //身份证号
    private String password;    //密码
    private String courierSendNumber;      //派件数
    private Date regLstTime;    //快递员注册时间
    private Date loginTime;     //上一次登陆时间

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", password='" + password + '\'' +
                ", courierSendNumber='" + courierSendNumber + '\'' +
                ", regIstTime=" + regLstTime +
                ", loginTime=" + loginTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return id == courier.id && Objects.equals(username, courier.username) && Objects.equals(userPhone, courier.userPhone) && Objects.equals(idNumber, courier.idNumber) && Objects.equals(password, courier.password) && Objects.equals(courierSendNumber, courier.courierSendNumber) && Objects.equals(regLstTime, courier.regLstTime) && Objects.equals(loginTime, courier.loginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, userPhone, idNumber, password, courierSendNumber, regLstTime, loginTime);
    }
    //快递员插入/修改时会用到
    public Courier(String username, String userPhone, String idNumber, String password) {
        this.username = username;
        this.userPhone = userPhone;
        this.idNumber = idNumber;
        this.password = password;
    }

    //列表查询会用到
    public Courier(int id, String username, String userPhone, String idNumber, String password, String courierSendNumber, Date regLstTime, Date loginTime) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        this.idNumber = idNumber;
        this.password = password;
        this.courierSendNumber = courierSendNumber;
        this.regLstTime = regLstTime;
        this.loginTime = loginTime;
    }

    public Courier() {
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

}