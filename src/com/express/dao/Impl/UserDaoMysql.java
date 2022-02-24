package com.express.dao.Impl;

import com.express.bean.User;
import com.express.dao.BaseUserDao;
import com.express.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
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
    public static final String SQL_DELETE = "delete from user where userPhone=? ";

    /**
     * 用于查询数据中全部用户（用户总数，当日新增）
     * 用户（总数，新增）
     *
     * @return [{ size:总数 , day:当日新增 }]
     */
    @Override
    public List<Map<String, Integer>> console() {
        ArrayList<Map<String, Integer>> data = new ArrayList<>();
        //1.获取连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet resultSet = null;
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
                Map data1=new HashMap();
                data1.put("user_size", user_size);
                data1.put("user_day", user_day);
                data.add(data1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5.关闭数据库流
            DruidUtil.close(conn, state, resultSet);
        }

        return data;
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
        List<User> list = new ArrayList<>();
        //获取连接
        Connection conn = DruidUtil.getConnection();
        //预编译SQL
        PreparedStatement state = null;
        ResultSet resultSet = null;
        try {
            if (limit) {
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                //填充参数
                state.setInt(1, offset);
                state.setInt(2, pageNumber);
            } else {
                state = conn.prepareStatement(SQL_FIND_ALL);
                //没有参数需要填充
            }
            resultSet = state.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String idNumber = resultSet.getString("idNumber");
                String password = resultSet.getString("password");
                Date regLstTime = resultSet.getDate("reglsttime");
                Date loginTime = resultSet.getDate("logintime");
                User u = new User(id, username, userPhone, idNumber, password, regLstTime, loginTime);
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, resultSet);
        }
        return list;
    }

    /**
     * 用于通过手机号查询快递员信息
     *
     * @param userPhone 要查询的用户手机号
     * @return 用户的信息
     */
    @Override
    public User findByPhone(String userPhone) {
        //创建连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet resultSet = null;
        try {
            state = conn.prepareStatement(SQL_BY_PHONE);
            state.setString(1, userPhone);
            resultSet = state.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String idNumber = resultSet.getString("idNumber");
                String password = resultSet.getString("password");
                Date regLstTime = resultSet.getDate("reglsttime");
                Date loginTime = resultSet.getDate("logintime");
                User u = new User(id, username, userPhone, idNumber, password, regLstTime, loginTime);
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,resultSet);
        }
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
        //接收参数
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            state.setString(1, u.getUsername());
            state.setString(2, u.getUserPhone());
            state.setString(3, u.getIdNumber());
            state.setString(4, u.getPassword());
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
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
        //接收参数
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            state.setString(1, newUser.getUsername());
            state.setString(2, newUser.getUserPhone());
            state.setString(3, newUser.getIdNumber());
            state.setString(4, newUser.getPassword());
            state.setString(5, newUser.getUserPhone());
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
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
        //接收参数
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_DELETE);
            state.setString(1,userPhone);
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }
}
