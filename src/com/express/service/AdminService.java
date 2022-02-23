package com.express.service;

import com.express.dao.BaseAdminDao;
import com.express.dao.Impl.AdminDaoMysql;

import java.util.Date;

public class AdminService {

    private static BaseAdminDao dao = new AdminDaoMysql();

    /**
     * 根据用户名，更新登陆时间和登陆IP
     *
     * @param username 用户名
     * @param date     登陆时间
     * @param ip       登陆ip
     */
    public static void updateLoginTimeAndIp(String username, Date date, String ip) {
        dao.updateLoginTime(username, date, ip);
    }

    /**
     * 管理员根据账号密码登陆
     *
     * @param username 账号
     * @param password 密码
     * @return 登陆的结果，true为登陆成功并 跳转页面，false为登陆失败 提示用户并重新返回登陆页面再次输入
     */
    public static boolean login(String username, String password) {
        return dao.login(username, password);
    }
}
