package com.express.controller;

import com.express.bean.*;
import com.express.mvc.ResponseBody;
import com.express.service.UserService;
import com.express.util.DataFormatUtil;
import com.express.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserController {

    /**
     * 控制台管理
     * @param req 客户端请求数据
     * @param resp 服务器返回数据
     * @return  JSON格式返回用户总数、当日新增
     */
    @ResponseBody("/user/console.do")
    public String console(HttpServletRequest req, HttpServletResponse resp){
        //得到数据库数据
        List<Map<String, Integer>> data = UserService.console();
        //创建消息对象
        Message msg = new Message();
        if (data.size() == 0) {
            //说明没有值
            msg.setStatus(-1);
        } else {
            msg.setStatus(0);
        }
        msg.setData(data);
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 查询用户信息（分页）
     * @param req 客户端请求信息
     * @param resp 服务器请求信息
     * @return JSON格式的用户信息数据
     */
    @ResponseBody("/user/list.do")
    public String findAll(HttpServletRequest req,HttpServletResponse resp){
        //接收参数
        int offset = Integer.parseInt(req.getParameter("offset"));
        int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        List<User> list = UserService.findAll(true, offset, pageNumber);
        List<BootStrapTableUser> list2 = new ArrayList<>();
        for (User u : list) {
            //调用时间格式化工具类将数据转为String类型并将时间格式为 yyyy-MM-dd HH:mm:ss
            String regLstTime = DataFormatUtil.format(u.getRegLstTime());
            String loginTime= u.getLoginTime()==null?"从未登录":DataFormatUtil.format(u.getLoginTime());
            BootStrapTableUser u2=new BootStrapTableUser(u.getId(),u.getUsername(), u.getUserPhone(), u.getIdNumber(), u.getPassword(),regLstTime,loginTime);
            list2.add(u2);
        }
        List<Map<String, Integer>> console = UserService.console();
        //获取集合console中0下标的courier_size的值
        Integer total = console.get(0).get("user_size");
        ResultData<BootStrapTableUser> data = new ResultData<>();
        //当前页数据
        data.setRows(list2);
        //总页数
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }

    /**
     * 插入用户信息
     * @param req 客户端请求信息
     * @param resp 服务器请求信息
     * @return JSON格式的用户信息数据
     */
    @ResponseBody("/user/insert.do")
    public String insert(HttpServletRequest req,HttpServletResponse resp){
        //接收参数
        String username = req.getParameter("username");
        String userPhone = req.getParameter("userPhone");
        String idNumber = req.getParameter("idNumber");
        String password = req.getParameter("password");
        User u = new User(username,userPhone,idNumber,password);
        boolean flag = UserService.insert(u);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("用户添加成功");
        } else {
            msg.setStatus(-1);
            msg.setResult("用户添加失败，请检查！");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 修改用户信息
     * @param req 客户端请求信息
     * @param resp 服务器请求信息
     * @return JSON格式的用户信息数据
     */
    @ResponseBody("/user/find.do")
    public String findByPhone(HttpServletRequest req,HttpServletResponse resp){
        String phone = req.getParameter("phone");
        User data = UserService.findByPhone(phone);
        Message msg = new Message();
        if (data !=null) {
            msg.setStatus(0);
            msg.setResult("查询成功！");
        } else {
            msg.setStatus(-1);
            msg.setResult("手机号错误，请检查！");
        }
        msg.setData(data);
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/user/update.do")
    public String update(HttpServletRequest req,HttpServletResponse resp){
        String phone = req.getParameter("phone");
        String username = req.getParameter("username");
        String userPhone = req.getParameter("userPhone");
        String idNumber = req.getParameter("idNumber");
        String password = req.getParameter("password");
        User newUser = new User(username,userPhone,idNumber,password);
        boolean flag = UserService.update(phone, newUser);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("更新成功！");
        } else {
            msg.setStatus(-1);
            msg.setResult("更新失败，请检查！");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/user/delete.do")
    public String delete(HttpServletRequest req,HttpServletResponse resp){
        String phone = req.getParameter("phone");
        boolean flag = UserService.delete(phone);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("删除成功！");
        } else {
            msg.setStatus(-1);
            msg.setResult("删除失败请检查！");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
