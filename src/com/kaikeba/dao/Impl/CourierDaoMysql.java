package com.kaikeba.dao.Impl;

import com.kaikeba.bean.Courier;
import com.kaikeba.dao.BaseCourierDao;
import com.kaikeba.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CourierDaoMysql implements BaseCourierDao {

    //查询所有快递员（总数 + 新增）用于在控制台显示
    public static final String SQL_CONSOLE = "SELECT COUNT(ID) courier_size,COUNT(TO_DAYS(reglsttime)=TO_DAYS(NOW()) OR NULL) courier_day FROM COURIER";
    //用于查询数据库中所有的快递员信息
    public static final String SQL_FIND_ALL = "select * from courier";
    //用于分页查询数据库中的快递员信息
    public static final String SQL_FIND_LIMIT = "select * from courier limit ?,?";
    //根据手机号查询快递员信息
    public static final String SQL_FIND_BY_PHONE = "select * from courier where userPhone=?";
    //添加快递员
    public static final String SQL_INSERT = "insert into courier (username,userPhone,idNumber,password,couriersendnumber,reglsttime) values(?,?,?,?,0,now())";
    //根据手机号修改快递员
    public static final String SQL_UPDATE = "update courier set username=?,userPhone=?,idNumber=?,password=? where userPhone=?";
    //删除快递员
    public static final String SQL_DELETE = "delete from courier where userPhone=?";

    /**
     * 用于查询数据库中的全部快递员（总数，当日新增）
     * 快递员（总数，当日新增）
     *
     * @return [{size:总数, day:新增}]
     */
    @Override
    public List<Map<String, Integer>> console() {
        ArrayList<Map<String, Integer>> data = new ArrayList<>();
        //1.    获取数据库链接
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL
        PreparedStatement state = null;
        ResultSet resultSet = null;
        try {
            state = conn.prepareStatement(SQL_CONSOLE);
            //3.    填充参数（可选）这里没有需要填充的参数
            //4.    执行SQL语句
            resultSet = state.executeQuery();
            //5.    获取执行结果
            if (resultSet.next()) {
                int courier_size = resultSet.getInt("courier_size");
                int courier_day = resultSet.getInt("courier_day");
                Map data1 = new HashMap();
                data1.put("courier_size", courier_size);
                data1.put("courier_day", courier_day);
                data.add(data1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.    释放资源
            DruidUtil.close(conn, state, resultSet);
        }
        return data;
    }

    /**
     * 用于查询所有快递员信息
     *
     * @param limit      是否分页的标记，true,表示分页，false表示查询所有快递员信息
     * @param offset     SQL语句的其实索引
     * @param pageNumber 每页查询的数量
     * @return 快递员信息的集合
     */
    @Override
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        //因为返回的是一个List集合，所以这里new一个ArrayList
        ArrayList<Courier> data = new ArrayList<>();
        //1.    获取数据库链接
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL
        PreparedStatement state = null;
        ResultSet resultSet = null;
        try {
            //3.    填充参数（可选）
            if (limit) {
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                state.setInt(1, offset);
                state.setInt(2, pageNumber);
            } else {
                state = conn.prepareStatement(SQL_FIND_ALL);
            }
            //4.    执行SQL语句
            resultSet = state.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String idNumber = resultSet.getString("idNumber");
                String password = resultSet.getString("password");
                String courierSendNumber = resultSet.getString("courierSendNumber");
                Date regLstTime = resultSet.getDate("regLstTime");
                Date loginTime = resultSet.getDate("loginTime");
                Courier c = new Courier(id, username, userPhone, idNumber, password, courierSendNumber, regLstTime, loginTime);
                data.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, resultSet);
        }
        return data;
    }

    /**
     * 用于通过手机号查询快递员信息
     *
     * @param userPhone 要查询的快递员手机号
     * @return 快递员的信息
     */
    @Override
    public Courier findByPhone(String userPhone) {
        //1.    获取数据库链接
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL
        PreparedStatement state = null;
        ResultSet resultSet = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_PHONE);
            //3.    填充参数（可选）
            state.setString(1, userPhone);
            //4.    执行SQL语句
            resultSet = state.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String idNumber = resultSet.getString("idNumber");
                String password = resultSet.getString("password");
                String courierSendNumber = resultSet.getString("courierSendNumber");
                Date regLstTime = resultSet.getDate("reglsttime");
                Date loginTime = resultSet.getDate("loginTime");
                Courier c = new Courier(id, username, userPhone, idNumber, password, courierSendNumber, regLstTime, loginTime);
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, resultSet);
        }
        return null;
    }

    /**
     * 添加快递员
     *
     * @param c 快递员信息
     * @return 受影响的行数
     */
    @Override
    public boolean insert(Courier c) {
        //1.    获取数据库链接
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            //3.    填充参数（可选）
            state.setString(1, c.getUsername());
            state.setString(2, c.getUserPhone());
            state.setString(3, c.getIdNumber());
            state.setString(4, c.getPassword());
            //4.    执行SQL语句
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    /**
     * 修改快递员
     *
     * @param userPhone  要修改快递员的手机号
     * @param newCourier 新的快递员对象（String username,String userPhone,String idNumber,String password）
     * @return
     */
    @Override
    public boolean update(String userPhone, Courier newCourier) {
        //1.    获取数据库链接
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL
        PreparedStatement state = null;

        try {
            state = conn.prepareStatement(SQL_UPDATE);
            //3.    填充参数（可选）
            state.setString(1, newCourier.getUsername());
            state.setString(2, newCourier.getUserPhone());
            state.setString(3, newCourier.getIdNumber());
            state.setString(4, newCourier.getPassword());
            state.setString(5, userPhone);
            //4.    执行SQL语句
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    /**
     * 删除快递员
     *
     * @param userPhone 要删除的快递员的手机号
     * @return 受影响的行数
     */
    @Override
    public boolean delete(String userPhone) {
        //1.    获取数据库链接
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_DELETE);
            //3.    填充参数（可选）这里没有需要填充的参数
            state.setString(1, userPhone);
            //4.    执行SQL语句
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }
}
