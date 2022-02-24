package com.express.controller;

import com.express.bean.*;
import com.express.mvc.ResponseBody;
import com.express.service.CourierService;
import com.express.util.DataFormatUtil;
import com.express.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourierController {
    /**
     * 控制台管理
     *
     * @param req  客户端请求内容
     * @param resp 服务器回复内容
     * @return JSON格式快递员总数、当日新增
     */
    @ResponseBody("/courier/console.do")
    public String console(HttpServletRequest req, HttpServletResponse resp) {
        //1.得到数据
        List<Map<String, Integer>> data = CourierService.console();
        System.out.println("进入了控制台");
        //2.创建消息对象
        Message msg = new Message();
        //判断数据中是否有值
        if (data.size() == 0) {
            //如果没有值，那么就给-1
            msg.setStatus(-1);
        } else {
            msg.setStatus(0);
        }
        //；将控制台中的快递数据传入消息对象中
        msg.setData(data);
        //5.将消息对象中的数据转换为json类型
        String json = JSONUtil.toJSON(msg);
        //将消息对象中的数据发往前端
        return json;
    }

    @ResponseBody("/courier/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp) {
        //1.查询数据的起始索引值
        int offset = Integer.parseInt(req.getParameter("offset"));
        //2.获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        //3.进行分页查询
        List<Courier> list = CourierService.findAll(true, offset, pageNumber);
        List<BootStrapTableCourier> list2 = new ArrayList<>();
        for (Courier c : list) {
            //调用时间格式化工具类将数据转为String类型并将时间格式为 yyyy-MM-dd HH:mm:ss
            String regLstTime = DataFormatUtil.format(c.getRegLstTime());
            String loginTime= c.getLoginTime()==null?"从未登录":DataFormatUtil.format(c.getLoginTime());
            BootStrapTableCourier c2=new BootStrapTableCourier(c.getId(),c.getUsername(),c.getUserPhone(),c.getIdNumber(),c.getPassword(),c.getCourierSendNumber(),regLstTime,loginTime);
            list2.add(c2);
        }
        List<Map<String, Integer>> console = CourierService.console();
        //获取集合console中0下标的courier_size的值
        Integer total = console.get(0).get("courier_size");
        //4.将集合封装为BootStrap-Table所识别的格式
        ResultData<BootStrapTableCourier> data = new ResultData<>();
        //这里要封装的数据就是数据的集合
        data.setRows(list2);
        //另一个是数据的总数量
        data.setTotal(total);
        //5.将数据转换成JSON格式
        String json = JSONUtil.toJSON(data);
        return json;
    }

    @ResponseBody("/courier/insert.do")
    public String insert(HttpServletRequest req, HttpServletResponse resp) {
        //接收参数
        String username = req.getParameter("username");
        String userPhone = req.getParameter("userPhone");
        String idNumber = req.getParameter("idNumber");
        String password = req.getParameter("password");
        //调用方法
        Courier c = new Courier(username, userPhone, idNumber, password);
        boolean insert = CourierService.insert(c);
        Message msg = new Message();
        if (insert) {
            msg.setStatus(0);
            msg.setResult("插入成功！");
        } else {
            msg.setStatus(-1);
            msg.setResult("插入失败！请检查");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/courier/find.do")
    public String find(HttpServletRequest req, HttpServletResponse resp) {
        //接收参数
        String phone = req.getParameter("phone");
        Courier data = CourierService.findByPhone(phone);
        Message msg = new Message();
        if (data == null) {
            //说明没找到
            msg.setStatus(-1);
            msg.setResult("该手机号码不存在，请检查！");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功！");
            msg.setData(data);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/courier/update.do")
    public String update(HttpServletRequest req, HttpServletResponse resp) {
        //接收参数
        String c_userPhone = req.getParameter("c_userPhone");
        String username = req.getParameter("username");
        String userPhone = req.getParameter("userPhone");
        String idNumber = req.getParameter("idNumber");
        String password = req.getParameter("password");
        Courier newCourier = new Courier();
        newCourier.setUsername(username);
        newCourier.setUserPhone(userPhone);
        newCourier.setIdNumber(idNumber);
        newCourier.setPassword(password);
        boolean update = CourierService.update(c_userPhone, newCourier);
        Message msg = new Message();
        if (update) {
            msg.setStatus(0);
            msg.setResult("修改成功！");
        } else {
            msg.setStatus(-1);
            msg.setResult("修改失败！");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/courier/delete.do")
    public String delete(HttpServletRequest req, HttpServletResponse resp){
        //接收参数
        String phone = req.getParameter("phone");
        boolean delete = CourierService.delete(phone);
        Message msg = new Message();
        if (delete) {
            msg.setStatus(0);
            msg.setResult("删除成功！");
        } else {
            msg.setStatus(-1);
            msg.setResult("删除失败！");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
