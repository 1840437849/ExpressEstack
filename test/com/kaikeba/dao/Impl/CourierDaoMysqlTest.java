package com.kaikeba.dao.Impl;

import com.kaikeba.bean.Courier;
import com.kaikeba.dao.BaseCourierDao;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class CourierDaoMysqlTest {
    BaseCourierDao dao=new CourierDaoMysql();
    @Test
    public void console() {
        List<Map<String, Integer>> console = dao.console();
        System.out.println(console);
    }

    @Test
    public void findAll() {
        List<Courier> all = dao.findAll(false, 0, 0);
        System.out.println(all);

    }

    @Test
    public void findByPhone() {
        Courier byPhone = dao.findByPhone("15350210273");
        System.out.println(byPhone);
    }

    @Test
    public void insert() {
        Courier c=new Courier("毕文超","15350210273","360122200204206919","1840437849@zyx");
        boolean insert = dao.insert(c);
        System.out.println(insert);
    }

    @Test
    public void update() {
        Courier newCourier = new Courier("居居","18979201176","123456789456123789","18404");
        boolean update = dao.update("1840437849", newCourier);
        System.out.println(update);
    }

    @Test
    public void delete() {
        System.out.println(dao.delete("18979201176"));

    }
}