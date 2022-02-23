package com.express.service;

import com.express.bean.Express;
import com.express.dao.BaseExpressDao;
import com.express.dao.Impl.ExpressDaoMysql;
import com.express.exception.DuplicateCodeException;
import com.express.util.RandomUtil;

import java.util.List;
import java.util.Map;

public class ExpressService {
    private static BaseExpressDao dao=new ExpressDaoMysql();
    /**
     * 用于查询所有的全部快递（总数+新增），待取件快递（总数＋新增）
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     */
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页的标记，true,表示分页，false表示查询所有快递
     * @param offset     SQL语句的其实索引
     * @param pageNumber 每页查询的数量
     * @return 快递的集合
     */
    public static List<Express> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    /**
     * 根据快递单号查询快递信息
     *
     * @param number 快递信息
     * @return 查询的快递信息，单号不存在时返回null
     */
    public static Express findByNumber(String number) {
        return dao.findByNumber(number);
    }

    /**
     * 根据取件码查询快递信息
     *
     * @param code 快递信息
     * @return 查询的快递信息，取件码不存在时返回null
     */
    public static Express findByCode(String code) {
        return dao.findByCode(code);
    }

    /**
     * 根据用户的手机号查询快递信息
     * 因为一个人可能有多个快递，所以定义一个集合
     *
     * @param userPhone 用户手机号
     * @return 查询的快递信息列表
     */
    public static List<Express> findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * 根据录入人的手机号查询快递信息
     * 因为一个人可能有多个快递，所以定义一个集合
     *
     * @param sysPhone 录入人手机号
     * @return 查询的快递信息列表
     */
    public static List<Express> findBySysPhone(String sysPhone) {
        return dao.findBySysPhone(sysPhone);
    }

    /**
     * 快递的录入
     *
     * @param e 要录入的快递对象
     * @return 录入的结果，成功为true，失败为false
     */
    public static boolean insert(Express e){
        //1.生成取件码
        e.setCode(RandomUtil.getCode()+"");//加""的原因是code是一个字符串，需要把它变成一个字符串
        try {
            //当出现异常，就说明取件码重复，重新调用insert(),否则就直接return
            boolean flag= dao.insert(e);
            if (flag) {
                //说明录入成功，可以通过sms进行sendsms的操作
                //如果这里短信签名申请不下来，那么可以改用随机数的方式，如下
                System.out.println("您的取件码:"+e.getCode()+"，请凭该取件码及时取走快递，请勿泄漏于他人！");
                //SMSUtil.send(e.getUserPhone(),e.getCode());
            }
            return flag;
        } catch (DuplicateCodeException ex) {
            return insert(e);
        }
    }

    /**
     * 快递的修改
     *
     * @param id         要修改的快递id
     * @param newExpress 新的快递对象（number,company,username,userPhone）
     * @return 修改的结果，true为成功，false为失败
     */
    public static boolean update(int id, Express newExpress) {
        //发现用户手机号不等于空，就说明用户要改这个手机号
        if (newExpress.getUserPhone() != null) {
            //先进行删除
            dao.delete(id);
            //删除后再进行插入，因为需要重新生成取件码的发送
            return insert(newExpress);
        } else {
            Express e = dao.findByNumber(newExpress.getNumber());
            boolean update = dao.update(id, newExpress);
            //当我们判断快递状态码为1时，视为已取件操作，那么执行取件操作
            if (newExpress.getStatus()==1) {
                updateStatus(e.getCode());
            }
            //返回快递结果
            return update;
        }
    }

    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 要修改的快递单号
     * @return 修改的结果，true为成功，false为失败
     */
    public static boolean updateStatus(String code) {
        return dao.updateStatus(code);
    }

    /**
     * 根据id删除快递信息
     *
     * @param id 要删除的快递id
     * @return 删除的结果，true为成功，false为失败
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
