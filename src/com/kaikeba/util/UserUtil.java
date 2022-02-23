package com.kaikeba.util;

import javax.servlet.http.HttpSession;

public class UserUtil {
    //根据session获取当前登陆的用户名、手机号
    // TODO:还没有编写柜子端，未存储任何录入人信息
    public static String getUserName(HttpSession session){
        return (String) session.getAttribute("adminUserName");
    }
    public static String getUserPhone(HttpSession session){
        return "188888888888";
    }
}
