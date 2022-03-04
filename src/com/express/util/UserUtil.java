package com.express.util;

import com.express.bean.Courier;
import com.express.bean.User;

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
    /**
     * 获取登录验证码
     * @param session
     * @param userPhone
     * @return
     */
    public static String getLoginSMS(HttpSession session,String userPhone){
        return (String) session.getAttribute(userPhone);
    }
    /**
     * 设置登录验证码
     * @param session
     * @param userPhone
     * @param code
     */
    public static void setLoginSMS(HttpSession session,String userPhone,String code){
        session.setAttribute(userPhone,code);
    }
    /**
     * 设置当前登陆的快递员/用户
     * 方法重载，当前登录的可能是用户也可能是快递员
     * @param session
     * @param user
     */
    public static void setWxUser(HttpSession session , User user) {
        session.setAttribute("wxUser",user);
    }
    public static void setWxUser(HttpSession session , Courier courier){
        session.setAttribute("wxUser",courier);
    }
    /**
     * 获取当前登录的用户/快递员
     * @param session
     * @return
     */
    public static Object getWxUser(HttpSession session){
        return  session.getAttribute("wxUser");
    }
}
