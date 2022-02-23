package com.kaikeba.service;

import com.kaikeba.bean.Courier;
import com.kaikeba.dao.BaseCourierDao;
import com.kaikeba.dao.Impl.CourierDaoMysql;

import java.util.List;
import java.util.Map;

public class CourierService{
    private static BaseCourierDao dao=new CourierDaoMysql();
    /**
     * 用于查询数据库中的全部快递员（总数，当日新增）
     * 快递员（总数，当日新增）
     *
     * @return [{size:总数, day:新增}]
     */
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 用于查询所有快递员信息
     *
     * @param limit      是否分页的标记，true,表示分页，false表示查询所有快递员信息
     * @param offset     SQL语句的其实索引
     * @param pageNumber 每页查询的数量
     * @return 快递员信息的集合
     */
    public static List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    /**
     * 用于通过手机号查询快递员信息
     *
     * @param userPhone 要查询的快递员手机号
     * @return 快递员的信息
     */
    public static Courier findByPhone(String userPhone) {
        return dao.findByPhone(userPhone);
    }

    /**
     * 添加快递员
     *
     * @param c 快递员信息
     * @return 受影响的行数
     */
    public static boolean insert(Courier c) {
        return dao.insert(c);
    }

    /**
     * 修改快递员
     *
     * @param userPhone  要修改快递员的手机号
     * @param newCourier 新的快递员对象（String username,String userPhone,String idNumber,String password）
     * @return 受影响的行数
     */
    public static boolean update(String userPhone, Courier newCourier) {
        return dao.update(userPhone,newCourier);
    }

    /**
     * 删除快递员
     *
     * @param userPhone 要删除的快递员的手机号
     * @return 受影响的行数
     */
    public static boolean delete(String userPhone) {
        return dao.delete(userPhone);
    }
}
