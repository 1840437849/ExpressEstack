package com.kaikeba.dao;

import java.util.Date;

/**
 * 用于定义数据库中eadmin表格的操作规范
 */
public interface BaseAdminDao {
    /**
     * 根据用户名，更新登陆时间和登陆IP
     * @param username 用户名
     * @param date  登陆时间
     * @param ip    登陆ip
     */
    void updateLoginTime(String username, Date date, String ip);

    /**
     * 管理员根据账号密码登陆
     * @param username  账号
     * @param password  密码
     * @return  登陆的结果，true为登陆成功并 跳转页面，false为登陆失败 提示用户并重新返回登陆页面再次输入
     */
    boolean login(String username,String password);
}
