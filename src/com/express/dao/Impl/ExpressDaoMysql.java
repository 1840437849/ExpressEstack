package com.express.dao.Impl;

import com.express.bean.Express;
import com.express.dao.BaseExpressDao;
import com.express.exception.DuplicateCodeException;
import com.express.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressDaoMysql implements BaseExpressDao {

    public static final String SQL_CONSOLE = "select count(id) data1_size,count(to_days(intime)=to_days(now()) or null) data1_day,count(status=0 or null) data2_size,count(to_days(intime)=to_days(now()) and status=0 or null) data2_day from Express";
    //用于查询数据库中所有的快递
    public static final String SQL_FIND_ALL = "select * from Express";
    //用于分页查询数据库中的快递信息
    public static final String SQL_FIND_LIMIT = "select * from Express limit ?,?";
    //通过快递单号查询快递信息
    public static final String SQL_FIND_BY_NUMBER = "select * from Express where number=?";
    //通过取件码查询快递信息
    public static final String SQL_FIND_BY_CODE = "select * from Express where code=?";
    //通过用户手机号查询快递信息
    public static final String SQL_FIND_BY_USERPHONE = "select * from Express where userphone=?";
    //通过录入人手机号查询快递信息
    public static final String SQL_FIND_BY_SYSPHONE = "select * from Express where sysphone=?";
    //进行快递录入
    public static final String SQL_INSERT = "insert into Express (number,username,userphone,company,code,intime,status,sysphone) values(?,?,?,?,?,now(),0,?) ";
    //根据id修改快递
    public static final String SQL_UPDATE = "update Express set number=?,username=?,company=?,status=? where id=? ";
    //取件操作（状态码更改）
    public static final String SQL_UPDATE_STATUS = "update Express set status=1,outtime=now(),code=null where code=?";
    //根据id删除快递
    public static final String SQL_DELETE = "delete from Express where id=?";

    /**
     * 用于查询所有的全部快递（总数+新增），待取件快递（总数＋新增）
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     */
    @Override
    public List<Map<String, Integer>> console() {
        ArrayList<Map<String, Integer>> data = new ArrayList<>();
        //1.    获取数据库连接
        Connection connection = DruidUtil.getConnection();  //获取连接对象
        //2.    预编译sql语句
        PreparedStatement state = null;
        ResultSet resultSet = null;
        try {
            state = connection.prepareStatement(SQL_CONSOLE);
            //3.    填充参数（可选）
            //4.    执行sql语句
            resultSet = state.executeQuery();
            //5.    获取执行结果
            if (resultSet.next()) {
                int data1_size = resultSet.getInt("data1_size");
                int data1_day = resultSet.getInt("data1_day");
                int data2_size = resultSet.getInt("data2_size");
                int data2_day = resultSet.getInt("data2_day");
                Map data1 = new HashMap();
                data1.put("data1_size", data1_size);
                data1.put("data1_day", data1_day);
                Map data2 = new HashMap();
                data2.put("data2_size", data2_size);
                data2.put("data2_day", data2_day);
                data.add(data1);
                data.add(data2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.    资源释放
            DruidUtil.close(connection, state, resultSet);
        }
        return data;
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页的标记，true,表示分页，false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每页查询的数量
     * @return 快递的集合
     */
    @Override
    public List<Express> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Express> data = new ArrayList<>();
        //1.    获取数据库连接
        Connection connection = DruidUtil.getConnection();  //获取连接对象
        //2.    预编译sql语句
        PreparedStatement state = null;
        ResultSet resultSet = null;
        try {
            if (limit) {
                state = connection.prepareStatement(SQL_FIND_LIMIT);
                //3.    填充参数（可选）
                state.setInt(1, offset);
                state.setInt(2, pageNumber);
            } else {
                state = connection.prepareStatement(SQL_FIND_ALL);
            }
            //4.    执行sql语句
            resultSet = state.executeQuery();
            //5.    获取执行结果
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("inTime");
                Timestamp outTime = resultSet.getTimestamp("outTime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
                data.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.    资源释放
            DruidUtil.close(connection, state, resultSet);
        }
        return data;
    }

    /**
     * 根据快递单号查询快递信息
     *
     * @param number 快递信息
     * @return 查询的快递信息，单号不存在时返回null
     */
    @Override
    public Express findByNumber(String number) {
        //1.    获取数据库连接
        Connection connection = DruidUtil.getConnection();  //获取连接对象
        //2.    预编译sql语句
        PreparedStatement state = null;
        ResultSet resultSet = null;
        try {
            state = connection.prepareStatement(SQL_FIND_BY_NUMBER);
            //3.    填充参数（可选）
            state.setString(1, number);
            //4.    执行sql语句
            resultSet = state.executeQuery();
            //5.    获取执行结果
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("inTime");
                Timestamp outTime = resultSet.getTimestamp("outTime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
                return e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.    资源释放
            DruidUtil.close(connection, state, resultSet);
        }
        return null;
    }

    /**
     * 根据取件码查询快递信息
     *
     * @param code 快递信息
     * @return 查询的快递信息，取件码不存在时返回null
     */
    @Override
    public Express findByCode(String code) {
        //1.    获取数据库连接
        Connection connection = DruidUtil.getConnection();  //获取连接对象
        //2.    预编译sql语句
        PreparedStatement state = null;
        ResultSet resultSet = null;
        try {
            state = connection.prepareStatement(SQL_FIND_BY_CODE);
            //3.    填充参数（可选）
            state.setString(1, code);
            //4.    执行sql语句
            resultSet = state.executeQuery();
            //5.    获取执行结果
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                Timestamp inTime = resultSet.getTimestamp("inTime");
                Timestamp outTime = resultSet.getTimestamp("outTime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
                return e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.    资源释放
            DruidUtil.close(connection, state, resultSet);
        }
        return null;
    }

    /**
     * 根据用户的手机号查询快递信息
     * 因为一个人可能有多个快递，所以定义一个集合
     *
     * @param userPhone 用户手机号
     * @return 查询的快递信息列表
     */
    @Override
    public List<Express> findByUserPhone(String userPhone) {
        ArrayList<Express> data = new ArrayList<>();
        //1.    获取数据库连接
        Connection connection = DruidUtil.getConnection();  //获取连接对象
        //2.    预编译sql语句
        PreparedStatement state = null;
        ResultSet resultSet = null;
        try {
            state = connection.prepareStatement(SQL_FIND_BY_USERPHONE);
            //3.    填充参数（可选）
            state.setString(1, userPhone);
            //4.    执行sql语句
            resultSet = state.executeQuery();
            //5.    获取执行结果
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("inTime");
                Timestamp outTime = resultSet.getTimestamp("outTime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
                data.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.    资源释放
            DruidUtil.close(connection, state, resultSet);
        }
        return data;
    }

    /**
     * 根据录入人的手机号查询快递信息
     * 因为一个人可能有多个快递，所以定义一个集合
     *
     * @param sysPhone 录入人手机号
     * @return 查询的快递信息列表
     */
    @Override
    public List<Express> findBySysPhone(String sysPhone) {
        ArrayList<Express> data = new ArrayList<>();
        //1.    获取数据库连接
        Connection connection = DruidUtil.getConnection();  //获取连接对象
        //2.    预编译sql语句
        PreparedStatement state = null;
        ResultSet resultSet = null;
        try {
            state = connection.prepareStatement(SQL_FIND_BY_SYSPHONE);
            //3.    填充参数（可选）
            state.setString(1, sysPhone);
            //4.    执行sql语句
            resultSet = state.executeQuery();
            //5.    获取执行结果
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("inTime");
                Timestamp outTime = resultSet.getTimestamp("outTime");
                int status = resultSet.getInt("status");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
                data.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.    资源释放
            DruidUtil.close(connection, state, resultSet);
        }
        return data;
    }

    /**
     * 快递的录入
     * insert into Express (number,username,userphone,company,code,intime,status,sysphone) values(?,?,?,?,?,now(),0,?)
     *
     * @param e 要录入的快递对象
     * @return 录入的结果，成功为true，失败为false
     */
    @Override
    public boolean insert(Express e) throws DuplicateCodeException{
        //1.    连接的获取
        Connection connection = DruidUtil.getConnection();
        //2.    预编译sql语句
        PreparedStatement state = null;
        try {
            state = connection.prepareStatement(SQL_INSERT);
            //3.    填充参数
            state.setString(1, e.getNumber());
            state.setString(2, e.getUsername());
            state.setString(3, e.getUserPhone());
            state.setString(4, e.getCompany());
            state.setString(5, e.getCode());
            state.setString(6, e.getSysPhone());
            //4.    执行sql语句并获取执行结果
            return state.executeUpdate()>0?true:false;
        } catch (SQLException ex) {
           /* ex.printStackTrace();*/
            if (ex.getMessage().endsWith(" for key 'express.code'")) {
                //是因为取件码重复而出现异常
                DuplicateCodeException e2=new DuplicateCodeException(ex.getMessage());
                throw e2;
            } else {
                ex.printStackTrace();
            }
        }finally {
            DruidUtil.close(connection,state,null);
        }
        //5.    释放资源
        return false;
    }

    /**
     * 快递的修改
     * update Express set number=?,username=?,company=? status=? where id=?
     * @param id         要修改的快递id
     * @param newExpress 新的快递对象（number,company,username,userPhone）
     * @return 修改的结果，true为成功，false为失败
     */
    @Override
    public boolean update(int id, Express newExpress) {
        //1.    连接的获取
        Connection connection = DruidUtil.getConnection();
        //2.    预编译sql语句
        PreparedStatement state = null;
        try {
            state = connection.prepareStatement(SQL_UPDATE);
            //3.    填充参数
            state.setString(1, newExpress.getNumber());
            state.setString(2, newExpress.getUsername());
            state.setString(3, newExpress.getCompany());
            state.setInt(4, newExpress.getStatus());
            state.setInt(5,id);
            //4.    执行sql语句并获取执行结果
            return state.executeUpdate()>0?true:false;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidUtil.close(connection,state,null);
        }
        return false;
    }

    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 要取件的快递取件码
     * @return 修改的结果，true为成功，false为失败
     */
    @Override
    public boolean updateStatus(String code) {
        //1.    连接的获取
        Connection connection = DruidUtil.getConnection();
        //2.    预编译sql语句
        PreparedStatement state = null;
        try {
            state = connection.prepareStatement(SQL_UPDATE_STATUS);
            //3.    填充参数
            state.setString(1, code);
            //4.    执行sql语句并获取执行结果
            return state.executeUpdate()>0?true:false;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidUtil.close(connection,state,null);
        }
        return false;
    }

    /**
     * 根据id删除快递信息
     *
     * @param id 要删除的快递id
     * @return 删除的结果，true为成功，false为失败
     */
    @Override
    public boolean delete(int id) {
        //1.    连接的获取
        Connection connection = DruidUtil.getConnection();
        //2.    预编译sql语句
        PreparedStatement state = null;
        try {
            state = connection.prepareStatement(SQL_DELETE);
            //3.    填充参数
            state.setInt(1, id);
            //4.    执行sql语句并获取执行结果
            return state.executeUpdate()>0?true:false;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidUtil.close(connection,state,null);
        }
        return false;
    }
}
