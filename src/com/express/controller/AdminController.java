package com.express.controller;

import com.express.bean.Message;
import com.express.mvc.ResponseBody;
import com.express.service.AdminService;
import com.express.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class AdminController {
    @ResponseBody("/admin/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean result = AdminService.login(username, password);
        // 准备不同的返回数据
        Message msg = null;
        if (result){
            // {status:0, result:"登录成功"}
            msg = new Message(0, "登录成功");
            // 登录时间和ip的更新
            Date date = new Date();
            String ip = request.getRemoteAddr();
            AdminService.updateLoginTimeAndIp(username, date, ip);
            request.getSession().setAttribute("adminUserName","username");
        }else {
            // {status:-1, result:"登录失败"}
            msg = new Message(-1, "登录失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    /*@ResponseBody("/admin/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        //1.接收参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //2.调用service 传参数并获取结果
        boolean result = AdminService.login(username, password);
        //3.根据结果，返回不同的数据
        Message msg = null;
        if (result) {
            msg = new Message(0, "登录成功");  //{status:0,result:"登录成功"}
            //登录时间、IP更新
            Date date = new Date();
            //request里面封装了HTTP协议请求信息，例如对方的操作系统、对方的IP都会有
            String ip=request.getRemoteAddr();
            //拿到参数后，开始调用service更新时间与IP
            AdminService.updateLoginTimeAndIp(username,date,ip);
        } else {
            msg = new Message(-1, "登录失败"); //{status:-1,result:"登录失败"}
        }
        //4.将数据转换为json
        String json = JSONUtil.toJSON(msg);
        //5.将数据回复给ajax
        return json;
    }*/
}
