package com.kaikeba.dao.Impl;

import com.kaikeba.bean.Express;
import com.kaikeba.dao.BaseExpressDao;
import com.kaikeba.exception.DuplicateCodeException;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ExpressDaoMysqlTest {
    BaseExpressDao dao=new ExpressDaoMysql();
    @Test
    public void console() {
        List<Map<String, Integer>> console = dao.console();
        System.out.println(console);
    }

    @Test
    public void findAll() {
        List<Express> all = dao.findAll(true, 0, 3);
        System.out.println(all);
        List<Express> all1 = dao.findAll(false, 0, 0);
        System.out.println(all1);
    }

    @Test
    public void findByNumber() {
        Express byNumber = dao.findByNumber("2131239");
        System.out.println(byNumber);
    }

    @Test
    public void findByCode() {
        Express byCode = dao.findByCode("123457");
        System.out.println(byCode);
    }

    @Test
    public void findByUserPhone() {
        List<Express> byUserPhone = dao.findByUserPhone("15350210273");
        System.out.println(byUserPhone);
    }

    @Test
    public void findBySysPhone() {
        List<Express> bySysPhone = dao.findBySysPhone("15350210273");
        System.out.println(bySysPhone);
    }

    @Test
    public void insert() {
        //String number, String username, String userPhone, String company, String sysPhone
        Express e = new Express("2131299","查裕心","15870842081","顺丰快递","666666","15350210273");
        boolean insert = false;
        try {
            insert = dao.insert(e);
        } catch (DuplicateCodeException ex) {
            System.out.println("取件码重复的异常被捕获到了");
        }
        System.out.println(insert);
    }
    @Test
    public void insert2() {
        //String number, String username, String userPhone, String company, String sysPhone
        boolean insert = false;
        Express e =null;
        for (int i = 0; i < 100; i++) {
             e = new Express("2131299"+i,"查裕心","15870842081","顺丰快递","666666"+i,"15350210273");
            try {
                insert = dao.insert(e);
            } catch (DuplicateCodeException ex) {
                System.out.println("取件码重复的异常被捕获到了");
            }
            System.out.println(insert);
        }
        }


    @Test
    public void update() {
        Express e=new Express();
        e.setNumber("128");
        e.setUsername("毕文超");
        e.setCompany("京东快递");
        e.setStatus(0);
        boolean update = dao.update(6, e);
        System.out.println(update);
    }

    @Test
    public void updateStatus() {
        boolean b = dao.updateStatus("666666");
        System.out.println(b);
    }

    @Test
    public void delete() {
        boolean delete = dao.delete(5);
        System.out.println(delete);
    }
}