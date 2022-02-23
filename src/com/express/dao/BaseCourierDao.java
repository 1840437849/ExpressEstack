package com.express.dao;

import com.express.bean.Courier;

import java.util.List;
import java.util.Map;

public interface BaseCourierDao {

    /**
     * 用于查询数据库中的全部快递员（总数，当日新增）
     * 快递员（总数，当日新增）
     * @return [{size:总数, day:新增}]
     */
    public List<Map<String, Integer>> console();

    /**
     * 用于查询所有快递员信息
     * @param limit 是否分页的标记，true,表示分页，false表示查询所有快递员信息
     * @param offset SQL语句的其实索引
     * @param pageNumber 每页查询的数量
     * @return 快递员信息的集合
     */
    List<Courier> findAll(boolean limit, int offset, int pageNumber);

    /**
     * 用于通过手机号查询快递员信息
     * @param userPhone 要查询的快递员手机号
     * @return 快递员的信息
     */
    Courier findByPhone(String userPhone);

    /**
     * 添加快递员
     * @param c 快递员信息
     * @return  录入的结果，成功为true，失败为false
     */
    boolean insert(Courier c);

    /**
     * 修改快递员
     * @param userPhone 要修改快递员的手机号
     * @param newCourier 新的快递员对象（String username,String userPhone,String idNumber,String password）
     * @return 录入的结果，成功为true，失败为false
     */
    boolean update(String userPhone,Courier newCourier);

    /**
     * 删除快递员
     * @param userPhone 要删除的快递员的手机号
     * @return  录入的结果，成功为true，失败为false
     */
    boolean delete(String userPhone);
}
