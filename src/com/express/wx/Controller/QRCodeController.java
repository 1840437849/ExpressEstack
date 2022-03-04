package com.express.wx.Controller;

import com.express.bean.BootStrapTableExpress;
import com.express.bean.Express;
import com.express.bean.Message;
import com.express.bean.User;
import com.express.mvc.ResponseBody;
import com.express.mvc.ResponseView;
import com.express.service.ExpressService;
import com.express.util.DataFormatUtil;
import com.express.util.JSONUtil;
import com.express.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class QRCodeController {
    @ResponseView("/wx/createQrcode.do")
    public String createQrCode(HttpServletRequest req, HttpServletResponse resp) {
        String code = req.getParameter("code");
        String type = req.getParameter("type");
        String QrcodeContent = null;  //二维码展示的消息
        String userPhone = null;
        User u =null;
        if ("express".equals(type)) {
            //快递二维码(只显示单个快递)
            //code
            QrcodeContent = "express_" + code;
        } else {
            //用户二维码(显示用户所有快递)
            //userPhone
            Object wxUser = UserUtil.getWxUser(req.getSession());//拿到微信的登陆用户
            if (wxUser instanceof User){
                u = (User) wxUser;
            }
            userPhone = u.getUserPhone();
            QrcodeContent = "userPhone" + userPhone;
        }
        HttpSession session = req.getSession();
        session.setAttribute("qrcode", QrcodeContent);
        return "/personQRcode.html";
    }

    @ResponseBody("/wx/qrcode.do")
    public String getQRCode(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String qrcode = (String) session.getAttribute("qrcode");
        Message msg = new Message();
        //防止用户从非正常渠道进来，这里做判断
        if (qrcode == null) {
            msg.setStatus(-1);
            msg.setResult("二维码查询失败，请用户重新操作!");
        } else {
            msg.setStatus(0);
            msg.setResult(qrcode);
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/wx/updateStatus.do")
    public String updateExpressStatus(HttpServletRequest req, HttpServletResponse resp) {
        String code = req.getParameter("code");
        boolean flag = ExpressService.updateStatus(code);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("取件成功！");
        } else {
            msg.setStatus(-1);
            msg.setResult("取件码不存在，请用户更新二维码");
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/wx/findExpressByCode.do")
    public String findExpressByCode(HttpServletRequest req, HttpServletResponse resp) {
        String code = req.getParameter("code");
        Express e = ExpressService.findByCode(code);
        BootStrapTableExpress e2=null;
        //只有快递对象存在时，才会做操作
        if (e!=null) {
            e2=new BootStrapTableExpress(e.getId(), e.getNumber(),
                    e.getUsername(), e.getUserPhone(), e.getCompany(), e.getCode(),
                    DataFormatUtil.format(e.getInTime()), e.getOutTime()==null?"未出库":DataFormatUtil.format(e.getOutTime()),
                    e.getStatus()==0?"未取件":"已取件", e.getSysPhone());
        }
        Message msg = new Message();
        if (e == null) {
            //取件码不存在时返回null
            msg.setStatus(-1);
            msg.setResult("取件码不存在，请检查！");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e2);
        }
        return JSONUtil.toJSON(msg);
    }
}
