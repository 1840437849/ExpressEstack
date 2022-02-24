package com.express.service;

import com.express.bean.User;
import com.express.dao.BaseUserDao;
import com.express.dao.Impl.UserDaoMysql;

import java.util.List;
import java.util.Map;

public class UserService  {
    static BaseUserDao dao=new UserDaoMysql();

    /**
     * 用于查询数据中全部用户（用户总数，当日新增）
     * 用户（总数，新增）
     *
     * @return [{ size:总数 , day:当日新增 }]
     */
    public static  List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 用于查询数据库中所有的用户信息
     *
     * @param limit      true为分页（默认），false为不分页直接查询所有
     * @param offset     查询SQL语句的起始索引（就是是从哪一条开始查的）
     * @param pageNumber 每一页显示的用户数量
     * @return 用户信息的集合
     */
    public static List<User> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    /**
     * 用于通过手机号查询快递员信息
     *
     * @param userPhone 要查询的用户手机号
     * @return 用户的信息
     */
    public static User findByPhone(String userPhone) {
        return dao.findByPhone(userPhone);
    }

    /**
     * 用户信息录入
     *
     * @param u 需要录入的用户信息（String username,String userPhone,String idNumber,String password）
     * @return 录入的结果，成功为true，失败为false
     */
    public static boolean insert(User u) {
        return dao.insert(u);
    }

    /**
     * 根据手机号修改用户信息
     *
     * @param userPhone 需要修改的用户手机号
     * @param newUser   新的用户信息
     * @return 录入的结果，成功为true，失败为false
     */
    public static boolean update(String userPhone, User newUser) {
        return dao.update(userPhone,newUser);
    }

    /**
     * 根据用户手机号删除用户信息
     *
     * @param userPhone 要删除的用户手机号
     * @return 录入的结果，成功为true，失败为false
     */
    public static boolean delete(String userPhone) {
        return dao.delete(userPhone);
    }
}
