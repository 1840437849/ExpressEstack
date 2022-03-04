package com.express.wx.Controller;

import com.express.bean.Courier;
import com.express.bean.Message;
import com.express.bean.User;
import com.express.mvc.ResponseBody;
import com.express.service.CourierService;
import com.express.service.UserService;
import com.express.util.JSONUtil;
import com.express.util.RandomUtil;
import com.express.util.SMSUtil;
import com.express.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController {
    /**
     * 短信发送功能
     * @param req 客户端传递手机号
     * @param resp 返回短信验证码给客户端
     * @return JSON格式消息对象
     */
    @ResponseBody("/wx/loginSms.do")
    public String send(HttpServletRequest req, HttpServletResponse resp) {
        String userPhone = req.getParameter("userPhone");
        //String  code = RandomUtil.getCode()+"";
        String code = "123456";
        /*
        未上线，导致无法使用短信功能，先用随机码代替，默认发送成功
        boolean flag = SMSUtil.loginSMS(userPhone, code);
        */
        boolean flag = true;
        Message msg = new Message();
        if (flag) {
            //短信发送成功
            msg.setStatus(0);
            msg.setResult("验证码发送成功，请注意查收！");
        } else {
            //短信发送失败
            msg.setStatus(-1);
            msg.setResult("验证码发送失败，请检查手机号或稍后再试！");
        }

        UserUtil.setLoginSMS(req.getSession(), userPhone, code);
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 登陆功能
     * @param req 用户传入手机号与验证码
     * @param req 根据情况回复不同的状态码到客户端
     * @return 返回状态码与消息对象
     */
    @ResponseBody("/wx/login.do")
    public String login(HttpServletRequest req, HttpServletResponse resp) {
        String userPhone = req.getParameter("userPhone");
        String userCode = req.getParameter("code"); //用户发过来的code
        String sysCode = UserUtil.getLoginSMS(req.getSession(), userPhone); //系统存储的code
        Message msg = new Message();

        if (sysCode == null) {
            //说明未获取到短信
            msg.setStatus(-1);
            msg.setResult("该手机号码未获取到短信！");
        } else if (sysCode.equals(userCode)) {
            // 验证码一致
            // TODO:判断是快递员还是用户，如果都不是则默认注册为用户，如果都是则以快递员身份登录
            User user1 = UserService.findByPhone(userPhone);
            Courier courier1 = CourierService.findByPhone(userPhone);
            if (user1 ==null && courier1==null) {
                //新注册用户
                msg.setStatus(0);
                User user = new User();
                user.setUserPhone(userPhone);
                UserService.insert(user);
                UserUtil.setWxUser(req.getSession(),user);
            } else if (user1!=null && courier1!=null) {
                //快递员登录
                msg.setStatus(1);
                UserUtil.setWxUser(req.getSession(),courier1);
            } else if (user1!=null && courier1==null) {
                //用户登录
                msg.setStatus(0);
                UserUtil.setWxUser(req.getSession(), user1);
            } else if (user1==null && courier1!=null) {
                //快递员登陆
                msg.setStatus(1);
                UserUtil.setWxUser(req.getSession(), courier1);
            }
        } else {
            //这里是短信验证码不一致、登录失败
            msg.setStatus(-2);
            msg.setResult("验证码不一致，请检查！");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 判断当前登陆的是用户还是快递员
     * @param req
     * @param resp
     * @return
     */
    @ResponseBody("/wx/userInfo.do")
    public String userInfo(HttpServletRequest req, HttpServletResponse resp) {
        Object user = UserUtil.getWxUser(req.getSession());
        boolean isUser = user instanceof User;
        Message msg = new Message();
        if (isUser)
            //是用户
            msg.setStatus(0);
        else
            //是快递员
            msg.setStatus(1);
        msg.setResult(((Courier)user).getUserPhone());
        //这样就把用户身份和手机号码发送出去了
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 退出登陆
     * @param req 销毁session
     * @param resp 回复客户端消息
     * @return  JSON格式的消息对象
     */
    @ResponseBody("/wx/logout.do")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        //1.    销毁session
        req.getSession().invalidate();
        //2.    给客户端回复消息
        Message msg = new Message();
        msg.setStatus(0);
        msg.setResult("退出成功！");
        return JSONUtil.toJSON(msg);
    }
}
