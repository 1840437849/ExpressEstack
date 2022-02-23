package com.express.dao.Impl;

import com.express.dao.BaseAdminDao;
import com.express.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AdminDaoMysql implements BaseAdminDao {

    private static final String SQL_UPDATE_LOGIN_TIME = "update eadmin set logintime=?,loginip=? where username=?";
    private static final String SQL_LOGIN = "select id from eadmin where username=? and password=?";

    /**
     * 根据用户名，更新登陆时间和登陆IP
     *
     * @param username 用户名
     * @param date     登陆时间
     * @param ip       登陆ip
     */
    @Override
    public void updateLoginTime(String username, Date date, String ip) {
        //1.    获取连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_UPDATE_LOGIN_TIME);
            //3.    填充参数
            state.setDate(1,new java.sql.Date(date.getTime()));
            state.setString(2,ip);
            state.setString(3,username);
            //4.    执行
            state.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //5.    释放资源
            DruidUtil.close(conn,state,null);
        }
    }

    /**
     * 管理员根据账号密码登陆
     *
     * @param username 账号
     * @param password 密码
     * @return 登陆的结果，true为登陆成功并 跳转页面，false为登陆失败 提示用户并重新返回登陆页面再次输入
     */
    @Override
    public boolean login(String username, String password) {
        //1.获得连接
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet resultSet =null;
        //2.预编译SQL语句
        try {
            state = connection.prepareStatement(SQL_LOGIN);
            //3.填充参数
            state.setString(1,username);
            state.setString(2,password);
            //4.执行并获取结果
            resultSet = state.executeQuery();
            //根据查询结果返回
            return resultSet.next();    //游标向下移动，如果有值，向下移动
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            DruidUtil.close(connection, state, resultSet);
        }
        //没有值，那么返回false
        return false;
    }
}
