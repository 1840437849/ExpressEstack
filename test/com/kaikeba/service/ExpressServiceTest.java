package com.kaikeba.service;

import com.kaikeba.bean.Express;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressServiceTest {

    @Test
    public void console() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void findByNumber() {
    }

    @Test
    public void findByCode() {
    }

    @Test
    public void findByUserPhone() {
    }

    @Test
    public void findBySysPhone() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void update() {
        int id=1;
        Express newExpress=new Express("1231","李四1","15350210273","京东快递","0");
        boolean update = ExpressService.update(id, newExpress);
        System.out.println(update);
    }

    @Test
    public void updateStatus() {
    }

    @Test
    public void delete() {
    }
}