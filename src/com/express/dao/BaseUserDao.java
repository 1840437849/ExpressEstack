package com.express.dao;

import com.express.bean.User;

import java.util.List;
import java.util.Map;

public interface BaseUserDao {

    /**
     * 用于查询数据中全部用户（用户总数，当日新增）
     * 用户（总数，新增）
     * @return [{ size:总数 , day:当日新增 }]
     */
     Map<String,Integer> console();

    /**
     * 用于查询数据库中所有的用户信息
     * @param limit true为分页（默认），false为不分页直接查询所有
     * @param offset 查询SQL语句的起始索引（就是是从哪一条开始查的）
     * @param pageNumber 每一页显示的用户数量
     * @return 用户信息的集合
     */
     List<User> findAll(boolean limit,int offset,int pageNumber);

    /**
     * 用于通过手机号查询快递员信息
     * @param userPhone 要查询的用户手机号
     * @return 用户的信息
     */
     User findByPhone(String userPhone);

    /**
     * 用户信息录入
     * @param u 需要录入的用户信息（String username,String userPhone,String idNumber,String password）
     * @return 录入的结果，成功为true，失败为false
     */
     boolean insert (User u);

    /**
     * 根据手机号修改用户信息
     * @param userPhone 需要修改的用户手机号
     * @param newUser   新的用户信息
     * @return 录入的结果，成功为true，失败为false
     */
     boolean update(String userPhone,User newUser);

    /**
     * 根据用户手机号删除用户信息
     * @param userPhone 要删除的用户手机号
     * @return 录入的结果，成功为true，失败为false
     */
     boolean delete(String userPhone);
}
