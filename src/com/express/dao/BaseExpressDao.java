package com.express.dao;

import com.express.bean.Express;
import com.express.exception.DuplicateCodeException;

import java.util.List;
import java.util.Map;

public interface BaseExpressDao {
    /**
     * 用于查询所有的全部快递（总数+新增），待取件快递（总数＋新增）
     * @return  [{size:总数,day:新增},{size:总数,day:新增}]
     */
    List<Map<String ,Integer>> console();

    /**
     * 用于查询所有快递
     * @param limit 是否分页的标记，true,表示分页，false表示查询所有快递
     * @param offset SQL语句的其实索引
     * @param pageNumber 每页查询的数量
     * @return 快递的集合
     */
    List<Express> findAll(boolean limit,int offset,int pageNumber);

    /**
     * 根据快递单号查询快递信息
     * @param number 快递信息
     * @return  查询的快递信息，单号不存在时返回null
     */
    Express findByNumber(String number);

    /**
     * 根据取件码查询快递信息
     * @param code 快递信息
     * @return  查询的快递信息，取件码不存在时返回null
     */
    Express findByCode(String code);

    /**
     * 根据用户的手机号查询快递信息
     *      因为一个人可能有多个快递，所以定义一个集合
     * @param  userPhone 用户手机号
     * @return  查询的快递信息列表
     */
    List<Express> findByUserPhone(String userPhone);

    /**
     * 根据录入人的手机号查询快递信息
     *      因为一个人可能有多个快递，所以定义一个集合
     * @param  sysPhone 录入人手机号
     * @return  查询的快递信息列表
     */
    List<Express> findBySysPhone(String sysPhone);

    /**
     * 快递的录入
     * @param e 要录入的快递对象
     * @return  录入的结果，成功为true，失败为false
     */
    boolean insert(Express e)throws DuplicateCodeException;

    /**
     * 快递的修改
     * @param id 要修改的快递id
     * @param newExpress 新的快递对象（number,company,username,userPhone）
     * @return 修改的结果，true为成功，false为失败
     */
    boolean update(int id,Express newExpress);

    /**
     * 更改快递的状态为1，表示取件完成
     * @param code 要修改的快递单号
     * @return 修改的结果，true为成功，false为失败
     */
    boolean updateStatus(String code);

    /**
     * 根据id删除快递信息
     * @param id 要删除的快递id
     * @return  删除的结果，true为成功，false为失败
     */
    boolean delete(int id);
}
