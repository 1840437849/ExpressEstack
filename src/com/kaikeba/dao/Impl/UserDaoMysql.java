package com.kaikeba.dao.Impl;

import com.kaikeba.bean.User;
import com.kaikeba.dao.BaseUserDao;
import com.kaikeba.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoMysql implements BaseUserDao {

    //查询所有用户（总数 + 新增）用于在控制台显示
    public static final String SQL_CONSOLE = "select count(id) user_size,count(to_days(reglsttime)=to_days(now()) or null) user_day from user";
    //查询所有用户信息
    public static final String SQL_FIND_ALL = "select * from user";
    //用分页查询，查询出数据库中所有的用户信息
    public static final String SQL_FIND_LIMIT = "select * from user limit ?,?";
    //根据手机号查询用户信息
    public static final String SQL_BY_PHONE = "select * from user where userPhone=?";
    //插入用户信息
    public static final String SQL_INSERT = "insert into user (username,userPhone,idNumber,password,reglsttime,logintime) values(?,?,?,?,now(),null) ";
    //根据手机号修改用户信息
    public static final String SQL_UPDATE = "update user set username=?,userPhone=?,idNumber=?,password=? where userPhone=?";
    //根据手机号删除用户信息
    public static final String SQL_DELETE = "delete from user where=? ";

    /**
     * 用于查询数据中全部用户（用户总数，当日新增）
     * 用户（总数，新增）
     *
     * @return [{ size:总数 , day:当日新增 }]
     */
    @Override
    public Map<String, Integer> console() {
        Map<String, Integer> map =new HashMap<>();
        //1.获取连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state =null;
        ResultSet resultSet =null;
        //2.预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_CONSOLE);
            //3.填充参数(这里没有参数可选)
            //4.执行SQL语句
            resultSet = state.executeQuery();
            if (resultSet.next()) {
                //将值提取出来
                int user_size = resultSet.getInt("user_size");
                int user_day = resultSet.getInt("user_day");
                map.put("user_size",user_size);
                map.put("user_day",user_day);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,resultSet);
        }
        //5.关闭数据库流
        return map;
    }

    /**
     * 用于查询数据库中所有的用户信息
     *
     * @param limit      true为分页（默认），false为不分页直接查询所有
     * @param offset     查询SQL语句的起始索引（就是是从哪一条开始查的）
     * @param pageNumber 每一页显示的用户数量
     * @return 用户信息的集合
     */
    @Override
    public List<User> findAll(boolean limit, int offset, int pageNumber) {
        //获取连接
        Connection conn = DruidUtil.getConnection();
        //预编译SQL
        PreparedStatement state =null;
        try {
            if (limit) {
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                //填充参数
                state.setInt(1,offset);
                state.setInt(2,pageNumber);
            } else {
                state=conn.prepareStatement(SQL_FIND_ALL);
                //没有参数需要填充
            }
            ResultSet resultSet = state.executeQuery();
            while (resultSet.next()) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }

        return null;
    }

    /**
     * 用于通过手机号查询快递员信息
     *
     * @param userPhone 要查询的用户手机号
     * @return 用户的信息
     */
    @Override
    public User findByPhone(String userPhone) {
        return null;
    }

    /**
     * 用户信息录入
     *
     * @param u 需要录入的用户信息（String username,String userPhone,String idNumber,String password）
     * @return 录入的结果，成功为true，失败为false
     */
    @Override
    public boolean insert(User u) {
        return false;
    }

    /**
     * 根据手机号修改用户信息
     *
     * @param userPhone 需要修改的用户手机号
     * @param newUser   新的用户信息
     * @return 录入的结果，成功为true，失败为false
     */
    @Override
    public boolean update(String userPhone, User newUser) {
        return false;
    }

    /**
     * 根据用户手机号删除用户信息
     *
     * @param userPhone 要删除的用户手机号
     * @return 录入的结果，成功为true，失败为false
     */
    @Override
    public boolean delete(String userPhone) {
        return false;
    }
}
